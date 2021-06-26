package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class TitleFormController {
    @Autowired
    TitleService titleService;
    @Autowired
    TeacherTitleService teacherTitleService;
    //显示title
    @GetMapping("/dynamic_title")
    public ModelAndView dynamicTitle(ModelAndView modelAndView){
        List<Title> titleList = titleService.list();
        modelAndView.addObject("titleList",titleList);
        modelAndView.setViewName("table/dynamic_title");
        return modelAndView;
    }
    //删除Title
    @GetMapping("/deleteTitle/{id}")
    public ModelAndView deleteTitle(@PathVariable("id")Long id,ModelAndView modelAndView){
        Title title = titleService.getById(id);
        QueryWrapper<TeacherTitle> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.ge("title_id",title.getId());
        List<TeacherTitle> teacherTitleList = teacherTitleService.list(queryWrapper);
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
        titleService.saveOrUpdate(title);
        modelAndView.addObject("msg","操作成功!");
        modelAndView.setViewName("add/addTitle");
        return modelAndView;
    }
}
