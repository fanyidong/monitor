package cn.fyd.monitorlogin.service;

import cn.fyd.common.MonitorException;
import cn.fyd.model.Monitor;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Monitor服务层
 * @author fanyidong
 * @date Created in 2019-01-07
 */
public interface MonitorService {

    /**
     * 保存监控项目
     * @param monitor 监控项目实体类
     * @return int 数量
     * @throws MonitorException 自定义异常
     * @throws SchedulerException 定时任务异常
     */
    int saveMonitor(Monitor monitor) throws MonitorException, SchedulerException;

    /**
     * 关闭/开启监控项目
     * @param monitorId 监控id
     * @param state 启用状态 0-禁用 1-启用
     * @return int 数量
     * @throws MonitorException 自定义异常
     * @throws SchedulerException 定时任务异常
     */
    int closeMonitor(String monitorId, Integer state) throws MonitorException, SchedulerException;

    /**
     * 编辑监控项目
     * @param monitor 监控项目实体类
     * @return int 数量
     * @throws MonitorException 自定义异常
     * @throws SchedulerException 定时任务异常
     */
    int editMonitor(Monitor monitor) throws MonitorException, SchedulerException;

    /**
     * 根据userId查询该用户创建的所有监控项目
     * @param userId 用户id
     * @param pageNum 页码
     * @param pageSize 每页数目
     * @return list 监控项目列表
     */
    PageInfo<Monitor> getMonitors(String userId, Integer pageNum, Integer pageSize);

    /**
     * 根据userId查询该用户创建的所有监控项目
     * @param userId 用户id
     * @return list 监控项目列表
     */
    List<Monitor> getMonitorsByUserId(String userId);

    /**
     * 删除监控任务(硬删除)
     * @param monitorId 监控任务id
     * @return int
     * @throws SchedulerException 定时任务异常
     * @throws MonitorException 自定义异常
     */
    int delMonitor(String monitorId) throws SchedulerException, MonitorException;

    /**
     * 根据主键查询监控任务
     * @param monitorId 监控id
     * @return cn.fyd.model.Monitor
     */
    Monitor getMonitorByPrimaryKey(String monitorId);
}
