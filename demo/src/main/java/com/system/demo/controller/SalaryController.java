package com.system.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class SalaryController {
    @GetMapping("/salary")
    public ModelAndView salaryPage(ModelAndView modelAndView){
        modelAndView.setViewName("pages/salary");
        return modelAndView;
    }

}
