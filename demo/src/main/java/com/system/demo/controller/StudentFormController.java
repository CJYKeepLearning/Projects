package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.Student;
import com.system.demo.bean.Teach;
import com.system.demo.service.CourseService;
import com.system.demo.service.StudentService;
import com.system.demo.service.TeachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class StudentFormController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    TeachService teachService;


    //显示全部学生信息:使用ModelAndView
    @GetMapping("/dynamic_student")
    public ModelAndView dynamic_student(){
        ModelAndView modelAndView = new ModelAndView();
        List<Student> students = studentService.list();
        modelAndView.addObject("students",students);
        modelAndView.setViewName("table/dynamic_student");
        return modelAndView;
    }


    //增加学生信息：
    @PostMapping("/addStudent")
    public String addStudent(@RequestParam(value = "id",defaultValue = "-1")Long id,
                             @RequestParam(value = "name",defaultValue = "-1")String name,
                             @RequestParam(value = "sex",defaultValue = "-1")String sex,
                             @RequestParam(value = "age",defaultValue = "-1")Long age,
                             Model model){
        if (id!=-1 && !name.equals("-1") && !sex.equals("-1") &&(sex.equals("男")||sex.equals("女")) && age!=-1){
            Student student = new Student(id,name,sex,age);
            if(studentService.getById(id)!=null){
                model.addAttribute("msg","id已经存在!添加失败");
            }else{
                studentService.save(student);
                model.addAttribute("msg","添加成功");
            }
        }else {
            model.addAttribute("msg","输入数据不合法");
        }
        return "add/addStudent";
    }

    //返回到增加学生信息界面：
    @RequestMapping("/addStudent.html")
    public String swapToPageAddStu(){
        return "add/addStudent";
    }
    //返回到returnDynamicStudent
    @RequestMapping("/returnDynamicStudent")
    public String swapToPageDyStu(){
        return  "redirect:/dynamic_student";
    }

    //返回到更新学生信息界面：
    @RequestMapping("/updateStudent.html")
    public String swapToPageUpdStu(){
        return "update/updateStudent";
    }
    //更新学生信息：
    @PostMapping("/updateStudent")
    public String updateStudent(@RequestParam(value = "id",defaultValue = "-1")Long id,
                                @RequestParam(value = "name",defaultValue = "-1")String name,
                                @RequestParam(value = "sex",defaultValue = "-1")String sex,
                                @RequestParam(value = "age",defaultValue = "-1")Long age,
                                Model model){
        if(id>0 && id<=100 && !name.equals("-1") && !sex.equals("-1") &&(sex.equals("男")||sex.equals("女")) && age!=-1){
            Student student = new Student();
            student.setId(id);
            student.setSname(name);
            boolean isUpdate = studentService.updateById(student);
            if (isUpdate){
                model.addAttribute("msg","更新成功");
            }else {
                model.addAttribute("msg","id不存在！更新失败");
            }
        }else{
            model.addAttribute("msg","数据不合法!请重新提交!");
        }
        return "update/updateStudent";
    }


    //删除学生信息
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id")Long id){
        studentService.removeById(id);
        //删除teach表中的信息
        QueryWrapper<Teach> teachQueryWrapper  = new QueryWrapper<>();
        teachQueryWrapper = teachQueryWrapper.ge("sno",id);
        List<Teach> teachList = teachService.list(teachQueryWrapper);
        for (int i=0;i<teachList.size();i++){
            id = teachList.get(i).getId();
            teachService.removeById(id);
        }
        return "redirect:/dynamic_student";
    }

}
