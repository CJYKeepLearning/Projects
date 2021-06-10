package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.Course;
import com.system.demo.bean.Exam;
import com.system.demo.service.CourseService;
import com.system.demo.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Slf4j
@Controller
public class CourseFormController {
    @Autowired
    CourseService courseService;

    @Autowired
    ExamService examService;

    //课程信息,还没加接口跳转到课程主页面！
    //显示全部课程信息:使用ModelAndView
    @GetMapping("/dynamic_course")
    public ModelAndView dynamic_course(){
        ModelAndView modelAndView = new ModelAndView();
        List<Course> courses = courseService.list();
        modelAndView.addObject("courses",courses);
        modelAndView.setViewName("table/dynamic_course");
        return modelAndView;
    }


    //增加课程信息：
    @PostMapping("/addCourse")
    public String addCourse(@RequestParam(value = "id",defaultValue = "-1")Long id,
                             @RequestParam(value = "cname")String cname,
                            @RequestParam(value = "credit")Long credit,
                            @RequestParam(value = "weekhours")Long weekhours,
                             Model model){
        if (id>0 && id<=100){
            Course course = new Course(id,cname,credit,weekhours);
            if(courseService.getById(id)!=null){
                model.addAttribute("msg","id已经存在!添加失败");
            }else{
                courseService.save(course);
                model.addAttribute("msg","添加成功");
            }
        }else {
            model.addAttribute("msg","id不合法!");
        }
        return "add/addCourse";
    }

    //返回到增加课程信息界面：
    @RequestMapping("/addCourse.html")
    public String swapToPageAddCou(){
        return "add/addCourse";
    }
    //返回到returnDynamicCourse
    @RequestMapping("/returnDynamicCourse")
    public String swapToPageDyCou(){
        return  "redirect:/dynamic_course";
    }
    //返回到增加课程信息界面：
    @RequestMapping("/updateCourse.html")
    public String swapToPageUpdCou(){
        return "update/updateCourse";
    }
    //更新课程信息：
    @PostMapping("/updateCourse")
    public String updateCourse(@RequestParam(value = "id",defaultValue = "-1")Long id,
                                @RequestParam(value = "cname")String cname,
                                @RequestParam(value = "credit")Long credit,
                                @RequestParam(value = "weekhours")Long weekhours,
                                Model model){
        if(id>0 && id<=100){
            Course course = new Course(id,cname,credit,weekhours);
            boolean isUpdate = courseService.updateById(course);
            if (isUpdate){
                model.addAttribute("msg","更新成功");
            }else {
                model.addAttribute("msg","id不存在！更新失败");
            }
        }else{
            model.addAttribute("msg","id不合法!请重新提交!");
        }
        return "update/updateCourse";
    }

    //删除课程信息
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id,Model model){
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.ge("cno",id);
        List<Exam> list = examService.list(queryWrapper);
        if(list.size()!=0){
            model.addAttribute("msg","该课已有学生选修，不能删除！");
        }else{
            courseService.removeById(id);
        }
        return "redirect:/dynamic_course";
    }

}
