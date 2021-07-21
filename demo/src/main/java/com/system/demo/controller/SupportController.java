package com.system.demo.controller;

import com.system.demo.bean.Support;
import com.system.demo.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SupportController {
    @Autowired
    SupportService supportService;
    //返回到增加被赡养人信息界面：
    @RequestMapping("/addSupport.html")
    public String swapToPageAddSupport(){
        return "add/addSupport";
    }
    @PostMapping("/addSupport")
    public String addSupport (@RequestParam("name")String name,
                              @RequestParam("relation")String relation,
                              @RequestParam(value = "tId",defaultValue = "-1")Long tid,
                              ModelAndView modelAndView){
        if (tid.equals(-1)){
            modelAndView.addObject("msg","输入错误");
            return "";
        }
        Support support = new Support();
        support.setName(name);
        support.setRelation(relation);
        support.setTId(tid);
        supportService.saveOrUpdate(support);
        return "";
    }

    @GetMapping(value = {"/salary_provide.html","returnsalarySupport"})
    public ModelAndView salary_provide(){
        ModelAndView modelAndView = new ModelAndView();
        List<Support> list = supportService.list();
        modelAndView.addObject("ProvideList",list);
        modelAndView.setViewName("table/salary_provide");
        return modelAndView;
    }
}
