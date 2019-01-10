package cn.fyd.monitor.controller;

import cn.fyd.common.Response;
import cn.fyd.model.Monitor;
import cn.fyd.model.User;
import cn.fyd.monitor.remote.JobRemote;
import cn.fyd.monitor.remote.LoginRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.fyd.common.Constant.*;

/**
 * job子服务控制层
 * @author fanyidong
 * @date Created in 2019-01-09
 */
@RestController
public class JobController {

    private static Logger logger = LogManager.getLogger(JobController.class);

    @Autowired
    private JobRemote jobRemote;
    @Autowired
    private LoginRemote loginRemote;

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

    @PostMapping("/closeMonitor")
    public String closeMonitor(String monitorId, Integer state, HttpServletRequest request) {
        Response response = jobRemote.closeMonitor(monitorId, state);
        return response.toString();
    }

    @PostMapping("/editMonitor")
    public String editMonitor(Monitor monitor, HttpServletRequest request) {
        Response response = jobRemote.editMonitor(monitor);
        return response.toString();
    }

    @PostMapping("/getMonitors")
    public String getMonitors(String userId, HttpServletRequest request) {
        Response<List<Monitor>> response = jobRemote.getMonitors(userId);
        if (response.getData().size() == 0) {
            response.setMessage(RESPONSE_EMPTY);
        }
        return response.toString();
    }
}
