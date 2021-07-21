package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.ClassRoom;
import com.system.demo.bean.Specialty;
import com.system.demo.bean.Student;
import com.system.demo.service.ClassRoomService;
import com.system.demo.service.SpecialtyService;
import com.system.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClassRoomFormController {
    @Autowired
    StudentService studentService;
    @Autowired
    ClassRoomService classRoomService;

    @PostMapping("classSearchStudent")
    public ModelAndView classSearchStudent(@RequestParam(value = "classNo",defaultValue = "null")Long classNo,
                                           ModelAndView modelAndView){
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper = studentQueryWrapper.ge("classNo",classNo);
        List<Student> studentList = studentService.list(studentQueryWrapper);
        modelAndView.addObject("students",studentList);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @GetMapping("/dynamic_classroom")
    public ModelAndView dynamic_classroom(){
        ModelAndView modelAndView = new ModelAndView();
        List<ClassRoom> classRooms= classRoomService.list();
        modelAndView.addObject("classList",classRooms);
        modelAndView.setViewName("table/dynamic_classroom");
        return modelAndView;
    }
}
