package cn.fyd.monitorlogin.service;

import cn.fyd.model.Result;
import cn.fyd.model.Stat;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 监控结果日志服务接口
 * @author fanyidong
 * @date Created in 2019-02-21
 */
public interface ResultService {

    /**
     * 保存http请求结果
     * @param result
     * @return insert个数
     */
    int saveResult(Result result);

    /**
     * 根据monitorId查询其所有监控结果(order by createTime desc)
     * @param monitorId 监控id
     * @return list
     */
    List<Result> getResultsByMonitorId(String monitorId);

    /**
     * 根据userId获取统计结果
     * @param userId 用户id
     * @param num 查询可用率和平均响应时间的最大数目(如为null则查询全部)
     * @return statictisc实体类
     */
    Stat getStatByUserId(String userId, Integer num);

    /**
     * 获取监控任务的连续出错次数
     * @param monitorId 监控id
     * @return int
     */
    int errorsTimes(String monitorId);

    /**
     * 根据monitorId获取该监控任务的监控结果
     * @param monitorId 监控任务id
     * @param pageNum 当前页数
     * @param pageSize 每页数目
     * @return
     */
    PageInfo<Result> getResultByMonitorId(String monitorId, Integer pageNum, Integer pageSize);
}
