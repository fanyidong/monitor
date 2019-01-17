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

    @GetMapping("/index")
    public String getIndex(String page) {
        return page;
    }

    @GetMapping("/regist")
    public String getRegist(String page) {
        return page;
    }
}
