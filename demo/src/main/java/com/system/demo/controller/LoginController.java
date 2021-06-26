package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }

    //前端发送请求
    @PostMapping("")
    public String login(@RequestParam("account")String account,
                        @RequestParam("password")String password,
                        Model model){
        if (!account.equals("admin") && !password.equals("admin")){
            model.addAttribute("msg","账号密码错误");
        }
        return "home";
    }
}
