package cn.fyd.monitor.controller;

import cn.fyd.annotation.Log;
import cn.fyd.common.Response;
import cn.fyd.model.Monitor;
import cn.fyd.model.User;
import cn.fyd.monitor.remote.JobRemote;
import cn.fyd.monitor.remote.LoginRemote;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static cn.fyd.common.Constant.RESPONSE_EMPTY;
import static cn.fyd.common.Constant.USER_NOT_EXIST;

/**
 * job子服务控制层
 * @author fanyidong
 * @date Created in 2019-01-09
 */
@RestController
public class JobController {

    private static Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobRemote jobRemote;
    @Autowired
    private LoginRemote loginRemote;

    @Log(name = "addMonitor", type = "modify")
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

    @Log(name = "closeMonitor", type = "modify")
    @PostMapping("/closeMonitor")
    public String closeMonitor(String monitorId, Integer state, HttpServletRequest request) {
        Response response = jobRemote.closeMonitor(monitorId, state);
        return response.toString();
    }

    @Log(name = "editMonitor", type = "modify")
    @PostMapping("/editMonitor")
    public String editMonitor(Monitor monitor, HttpServletRequest request) {
        Response response = jobRemote.editMonitor(monitor);
        return response.toString();
    }

    @Log(name = "getMonitors", type = "query")
    @PostMapping("/getMonitors")
    public String getMonitors(String userId, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Response<PageInfo<Monitor>> response = jobRemote.getMonitors(userId, pageNum, pageSize);
        if (response.getData().getList().size() == 0) {
            response.setMessage(RESPONSE_EMPTY);
        }
        return response.toString();
    }
}
