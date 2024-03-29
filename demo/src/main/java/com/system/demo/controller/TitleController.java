package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.Teacher;
import com.system.demo.bean.TeacherTitle;
import com.system.demo.bean.Title;
import com.system.demo.service.TeacherService;
import com.system.demo.service.TeacherTitleService;
import com.system.demo.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TitleController {
    @Autowired
    TitleService titleService;
    @Autowired
    TeacherService teacherService;
    //显示title
    @GetMapping(value = {"/dynamic_title","salary_title.html"})
    public ModelAndView dynamicTitle(ModelAndView modelAndView){
        List<Title> titleList = titleService.list();
        modelAndView.addObject("titleList",titleList);
        modelAndView.setViewName("table/dynamic_title");
        return modelAndView;
    }
    //删除Title
    @GetMapping("/deleteTitle/{id}")
    public ModelAndView deleteTitle(@PathVariable("id")String id,ModelAndView modelAndView){
        Title title = titleService.getById(id);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("title",title.getId());
        List<Teacher> teacherTitleList = teacherService.list(queryWrapper);
        if (teacherTitleList.size()!=0){
            modelAndView.addObject("msg","存在教师为此职称，删除失败!");
        }else {
            modelAndView.addObject("msg","删除成功");
            titleService.removeById(id);
        }
        modelAndView.setViewName("table/dynamic_title");
        List<Title> titleList = titleService.list();
        modelAndView.addObject("titleList",titleList);
        return modelAndView;
    }
    //转到增加页面
    @RequestMapping("/addTitle.html")
    public String swageToAddTitle(){
        return "add/addTitle";
    }
    //返回到显示的页面
    @RequestMapping("returnDynamicTitle")
    public String returnDynamicTitle(){
        return  "redirect:/dynamic_title";
    }
    //增加或者更新Title
    @PostMapping("addTitle")
    public ModelAndView addTitle(@RequestParam("titleName")String titleName,
                                 @RequestParam("wage")Long wage,
                                 @RequestParam(value = "accumulationFund",defaultValue = "null")Long accumulationFund,
                                 ModelAndView modelAndView){
        Title title = new Title();
        QueryWrapper<Title> titleQueryWrapper = new QueryWrapper<>();
        titleQueryWrapper = titleQueryWrapper.ge("title_name",titleName);
        List<Title> titleList= titleService.list(titleQueryWrapper);
        if (titleList.size()!=0){
            title.setId(titleList.get(0).getId());
        }
        title.setTitleName(titleName);
        title.setWage(wage);
        title.setAccumulationFund(accumulationFund);
        titleService.saveOrUpdate(title);
        modelAndView.addObject("msg","操作成功!");
        modelAndView.setViewName("add/addTitle");
        return modelAndView;
    }
}
