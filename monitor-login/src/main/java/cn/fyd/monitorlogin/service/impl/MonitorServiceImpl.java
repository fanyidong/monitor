package cn.fyd.monitorlogin.service.impl;

import cn.fyd.common.MonitorException;
import cn.fyd.model.Monitor;
import cn.fyd.model.Result;
import cn.fyd.monitorlogin.common.HttpUtil;
import cn.fyd.monitorlogin.common.NumUtil;
import cn.fyd.monitorlogin.dao.MonitorDao;
import cn.fyd.monitorlogin.dao.ResultDao;
import cn.fyd.monitorlogin.service.MonitorService;
import cn.fyd.monitorlogin.service.QuartzService;
import cn.fyd.monitorlogin.service.ResultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static cn.fyd.common.Constant.*;

/**
 * Monitor服务实现层
 * @author fanyidong
 * @date Created in 2019-01-07
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private static Logger log = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Autowired
    private MonitorDao monitorDao;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private ResultService resultService;
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private HttpUtil httpUtil;

    @Override
    public int saveMonitor(Monitor monitor) throws MonitorException, SchedulerException {
        log.info("开始保存监控任务 -> 监控名:" + monitor.getName());
        // 是否存在相同并且开启的监控
        if (hadMonitor(monitor)) {
            throw new MonitorException(MONITOR_EXIST);
        }
        // 设置监控启用状态为1-开启
        monitor.setState(1);
        monitor.setMonitorId(UUID.randomUUID().toString());
        // 验证post请求时的提交内容是否符合格式要求
        if (POST.equals(monitor.getRequestMethod())) {
           checkPostParams(monitor.getCommitContent());
        }
        int addNum = monitorDao.saveMonitor(monitor);
        if (addNum == 0) {
            throw new MonitorException(MONITOR_ADD_FAILED);
        }
        // 开启定时任务
        quartzService.updateQuartz(monitor);
        log.info("保存监控任务成功 -> 监控名:" + monitor.getName());
        return addNum;
    }

    @Override
    public int closeMonitor(String monitorId, Integer state) throws MonitorException, SchedulerException {
        log.info("开始更改监控任务启用状态 -> 监控id:" + monitorId);
        // 根据monitorId查询实体类
        Monitor queryParam = new Monitor();
        queryParam.setMonitorId(monitorId);
        Monitor queryRes = monitorDao.getMonitorBySelective(queryParam);
        if (queryRes == null) {
            throw new MonitorException(MONITOR_NOT_EXIST);
        }
        int closeNum = monitorDao.closeMonitor(monitorId, state);
        if (closeNum == 0) {
            throw new MonitorException(EDIT_FAILED);
        }
        // 暂停/恢复定时任务
        if (state == 0) {
            quartzService.pauseQuartz(queryRes.getMonitorId(), queryRes.getUserId());
        } else {
            quartzService.resumeQuartz(queryRes);
        }
        log.info("更改监控任务启用状态成功 -> 监控名:" + queryRes.getName());
        return closeNum;
    }

    @Override
    public int editMonitor(Monitor newMonitor) throws MonitorException, SchedulerException {
        log.info("开始编辑监控任务 -> 监控名:" + newMonitor.getName());
        // 根据主键id查询
        if (monitorDao.countByPrimaryKey(newMonitor.getMonitorId()) == 0) {
            throw new MonitorException(MONITOR_NOT_EXIST);
        }
        // 是否存在相同并且开启的监控
        if (hadMonitor(newMonitor)) {
            throw new MonitorException(MONITOR_EXIST);
        }
        // 验证post请求时的提交内容是否符合格式要求
        if (POST.equals(newMonitor.getRequestMethod())) {
            checkPostParams(newMonitor.getCommitContent());
        }
        int editNum = monitorDao.editMonitorByPrimaryKey(newMonitor);
        if (editNum == 0) {
            throw new MonitorException(EDIT_FAILED);
        }
        Monitor monitor = monitorDao.getMonitorByPrimaryKey(newMonitor.getMonitorId());
        // 更新定时任务
        quartzService.updateQuartz(monitor);
        log.info("编辑监控任务成功 -> 监控名:" + newMonitor.getName());
        return editNum;
    }

    @Override
    public PageInfo<Monitor> getMonitors(String userId, Integer pageNum, Integer pageSize) {
        log.info("开始查询监控任务 -> userId:" + userId);
        Monitor queryParam = new Monitor();
        queryParam.setUserId(userId);
        // 分页操作
        PageHelper.startPage(pageNum, pageSize);
        // 获取返回结果
        List<Monitor> resList = monitorDao.queryBySelective(queryParam);
        // 添加平均响应时间及可用率两个字段
        for (Monitor m:resList) {
            // 查询所有请求结果result表
            List<Result> resultList = resultService.getResultsByMonitorId(m.getMonitorId());
            // 监控结果总数
            double sum = resultList.size();
            // 防止空指针异常
            if (sum == 0) {
                m.setAverageResponseTime(ZERO.toString());
                m.setUsable(ZERO.toString());
                continue;
            }
            // 响应时间和
            double reponseTimeSum = 0;
            // 可用的次数
            double usableSum = 0;
            // 计算响应时间和及可用次数和
            for (Result res:resultList) {
                reponseTimeSum += res.getResponseTime();
                // 如果是否可用字段为空则视为不可用
                if (res.getUsable()!=null) {
                    usableSum += res.getUsable()==1?1:0;
                }
            }
            // 计算平均响应时间(保留两位小数)
            m.setAverageResponseTime(NumUtil.stateScale(TWO, reponseTimeSum/sum));
            // 计算可用率，0.2-> 20%，四舍五入保留2位小数
            m.setUsable(NumUtil.stateScale(TWO, usableSum/sum * 100));
        }
        log.info("查询监控任务成功 -> userId:" + userId);
        return new PageInfo<>(resList);
    }

    @Override
    public List<Monitor> getMonitorsByUserId(String userId) {
        log.info("开始查询监控任务 -> userId:" + userId);
        Monitor monitor = new Monitor();
        monitor.setUserId(userId);
        log.info("查询监控任务应该成功，如果失败就是dao层的问题了 -> userId:" + userId);
        return monitorDao.queryBySelective(monitor);
    }

    @Override
    public int delMonitor(String monitorId) throws SchedulerException, MonitorException {
        log.info("开始删除监控任务 -> monitorId:" + monitorId);
        Monitor monitor = monitorDao.getMonitorByPrimaryKey(monitorId);
        if (monitor==null) {
            throw new MonitorException(MONITOR_NOT_EXIST);
        }
        // 删除定时任务
        quartzService.deleteQuartz(monitorId, monitor.getUserId());
        // 删除监控任务
        monitorDao.delMonitorByPrimaryKey(monitorId);
        // 删除监控结果
        resultDao.delResultsByMonitorId(monitorId);
        log.info("删除监控任务及监控结果成功 -> monitorId:" + monitorId);
        return 1;
    }

    @Override
    public Monitor getMonitorByPrimaryKey(String monitorId) {
        return monitorDao.getMonitorByPrimaryKey(monitorId);
    }

    /**
     * 根据项目名称、url和state=1判断是否存在相同的监控
     * @param monitor 监控实体类
     * @return true-已存在 false-不存在
     */
    public boolean hadMonitor(Monitor monitor) {
        Monitor queryParam = new Monitor();
        // 对象复制，设置查询条件
        queryParam.setMonitorId(monitor.getMonitorId());
        queryParam.setName(monitor.getName());
        queryParam.setUrl(monitor.getUrl());
        queryParam.setState(1);
        return monitorDao.countBySelective(queryParam) > 0;
    }

    /**
     * 验证post请求的参数格式是否符合要求
     * @param params
     * @throws MonitorException
     */
    public void checkPostParams(String params) throws MonitorException {
        if (!StringUtils.isEmpty(params)) {
            try {
                httpUtil.createParam(params);
            } catch (Exception e) {
                log.info("post请求参数格式错误", e);
                throw new MonitorException(POST_PARAMS_FORMAT_ERROR);
            }
        }
    }
}
