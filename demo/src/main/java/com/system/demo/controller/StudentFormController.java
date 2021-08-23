package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.*;
import com.system.demo.service.*;
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
    TeachService teachService;

    @Autowired
    ClassRoomService classService;

    @Autowired
    PersonService personService;

    @Autowired
    SpecialtyService specialtyService;

    Student student;

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
    public String addStudent(@RequestParam(value = "id", defaultValue = "-1") String id,
                             @RequestParam(value = "name", defaultValue = "-1") String name,
                             @RequestParam(value = "sex", defaultValue = "-1") String sex,
                             @RequestParam(value = "age", defaultValue = "-1") Long age,
                             HttpServletRequest request,
                             Model model){
        String spName = request.getParameter("spName");
        if (!id.equals("-1") && !name.equals("-1") && !sex.equals("-1") &&(sex.equals("男")||sex.equals("女")) && age!=-1){
            Student student = new Student();
            student.setId(id);
            student.setSname(name);
            student.setSex(sex);
            student.setAge(age);
            student.setSpName(spName);
            Person person = new Person();
            person.setAccount(id);
            person.setPower(3);
            person.setPwd("123456");
            if(studentService.getById(id)!=null){
                model.addAttribute("msg","id已经存在!添加失败");
            }else{
                studentService.save(student);
                personService.save(person);
                model.addAttribute("msg","添加成功");
            }
        }else {
            model.addAttribute("msg","输入数据不合法");
        }
        List<Specialty> specialties = specialtyService.list();
        QueryWrapper<Specialty> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("sp_name",spName);
        Specialty specialty = specialtyService.list(queryWrapper).get(0);
        specialty.setNumber(specialty.getNumber()+1);
        specialtyService.saveOrUpdate(specialty);
        model.addAttribute("specialties",specialties);
        return "add/addStudent";
    }

    //返回到增加学生信息界面：
    @RequestMapping("/addStudent.html")
    public ModelAndView swapToPageAddStu(ModelAndView modelAndView){
/*         modelAndView.addObject("id",id);*/
        List<Specialty> specialties = specialtyService.list();
        modelAndView.addObject("specialties",specialties);
         modelAndView.setViewName("add/addStudent");
         return modelAndView;
    }

    //返回到returnDynamicStudent

    @RequestMapping("returnDynamicStudent")
    public String swapToPageDyStu(){
        return  "redirect:/dynamic_student";
    }
    @RequestMapping("/{sno}/returnDynamicStudent")
    public String swapToPageDyStu2(@PathVariable("sno")Long sno){
        return  "redirect:/dynamic_student";
    }

    //返回到更新学生信息界面：
    @RequestMapping("/updateStudent.html")
    public ModelAndView swapToPageUpdStu(ModelAndView modelAndView){
        List<Specialty> specialties = specialtyService.list();
        modelAndView.addObject("specialties",specialties);
        modelAndView.setViewName("add/addStudent");
        return modelAndView;
    }
    //更新学生信息：
    @PostMapping("/updateStudent")
    public ModelAndView updateStudent(@RequestParam(value = "id",defaultValue = "-1")String id,
                                @RequestParam(value = "name",defaultValue = "-1")String name,
                                @RequestParam(value = "sex",defaultValue = "-1")String sex,
                                @RequestParam(value = "age",defaultValue = "-1")Long age,
                                      HttpServletRequest request,ModelAndView model){
        Student student = new Student();
        String spName = request.getParameter("spName");
        if(!name.equals("-1") && !sex.equals("-1") &&(sex.equals("男")||sex.equals("女")) && age!=-1){
            student.setSname(name);
            student.setSex(sex);
            student.setAge(age);
            student.setId(id);
            student.setSpName(spName);
            boolean isUpdate = studentService.updateById(student);
            if (isUpdate){
                model.addObject("msg","更新成功");
            }else {
                model.addObject("msg","id不存在！更新失败");
            }
        }else{
            model.addObject("msg","数据不合法!请重新提交!");
        }
        model.setViewName("update/updateStudent");
        model.addObject("id",0);
        List<Specialty> specialties = specialtyService.list();
        model.addObject("specialties",specialties);
        return model;
    }


    //删除学生信息
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id")Long id){
        //删除teach表中的信息
        QueryWrapper<Teach> teachQueryWrapper  = new QueryWrapper<>();
        teachQueryWrapper = teachQueryWrapper.ge("sno",id);
        List<Teach> teachList = teachService.list(teachQueryWrapper);
        for (int i=0;i<teachList.size();i++){
            id = teachList.get(i).getId();
            teachService.removeById(id);
        }
        Student student = studentService.getById(id);
        studentService.removeById(id);
        List<Specialty> specialties = specialtyService.list();
        QueryWrapper<Specialty> queryWrapper = new QueryWrapper<>();
        String spName = student.getSpName();
        queryWrapper.ge("sp_name",spName);
        Specialty specialty = specialtyService.list(queryWrapper).get(0);
        specialty.setNumber(specialty.getNumber()-1);
        specialtyService.saveOrUpdate(specialty);
        return "redirect:/dynamic_student";
    }

    @RequestMapping("/updateSelf.html{id}")
    public ModelAndView updateSelf(ModelAndView modelAndView,@PathVariable("id")String id){
        modelAndView.setViewName("update/updateSelf");
        modelAndView.addObject("id",id);
        List<Specialty> specialties = specialtyService.list();
        modelAndView.addObject("specialties",specialties);
        return modelAndView;
    }
    @PostMapping("/updateSelf")
    public ModelAndView updateSelf(@RequestParam(value = "name")String name,
                                   @RequestParam(value = "sex")String sex,
                                   @RequestParam(value = "age")Long age,
                                   HttpServletRequest request,
                                   ModelAndView model){
        Student student = new Student();
        String id = request.getSession().getAttribute("account").toString();
        student.setId(id);
        student.setSname(name);
        student.setSex(sex);
        student.setAge(age);
        student.setSpName(request.getParameter("spName"));
        studentService.saveOrUpdate(student);
        model.setViewName("update/updateSelf");
        model.addObject("id",id);
        model.addObject("msg","更新成功");
        return model;
    }
    @RequestMapping(value = {"/returnStudentHome{account}"})
    public ModelAndView returnStudentHome(ModelAndView modelAndView,@PathVariable("account")String id){
        modelAndView.addObject("id",id);
        modelAndView.setViewName("studentHome");
        return modelAndView;
    }
}
