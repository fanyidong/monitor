package cn.fyd.monitorlogin.common;

import cn.fyd.common.MonitorException;
import cn.fyd.model.Monitor;
import cn.fyd.model.Result;
import cn.fyd.model.User;
import cn.fyd.monitorlogin.dao.UserDao;
import cn.fyd.monitorlogin.service.MailService;
import cn.fyd.monitorlogin.service.MonitorService;
import cn.fyd.monitorlogin.service.ResultService;
import com.alibaba.fastjson.JSON;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

import static cn.fyd.common.Constant.*;

/**
 * 定义定时任务类
 * @author fanyidong
 * @date Created in 2019-02-13
 */
@DisallowConcurrentExecution
public class QuartzHttpJob implements Job {

    private static Logger log = LoggerFactory.getLogger(QuartzHttpJob.class);

    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private ResultService resultService;
    @Autowired
    private MonitorService monitorService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserDao userDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取job中动态传递的参数
        String monitorId = (String) context.getMergedJobDataMap().get("monitorId");
        // 查询最新的监控任务
        Monitor scheduleJob = monitorService.getMonitorByPrimaryKey(monitorId);
        log.info("开始执行定时任务 -> 监控名:" + scheduleJob.getName());
        // 创建结果对象类
        Result resultIntoDB = httpUtil.doRequest(scheduleJob);
        // 将请求结果记录到数据库中
        resultService.saveResult(resultIntoDB);
        // 连续两次监控不可用，则报警
        int errorTimes = resultService.errorsTimes(monitorId);
        if (errorTimes == TWO) {
            User user = userDao.queryByUserId(scheduleJob.getUserId());
            try {
                String mess = "您的监控任务【" +scheduleJob.getName() + "】已经连续多次出错，请查看它是否可用";
                mailService.sendEmail(user.getEmail(), WORNING, mess);
            } catch (MonitorException | ParseException e) {
                log.error("发送邮件失败 - " + e.getMessage(), e);
            }
            log.info("连续监控结果为不可用，已警告用户");
        } else if (errorTimes>FIVE){
            log.info("监控任务【" +scheduleJob.getName() + "】连续五次监控结果为不可用，应当使用短信提醒用户");
            //todo 给用户发送短信或拨打电话提醒
        }
        log.info("定时任务请求成功，响应内容 ==> " + JSON.toJSONString(resultIntoDB));
    }
}
