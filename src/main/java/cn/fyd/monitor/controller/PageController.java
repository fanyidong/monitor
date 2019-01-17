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

    @GetMapping("/index.do")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/regist.do")
    public String getRegist() {
        return "regist";
    }

    @GetMapping("/main.do")
    public String getMainPage() {
        return "main";
    }
}
