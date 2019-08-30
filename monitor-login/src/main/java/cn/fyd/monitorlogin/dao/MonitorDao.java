package cn.fyd.monitorlogin.dao;

import cn.fyd.model.Monitor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * monitor表操作db接口
 * @author fanyidong
 * @date Created in 2019-01-07
 */
@Repository
public interface MonitorDao {

    /**
     * 保存监控项目
     * @param monitor 监控实体类
     * @return int
     */
    int saveMonitor(Monitor monitor);

    /**
     * 根据条件查询
     * @param monitor 监控实体类
     * @return list 列表
     */
    List<Monitor> queryBySelective(Monitor monitor);

    /**
     * 关闭监控
     * @param monitorId 监控id
     * @param state 启用状态 0-禁用 1-启用
     * @return int 更新的数量
     */
    int closeMonitor(String monitorId, Integer state);

    /**
     * 根据主键id修改监控
     * @param monitor 监控实体类
     * @return int 数量
     */
    int editMonitorByPrimaryKey(Monitor monitor);

    /**
     * 根据主键查询个数
     * @param monitorId 监控实体类
     * @return int 结果个数
     */
    int countByPrimaryKey(String monitorId);

    /**
     * 根据主键查询
     * @param monitor 监控实体类
     * @return cn.fyd.model.Monitor
     */
    Monitor getMonitorBySelective(Monitor monitor);

    /**
     * 根据主键查询(是否重复，排除monitorId)
     * @param monitor 监控实体类
     * @return int
     */
    int countBySelective(Monitor monitor);

    /**
     * 删除监控任务
     * @param monitorId 监控id
     * @return int
     */
    int delMonitorByPrimaryKey(String monitorId);

    /**
     * 根据主键查询监控任务
     * @param monitorId 监控id
     * @return cn.fyd.model.Monitor
     */
    Monitor getMonitorByPrimaryKey(String monitorId);
}
