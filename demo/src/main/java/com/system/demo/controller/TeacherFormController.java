package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import com.system.demo.bean.TC;
import com.system.demo.bean.Teach;
import com.system.demo.bean.Teacher;
import com.system.demo.bean.Title;
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
    TitleService titleService;
    @GetMapping("/dynamic_teacher")
    public ModelAndView dynamic_teacher(){
        ModelAndView modelAndView = new ModelAndView("table/dynamic_teacher");
        List<Teacher> list = teacherService.list();
        List<Title> titleList = titleService.list();
        modelAndView.addObject("teachers",list);
        modelAndView.addObject("titles",titleList);
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
    public ModelAndView addOrUpdateTeacher(@RequestParam("id")Long id,@RequestParam("name")String name,
                                           ModelAndView modelAndView){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTname(name);
        teacherService.saveOrUpdate(teacher);
        modelAndView.setViewName("add/addOrUpdateTeacher");
        return modelAndView;
    }

    //返回到教师页面
    @RequestMapping("/returnDynamicTeacher")
    public ModelAndView returnDynamicTeacher(ModelAndView modelAndView){
        modelAndView.setViewName("table/dynamic_teacher");
        List<Teacher> list = teacherService.list();
        modelAndView.addObject("teachers",list);
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
}
