package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.ClassRoom;
import com.system.demo.bean.Specialty;
import com.system.demo.service.ClassRoomService;
import com.system.demo.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SpecialtyFormController {
    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    SpecialtyService specialtyService;
    @PostMapping("spSearchClass")
    public ModelAndView spSearchClass(@RequestParam("id")Long id,ModelAndView modelAndView){
        QueryWrapper<ClassRoom> classRoomQueryWrapper = new QueryWrapper<>();
        classRoomQueryWrapper = classRoomQueryWrapper.ge("id",id);
        List<ClassRoom> classRooms = classRoomService.list(classRoomQueryWrapper);
        modelAndView.addObject("classes",classRooms);
        modelAndView.setViewName("");
        return modelAndView;
    }
    @RequestMapping(value = "/dynamic_specialty")
    public ModelAndView dynamic_specialty(){
        ModelAndView modelAndView = new ModelAndView();
        List<Specialty> specialties = specialtyService.list();
        modelAndView.addObject("specialtyList",specialties);
        modelAndView.setViewName("table/dynamic_specialty");
        return modelAndView;
    }
}
