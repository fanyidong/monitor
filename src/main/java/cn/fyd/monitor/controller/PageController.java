package cn.fyd.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 获取页面的控制层
 * @author fanyidong
 * @date Created in 2019-01-14
 */
@Controller
public class PageController {

    @GetMapping("/login.do")
    public String getIndex() {
        return "login";
    }

    @GetMapping("/regist.do")
    public String getRegist() {
        return "regist";
    }

    @GetMapping("/forgot.do")
    public String getForgot() {
        return "forgot";
    }

    @GetMapping("/main.do")
    public String getMainPage() {
        return "main";
    }
}
