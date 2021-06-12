package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.system.demo.bean.Exam;
import com.system.demo.bean.Student;
import com.system.demo.mapper.ExamMapper;
import com.system.demo.service.CourseService;
import com.system.demo.service.ExamService;
import com.system.demo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class StudentFormController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExamService examService;

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
                             @RequestParam("name")String name,
                             Model model){
        if (id!=-1){
            Student student = new Student();
            student.setId(id);
            student.setSname(name);
            if(studentService.getById(id)!=null){
                model.addAttribute("msg","id已经存在!添加失败");
            }else{
                studentService.save(student);
                model.addAttribute("msg","添加成功");
            }
        }else {
            model.addAttribute("msg","id不能为空");
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
    //返回到增加学生信息界面：
    @RequestMapping("/updateStudent.html")
    public String swapToPageUpdStu(){
        return "update/updateStudent";
    }
    //更新学生信息：
    @PostMapping("/updateStudent")
    public String updateStudent(@RequestParam(value = "id",defaultValue = "-1")Long id,
                             @RequestParam("name")String name,
                             Model model){
        if(id>0 && id<=100){
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
         model.addAttribute("msg","id不合法!请重新提交!");
        }
        return "update/updateStudent";
    }
    @GetMapping("/addSC.html")
    public String swapToPageAddSC(){
        return "add/addSC";
    }
    @PostMapping("/addSC")
    public String addSC(@RequestParam("sno")Long sno, @RequestParam("cno")Long cno,
                        @RequestParam("grade")Long grade, Model model){
        //判断是否存在sno,cno
        if (studentService.getById(sno)!=null && courseService.getById(cno)!=null){
            Exam exam = new Exam(null,sno,cno,grade);
            QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
            queryWrapper = queryWrapper.ge("sno",sno);
            queryWrapper = queryWrapper.ge("cno",cno);
            if (examService.list(queryWrapper).size()!=0){
                Long id = examService.list(queryWrapper).get(0).getId();
                exam.setId(id);
                examService.saveOrUpdate(exam);
            }else {
                examService.save(exam);
            }
            model.addAttribute("msg","添加成功!");
        }else {
            model.addAttribute("msg","您输入的数据不合法！");
        }
        return "add/addSC";
    }

    //删除学生信息
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id")Long id){
        studentService.removeById(id);
        return "redirect:/dynamic_student";
    }

}
