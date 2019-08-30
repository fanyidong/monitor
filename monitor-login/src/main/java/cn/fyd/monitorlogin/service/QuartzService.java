package cn.fyd.monitorlogin.service;

import cn.fyd.model.Monitor;
import org.quartz.SchedulerException;

/**
 * 定时任务服务层
 * @author fanyidong
 * @date Created in 2019-02-12
 */
public interface QuartzService {

    /**
     * 任务创建与更新
     * @param monitor 监控类
     * @throws SchedulerException
     */
    void updateQuartz(Monitor monitor) throws SchedulerException;

    /**
     * 删除任务
     * @param jobName 任务名monitorId
     * @param jobGroup 任务组userId
     * @throws SchedulerException
     */
    void deleteQuartz(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复任务
     * @param jobName 任务名monitorId
     * @param jobGroup 任务组userId
     * @throws SchedulerException
     */
    void pauseQuartz(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复任务
     * @param monitor
     * @throws SchedulerException
     */
    void resumeQuartz(Monitor monitor) throws SchedulerException;


}
