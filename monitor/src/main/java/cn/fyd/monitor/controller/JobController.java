package cn.fyd.monitor.controller;

import cn.fyd.annotation.IsLogin;
import cn.fyd.annotation.Log;
import cn.fyd.common.Response;
import cn.fyd.model.Monitor;
import cn.fyd.model.Result;
import cn.fyd.model.Stat;
import cn.fyd.model.User;
import cn.fyd.monitor.remote.JobRemote;
import cn.fyd.monitor.remote.LoginRemote;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static cn.fyd.common.Constant.USER_NOT_EXIST;

/**
 * job子服务控制层
 * @author fanyidong
 * @date Created in 2019-01-09
 */
@RestController
public class JobController {

    @Autowired
    private JobRemote jobRemote;
    @Autowired
    private LoginRemote loginRemote;

    @IsLogin
    @PostMapping("/addMonitor")
    public String addMonitor(Monitor monitor, HttpServletRequest request) {
        // 根据userId查询该用户是否存在
        Response<User> userRes = loginRemote.userInfo(monitor.getUserId());
        if (userRes.getData() == null) {
            userRes.setMessage(USER_NOT_EXIST);
            return userRes.toString();
        }
        Response response = jobRemote.addMonitor(monitor);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/closeMonitor")
    public String closeMonitor(String monitorId, Integer state, HttpServletRequest request) {
        Response response = jobRemote.closeMonitor(monitorId, state);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/editMonitor")
    public String editMonitor(Monitor monitor, HttpServletRequest request) {
        Response response = jobRemote.editMonitor(monitor);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/getMonitors")
    public String getMonitors(String userId, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Response<PageInfo<Monitor>> response = jobRemote.getMonitors(userId, pageNum, pageSize);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/delMonitor")
    public String delMonitor(String monitorId, HttpServletRequest request) {
        Response response = jobRemote.delMonitor(monitorId);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/getMonitor")
    public String getMonitor(String monitorId, HttpServletRequest request) {
        Response<Monitor> response = jobRemote.getMonitor(monitorId);
        return response.toString();
    }

    @IsLogin
    @PostMapping("/getStat")
    public String getStat(String userId, HttpServletRequest request) {
        Response<Stat> response = jobRemote.getStat(userId);
        // 如果返回结果是null，需要赋值errorsInHours列表
        if (response.getData() == null) {
            response.setData(new Stat());
        }
        return response.toString();
    }

    @IsLogin
    @PostMapping("/getMonitorRes")
    public String getMonitorRes(String monitorId, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Response<PageInfo<Result>> response = jobRemote.getMonitorRes(monitorId, pageNum, pageSize);
        return response.toString();
    }
}
