package cn.fyd.monitorlogin.controller;

import cn.fyd.annotation.Log;
import cn.fyd.common.MonitorException;
import cn.fyd.common.Response;
import cn.fyd.common.ValidFileds;
import cn.fyd.model.Monitor;
import cn.fyd.monitorlogin.service.MonitorService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static cn.fyd.common.Constant.EMPTY_PARAMS;
import static cn.fyd.common.Constant.MONITOR_NOT_EXIST;

/**
 * monitor表controller层
 * @author fanyidong
 * @date Created in 2019-01-08
 */
@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @Log(name = "增加监控任务")
    @PostMapping("/addMonitor")
    @Transactional(rollbackFor = Exception.class)
    public Response addMonitor(@RequestBody Monitor monitor) throws MonitorException, SchedulerException {
        // 验证参数是否为空
        ValidFileds.verificationoColumn(monitor);
        return Response.success(monitorService.saveMonitor(monitor));
    }

    @Log(name = "关闭/开启监控任务")
    @PostMapping("/closeMonitor")
    @Transactional(rollbackFor = Exception.class)
    public Response closeMonitor(String monitorId, Integer state) throws MonitorException, SchedulerException {
        // 验证监控id是否为空
        if (StringUtils.isEmpty(monitorId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        return Response.success(monitorService.closeMonitor(monitorId, state));
    }

    @Log(name = "编辑监控任务")
    @PostMapping("/editMonitor")
    @Transactional(rollbackFor = Exception.class)
    public Response editMonitor(@RequestBody Monitor monitor) throws MonitorException, SchedulerException {
        // 验证监控id是否为空
        if (StringUtils.isEmpty(monitor.getMonitorId())) {
            return Response.failed(EMPTY_PARAMS);
        }
        // 验证参数是否为空
        ValidFileds.verificationoColumn(monitor);
        return Response.success(monitorService.editMonitor(monitor));
    }

    @Log(name = "获取该用户的所有监控任务列表")
    @PostMapping("/getMonitors")
    @Transactional(rollbackFor = Exception.class)
    public Response getMonitors(String userId, Integer pageNum, Integer pageSize) {
        // 验证userId是否为空
        if (StringUtils.isEmpty(userId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        return Response.success(monitorService.getMonitors(userId, pageNum, pageSize));
    }

    @Log(name = "删除监控任务")
    @PostMapping("/delMonitor")
    @Transactional(rollbackFor = Exception.class)
    public Response delMonitor(String monitorId) throws SchedulerException, MonitorException {
        // 验证monitorId是否为空
        if (StringUtils.isEmpty(monitorId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        return Response.success(monitorService.delMonitor(monitorId));
    }

    @Log(name = "获取单个监控任务详情")
    @PostMapping("/getMonitor")
    @Transactional(rollbackFor = Exception.class)
    public Response getMonitor(String monitorId) throws MonitorException {
        // 验证monitorId是否为空
        if (StringUtils.isEmpty(monitorId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        Monitor res = monitorService.getMonitorByPrimaryKey(monitorId);
        if (res==null) {
            throw new MonitorException(MONITOR_NOT_EXIST);
        }
        return Response.success(res);
    }
}
