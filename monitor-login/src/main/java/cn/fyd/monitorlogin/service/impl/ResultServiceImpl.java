package cn.fyd.monitorlogin.service.impl;

import cn.fyd.model.Monitor;
import cn.fyd.model.Result;
import cn.fyd.model.Stat;
import cn.fyd.monitorlogin.common.NumUtil;
import cn.fyd.monitorlogin.dao.ResultDao;
import cn.fyd.monitorlogin.service.MonitorService;
import cn.fyd.monitorlogin.service.ResultService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static cn.fyd.common.Constant.TWO;
import static cn.fyd.common.Constant.ZERO;

/**
 * @author fanyidong
 * @date Created in 2019-02-21
 */
@Service
public class ResultServiceImpl implements ResultService {

    private static Logger log = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultDao resultDao;
    @Autowired
    private MonitorService monitorService;

    @Override
    public int saveResult(Result result) {
        return resultDao.saveResult(result);
    }

    @Override
    public List<Result> getResultsByMonitorId(String monitorId) {
        return resultDao.getResultsByMonitorId(monitorId);
    }

    @Override
    public Stat getStatByUserId(String userId, Integer num) {
        log.info("开始查询统计数据 -> userId:" + userId);
        // 统计结果实体类
        Stat stat = new Stat();
        // 监控任务名
        List<String> monitorNames = new ArrayList<>();

        // 根据userId获取监控任务列表(所有监控order by createTime desc)
        List<Monitor> monitorList = monitorService.getMonitorsByUserId(userId);
        // 该用户所创建的监控项目的所有监控结果
        List<Result> results = new ArrayList<>();
        // 遍历该用户创建的所有监控任务
        for (int monitorsIndex=0; monitorsIndex<monitorList.size(); monitorsIndex++) {
            // 创建本次循环的监控对象
            Monitor thisMonitor = monitorList.get(monitorsIndex);
            log.info("开始处理监控任务(" + thisMonitor.getName() + ")的统计情况");
            // 如果大于默认显示的任务数，则结束统计
            if (num != null && monitorsIndex > num) {
                break;
            }
            // 插入本次监控任务的名字
            monitorNames.add(thisMonitor.getName());
            // 获取本次监控结果列表
            List<Result> resultsByMonitorId = resultDao.getResultsByMonitorId(thisMonitor.getMonitorId());
            // 将查询所得的监控结果加入到所有监控结果列表
            results.addAll(resultsByMonitorId);
            // 监控结果列表大于零时才计算统计参数
            if (resultsByMonitorId.size() > 0) {
                // 计算监控任务的可用率、平均响应时间和异常情况
                calculateThreeParams(resultsByMonitorId, stat);
            } else {
                // 如果没有监控记录则赋值为零
                stat.getAvailableRates().add(Double.valueOf(ZERO));
                stat.getResponseTimes().add(Double.valueOf(ZERO));
                stat.getErrors().add(ZERO);
            }
        }
        // 计算监控任务出现异常的时段
        getErrorTimes(stat, results);
        // 监控任务名赋值
        stat.setMonitorNames(monitorNames);
        log.info("查询统计数据成功 -> userId:" + userId + ";统计情况:" + JSON.toJSONString(stat));
        return stat;
    }

    @Override
    public int errorsTimes(String monitorId) {
        log.info("开始判断监控任务monitorId=" + monitorId + "的监控结果的连续不可用次数");
        // 获取本次监控结果列表
        List<Result> results = resultDao.getResultsByMonitorId(monitorId);
        // 对results集合中的数据进行倒叙排序
        Collections.reverse(results);
        // 不可用的次数
        int unusableCount = 0;
        // 从最新一次监控开始，判断连续监控结果为不可用的数目
        for (int i = 0; i < results.size(); i++) {
            Integer usable = results.get(i).getUsable();
            if (usable==null || usable==0) {
                unusableCount++;
            } else {
                break;
            }
        }
        log.info("本次监控任务id【" + monitorId + "】的连续监控不可用次数为【" +unusableCount+ "】次");
        return unusableCount;
    }

    /**
     * 根据monitorId获取该监控任务的监控结果
     * @param monitorId 监控任务id
     * @param pageNum   当前页数
     * @param pageSize  每页数目
     * @return
     */
    @Override
    public PageInfo<Result> getResultByMonitorId(String monitorId, Integer pageNum, Integer pageSize) {
        // 分页操作
        PageHelper.startPage(pageNum, pageSize);
        List<Result> results = resultDao.getResultsByMonitorId(monitorId);
        return new PageInfo<>(results);
    }

    /**
     * 监控任务出现异常的时段
     * @param stat
     * @param results 该用户所创建的监控任务的所有监控结果
     * @return
     */
    private void getErrorTimes(Stat stat, List<Result> results) {
        // 监控任务出现异常的时段，默认值为0
        List<Integer> errorsInHours = stat.getErrorsInHours();
        for (Result result:results) {
            // 监控结果为不可用
            Integer usable = result.getUsable();
            if (usable==null || usable==0) {
                Calendar errorTime = Calendar.getInstance();
                // 设置监控结果的创建时间
                errorTime.setTime(result.getCreateTime());
                // 获取监控结果在一天中的时间段
                int hour = errorTime.get(Calendar.HOUR_OF_DAY);
                // 获得返回结果列表的该时间段的值
                Integer errors = errorsInHours.get(hour);
                // 异常次数+1
                errors++;
                // 重新为该时间段赋值
                errorsInHours.set(hour, errors);
            }
        }
    }

    /**
     * 计算监控任务的可用率、平均响应时间和异常情况
     * @param results 本次监控的监控结果
     * @param stat 统计结果实体类
     */
    private void calculateThreeParams(List<Result> results, Stat stat) {
        // 监控项目的异常情况
        int errors = 0;
        // 监控结果总次数
        double resultCount = results.size();
        // 监控结果成功次数响应时间和
        double responseTimeSum = 0;

        // 遍历每一次监控结果
        for (int resultIndex = 0; resultIndex < results.size(); resultIndex++) {
            // 本次循环的监控结果
            Result thisResult = results.get(resultIndex);
            // 计算异常次数
            Integer usable = thisResult.getUsable();
            if (usable==null || usable==0) {
                // 异常次数+1
                errors++;
            }
            // 响应时间累加
            responseTimeSum += thisResult.getResponseTime();
        }
        // 本次监控项目的异常情况
        stat.getErrors().add(errors);
        // 本次监控项目的可用率
        String availableRate = NumUtil.stateScale(TWO, (resultCount - errors) / resultCount * 100);
        stat.getAvailableRates().add(Double.valueOf(availableRate));
        // 本次监控项目的平均响应时间
        String responseTime = NumUtil.stateScale(TWO, responseTimeSum / resultCount);
        stat.getResponseTimes().add(Double.valueOf(responseTime));
    }
}
