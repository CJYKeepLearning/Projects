package com.system.demo.controller;

import com.system.demo.bean.Person;
import com.system.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }

    @Autowired
    PersonService personService;
    //前端发送请求
    @PostMapping("/check")
    public ModelAndView login(@RequestParam("account")String account,
                              @RequestParam("password")String password,
                              HttpServletRequest request,
                              ModelAndView model){
        Person person = personService.getById(account);
        if (person==null || !person.getPwd().equals(password)){
            model.addObject("msg","账号密码错误");
            model.setViewName("login");
        }else if (person.getPower()==1){
            model.addObject("power",1);
            model.addObject("id",0);
            model.setViewName("home");
        }else if (person.getPower()==3){
            model.addObject("power",3);
            model.addObject("id",account);
            model.setViewName("studentHome");
        }else{
            model.addObject("power",2);
            model.addObject("id",account);
            model.setViewName("teacherHome");
        }
        request.getSession().setAttribute("account",account);
        return model;
    }
}
