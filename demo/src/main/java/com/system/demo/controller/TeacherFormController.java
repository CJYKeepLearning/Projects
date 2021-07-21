package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import com.system.demo.bean.*;
import com.system.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RestController
public class TeacherFormController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    TCService tcService;
    @Autowired
    TeachService teachService;
    @Autowired
    SupportService supportService;

    @Autowired
    TitleService titleService;
    @GetMapping(value = {"/dynamic_teacher","/returnDynamicTeacher"})
    public ModelAndView dynamic_teacher(){
        ModelAndView modelAndView = new ModelAndView();
        List<Teacher> list = teacherService.list();
        List<Title> titleList = titleService.list();
        modelAndView.addObject("teachers",list);
        modelAndView.addObject("titles",titleList);
        modelAndView.setViewName("table/dynamic_teacher");
        return modelAndView;
    }
    //跳转到增加或更新教师信息页面
    @GetMapping("addOrUpdateTeacher.html")
    public ModelAndView swapToPageAddOrUpdateTeacher(ModelAndView modelAndView){
        modelAndView.setViewName("add/addOrUpdateTeacher");
        return modelAndView;
    }

    //Post增加教师
    @PostMapping("addOrUpdateTeacher")
    public ModelAndView addOrUpdateTeacher(@RequestParam("id")String id,
                                           @RequestParam("name")String name,
                                           @RequestParam(value = "title",defaultValue ="null")Long title,
                                           @RequestParam(value = "email",defaultValue ="null")Long email,
                                           @RequestParam(value = "city",defaultValue = "null")String city,
                                           @RequestParam(value = "area",defaultValue = "null")String area,
                                           @RequestParam(value = "code",defaultValue = "null")String code,
                                           @RequestParam(value = "phone",defaultValue = "null")String phone,
                                           ModelAndView modelAndView){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTname(name);
        teacher.setEmail(email);
        teacher.setCity(city);
        teacher.setArea(area);
        teacher.setCode(code);
        if (title!=null){
            Title title1 = titleService.getById(title);
            if (title1!=null)
                teacher.setTitle(title);
            else
                modelAndView.addObject("msg","职称输入不合法");
        }
        teacherService.saveOrUpdate(teacher);
        modelAndView.setViewName("add/addOrUpdateTeacher");
        return modelAndView;
    }
    //删除教师信息
    @GetMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable("id")Long id){
        teacherService.removeById(id);
        //置空teach表中内容
        QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TC> tcQueryWrapper = new QueryWrapper<>();
        teachQueryWrapper = teachQueryWrapper.ge("tno",id);
        List<Teach> teachList = teachService.list(teachQueryWrapper);
        List<TC> tcList = tcService.list(tcQueryWrapper);
        for (int i=0;i<tcList.size();i++){
            Long tcId = tcList.get(i).getId();
            tcService.removeById(tcId);
        }
        for (int i=0;i<teachList.size();i++){
            Long teachId = teachList.get(i).getId();
            Teach teach = new Teach(teachId,teachList.get(i).getSno(),teachList.get(i).getCno(),teachList.get(i).getGrade(),null);
            teachService.saveOrUpdate(teach);
        }
        return "redirect:/dynamic_teacher";
    }

    //////////////////////////////
    //第二个
    @GetMapping("/salary_teacher.html")
    public ModelAndView secondTeacher(){
        ModelAndView modelAndView = new ModelAndView();
        List<Teacher> list = teacherService.list();
        List<Title> titleList = titleService.list();
        modelAndView.addObject("teachers",list);
        modelAndView.addObject("titles",titleList);
        modelAndView.setViewName("table/salary_teacher");
        return modelAndView;
    }


}
