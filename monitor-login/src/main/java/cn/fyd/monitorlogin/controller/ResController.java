package cn.fyd.monitorlogin.controller;

import cn.fyd.annotation.Log;
import cn.fyd.common.Response;
import cn.fyd.monitorlogin.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.fyd.common.Constant.EMPTY_PARAMS;
import static cn.fyd.common.Constant.ONE;

/**
 * 统计监控结果的控制层
 * @author fanyidong
 * @date Created in 2019-02-21
 */
@RestController
public class ResController {

    @Autowired
    private ResultService resultService;

    @Log(name = "获取首页统计信息")
    @PostMapping("getStat")
    public Response getStat(String userId, Integer num) {
        // 验证参数是否为空
        if (StringUtils.isEmpty(userId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        return Response.success(resultService.getStatByUserId(userId, num));
    }

    @Log(name = "获取单个监控任务的监控结果列表")
    @PostMapping("getMonitorRes")
    public Response getMonitorRes(String monitorId, Integer pageNum, Integer pageSize) {
        // 验证参数是否为空
        if (StringUtils.isEmpty(pageNum)) {
            // 默认pageNum显示第一页
            pageNum = ONE;
        }
        return Response.success(resultService.getResultByMonitorId(monitorId, pageNum, pageSize));
    }
}
