package com.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SPA 路由控制器
 * 将所有非 API 请求转发到 index.html，支持 Vue Router history 模式
 */
@Controller
public class SpaController {

    /**
     * 匹配所有非 /api/ 和非静态资源的路径，转发到 index.html
     */
    @RequestMapping(value = {
            "/{path:[^\\.]*}",
            "/{path:^(?!api).*}/**/{path:[^\\.]*}"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
