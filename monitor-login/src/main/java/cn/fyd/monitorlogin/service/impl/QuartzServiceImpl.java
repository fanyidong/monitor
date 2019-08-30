package cn.fyd.monitorlogin.service.impl;

import cn.fyd.model.Monitor;
import cn.fyd.monitorlogin.common.QuartzHttpJob;
import cn.fyd.monitorlogin.service.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务服务实现层
 * @author fanyidong
 * @date Created in 2019-02-12
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    private static Logger log = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private Scheduler scheduler;


    @Override
    public void updateQuartz(Monitor monitor) throws SchedulerException {
        log.info("开始更新定时任务 -> 监控任务名:" + monitor.getName());
        int minute = monitor.getWatchTime() % 60;
        int hour = monitor.getWatchTime() / 60;
        // cron表达式：每hour小时minute分钟执行一次
        String cron = "0 0/"+minute;
        if (hour==0) {
            cron += " * * * ?";
        } else {
            cron += " 0/"+hour+" * * ?";
        }
//        String cron = "0/5 * * * * ?";
        //获取触发器标识
        TriggerKey triggerKey = TriggerKey.triggerKey(monitor.getMonitorId(), monitor.getUserId());
        // 获取触发器trigger
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger==null) {
            // 创建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzHttpJob.class)
                    .withIdentity(monitor.getMonitorId(), monitor.getUserId())
                    .build();
            // 在job中动态传递参数(Monitor对象)
            jobDetail.getJobDataMap().put("monitorId", monitor.getMonitorId());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 按照新的cron表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(monitor.getMonitorId(), monitor.getUserId())
                    .withSchedule(scheduleBuilder)
                    .build();
            // 执行定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 按照新的cron表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            // 按照新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        // 编辑监控任务时，其状态为开启时(新增监控任务时state默认赋值为1)
        if (monitor.getState()==1) {
            // 开启任务
            scheduler.start();
            log.info("开启监控任务 -> " + monitor.getName());
        } else {
            // 若未开启定时任务，先置为暂停
            pauseQuartz(monitor.getMonitorId(), monitor.getUserId());
        }
        log.info("更新定时任务成功 -> 监控任务名:" + monitor.getName());
    }

    @Override
    public void deleteQuartz(String jobName, String jobGroup) throws SchedulerException {
        log.info("开始删除定时任务 -> jobName即monitorId:" + jobName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
        log.info("更新定时任务成功 -> jobName即monitorId:" + jobName);
    }

    @Override
    public void pauseQuartz(String jobName, String jobGroup) throws SchedulerException {
        log.info("开始暂停定时任务 -> jobName即monitorId:" + jobName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
        log.info("暂停定时任务成功 -> jobName即monitorId:" + jobName);
    }

    @Override
    public void resumeQuartz(Monitor monitor) throws SchedulerException {
        log.info("开始恢复定时任务 -> 监控任务名:" + monitor.getName());
        JobKey jobKey = JobKey.jobKey(monitor.getMonitorId(), monitor.getUserId());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail !=null) {
            scheduler.resumeJob(jobKey);
            log.info("恢复定时任务成功 -> 监控任务名:" + monitor.getName());
        } else {
            log.info("恢复定时任务成功 -> 不存在该定时任务，需要重新创建定时任务 -> 监控任务名:" + monitor.getName());
            updateQuartz(monitor);
        }

    }
}
