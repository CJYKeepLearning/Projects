package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeachSysController {

    @GetMapping(value = {"/teachSys"})
    public ModelAndView teachPage(ModelAndView modelAndView){
        modelAndView.setViewName("pages/teachSys");
        return modelAndView;
    }
}
