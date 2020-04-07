package com.huangxj.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ApplicationController
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-09-24 19:46
 * @Version V1.0
 **/
@Controller
public class ApplicationController {
    @GetMapping("/")
    public String ui() {
        return "forward:/admin/index.html";
    }

    @GetMapping("admin")
    public String admin() {
        return "forward:/admin/index.html";
    }

}
