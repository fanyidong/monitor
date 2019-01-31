package cn.fyd.monitor.remote;

import cn.fyd.common.Response;
import cn.fyd.model.Monitor;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用远程服务(job)接口类
 * @author fanyidong
 * @date Created in 2019-01-09
 */
@FeignClient("${microService.job}")
@Service
public interface JobRemote {

    /**
     * 增加监控项目
     * @param monitor 实体类
     * @return cn.fyd.common.Response 公用返回类
     */
    @PostMapping("/addMonitor")
    Response addMonitor(@RequestBody Monitor monitor);

    /**
     * 开启/关闭 监控
     * @param monitorId 监控id
     * @param state 启用状态 0-禁用 1-开启
     * @return cn.fyd.common.Response 公用返回类
     */
    @PostMapping("/closeMonitor")
    Response closeMonitor(@RequestParam("monitorId") String monitorId, @RequestParam("state") Integer state);

    /**
     * 编辑监控项目
     * @param monitor 监控实体类
     * @return cn.fyd.common.Response 公用返回类
     */
    @PostMapping("/editMonitor")
    Response editMonitor(@RequestBody Monitor monitor);

    /**
     * 开启/关闭 监控
     * @param userId 用户id
     * @param pageNum 页码
     * @param pageSize 每页数目
     * @return cn.fyd.common.Response 公用返回类
     */
    @PostMapping("/getMonitors")
    Response<PageInfo<Monitor>> getMonitors(@RequestParam("userId") String userId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 删除监控任务
     * @param monitorId 监控任务id
     * @return cn.fyd.common.Response 公用返回类
     */
    @PostMapping("/delMonitor")
    Response delMonitor(@RequestParam("monitorId") String monitorId);
}
