package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //返回到增加课程信息界面：
    @RequestMapping("/home")
    public String swapToPageHome(){
        return "home";
    }
    @RequestMapping(value = {"/studentHome","/updateSelf/returnStudentHome"})
    public String swapToPageStudentHome(){
        return "studentHome";
    }
    @RequestMapping("/teacherHome")
    public String swapToPageTeacherHome(){
        return "teacherHome";
    }
}
