package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //返回到增加课程信息界面：
    @RequestMapping("")
    public String swapToPageDynamicStudent(){
        return "redirect:/dynamic_student";
    }
}
