package cn.fyd.monitorlogin.dao;

import cn.fyd.model.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fanyidong
 * @date Created in 2019-02-13
 */
@Repository
public interface ResultDao {

    /**
     * 保存http请求结果
     * @param result
     * @return insert个数
     */
    int saveResult(Result result);

    /**
     * 根据monitorId查询其所有监控结果(group by monitorId order by createTime desc)
     * @param monitorId 监控id
     * @return list
     */
    List<Result> getResultsByMonitorId(@Param("monitorId") String monitorId);

    /**
     * 根据monitorId删除监控结果
     * @param monitorId
     */
    void delResultsByMonitorId(@Param("monitorId") String monitorId);
}
