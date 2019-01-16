package cn.fyd.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fanyidong
 * @date Created in 2019-01-14
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String getPage(String page) {
        return page;
    }
}
