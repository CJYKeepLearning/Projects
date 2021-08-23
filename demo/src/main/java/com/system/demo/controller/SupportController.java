package com.system.demo.controller;

import com.system.demo.bean.Support;
import com.system.demo.bean.Teacher;
import com.system.demo.service.SupportService;
import com.system.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SupportController {
    @Autowired
    SupportService supportService;
    @Autowired
    TeacherService teacherService;
    //返回到增加被赡养人信息界面：
    @RequestMapping("/addSupport.html")
    public String swapToPageAddSupport(){
        return "add/addSupport";
    }
    @PostMapping("/addSupport")
    public ModelAndView addSupport (@RequestParam("name")String name,
                              @RequestParam("relation")String relation,
                              @RequestParam(value = "tId")String tid,
                              HttpServletRequest request,
                              ModelAndView modelAndView){
        if (StringUtils.isEmpty(tid)){
            tid = request.getSession().getAttribute("account").toString();
            modelAndView.setViewName("add/addSupportSelf");
        }else {
            modelAndView.setViewName("add/addSupport");
        }
        Support support = new Support();
        support.setName(name);
        support.setRelation(relation);
        support.setTId(tid);
        supportService.saveOrUpdate(support);
        modelAndView.addObject("msg","添加成功");
        return modelAndView;
    }

    @GetMapping(value = {"/salary_provide.html","returnSalarySupport"})
    public ModelAndView salary_provide(){
        ModelAndView modelAndView = new ModelAndView();
        List<Teacher> teacherList = teacherService.list();
        modelAndView.addObject("teacherList",teacherList);
        List<Support> list = supportService.list();
        modelAndView.addObject("provideList",list);
        modelAndView.setViewName("table/salary_provide");
        return modelAndView;
    }
}
