package com.system.demo.controller;

import com.system.demo.bean.Teacher;
import com.system.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RestController
public class TeacherFormController {
    @Autowired
    TeacherService teacherService;
    @GetMapping("/dynamic_teacher")
    public ModelAndView dynamic_teacher(){
        ModelAndView modelAndView = new ModelAndView("table/dynamic_teacher");
        List<Teacher> list = teacherService.list();
        modelAndView.addObject("teachers",list);
        return modelAndView;
    }
    //跳转到增加或更新教师信息页面
    @GetMapping("addOrUpdateTeacher.html")
    public ModelAndView swapToPageAddOrUpdateTeacher(ModelAndView modelAndView){
        modelAndView.setViewName("add/addOrUpdateTeacher");
        return modelAndView;
    }
    @PostMapping("addTeacher")
    public String addTeacher(@RequestParam("id")Long id,@RequestParam("tname")String tname){
        Teacher teacher = new Teacher(id,tname);
        teacherService.saveOrUpdate(teacher);
        return "add/addOrUpdateTeacher";
    }
}
