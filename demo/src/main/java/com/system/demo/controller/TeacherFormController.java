package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController
public class TeacherFormController {
    @GetMapping("/dynamic_teacher")
    public ModelAndView dynamic_teacher(){
        ModelAndView modelAndView = new ModelAndView("table/dynamic_teacher");
        return modelAndView;
    }
}
