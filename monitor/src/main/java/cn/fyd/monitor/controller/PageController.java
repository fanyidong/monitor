package cn.fyd.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 获取页面的控制层
 * @author fanyidong
 * @date Created in 2019-01-14
 */
@Controller
public class PageController {

    /**
     * 请求页面控制层
     * @param url
     * @return 页面
     */
    @GetMapping("/{url}")
    public String getPage(@PathVariable("url") String url) {
        // 存在的thymeleaf模板
        String pages = "login,regist,forgot,main,user,manage,apply,edit,resInfo";
        if (pages.contains(url)) {
            return url;
        } else {
            return "error";
        }
    }
}
