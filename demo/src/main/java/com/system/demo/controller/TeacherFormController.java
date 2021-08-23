package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.system.demo.bean.*;
import com.system.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("returnTeacherHome")
    public ModelAndView teacherHome(ModelAndView modelAndView){
        modelAndView.setViewName("teacherHome");
        return modelAndView;
    }
    //跳转到增加或更新教师信息页面
    @GetMapping("addOrUpdateTeacher.html")
    public ModelAndView swapToPageAddOrUpdateTeacher(ModelAndView modelAndView){
        modelAndView.setViewName("add/addOrUpdateTeacher");
        return modelAndView;
    }
    @GetMapping("updateTeacherSelf.html")
    public ModelAndView swapToPageUpdateTeacherSelf(ModelAndView modelAndView,HttpServletRequest request){
        String id = request.getSession().getAttribute("account").toString();
        Teacher teacher = teacherService.getById(id);
        modelAndView.addObject("teacher",teacher);
        modelAndView.setViewName("update/updateTeacherSelf");
        return modelAndView;
    }

    //Post增加教师
    @PostMapping("addOrUpdateTeacher")
    public ModelAndView addOrUpdateTeacher(@RequestParam(value = "id",required = false)String id,
                                           @RequestParam("name")String name,
                                           @RequestParam(value = "title",defaultValue ="null")String title,
                                           @RequestParam(value = "email",defaultValue ="null")String email,
                                           @RequestParam(value = "city",defaultValue = "null")String city,
                                           @RequestParam(value = "sex",required = false)String sex,
                                           @RequestParam(value = "area",defaultValue = "null")String area,
                                           @RequestParam(value = "code",defaultValue = "null")String code,
                                           @RequestParam(value = "phone",defaultValue = "null")String phone,
                                           HttpServletRequest request,
                                           ModelAndView modelAndView){
        Teacher teacher = new Teacher();
        if (StringUtils.isEmpty(id)){
            String account = request.getSession().getAttribute("account").toString();
            teacher.setId(account);
            teacher.setSex(sex);
            modelAndView.setViewName("update/updateTeacherSelf");
        }else {
            teacher.setId(id);
            teacher.setSex(teacherService.getById(id).getSex());
            modelAndView.setViewName("add/addOrUpdateTeacher");
        }
        teacher.setTname(name);
        teacher.setEmail(email);
        teacher.setCity(city);
        teacher.setArea(area);
        teacher.setCode(code);
        teacher.setPhone(phone);
        teacher.setTitle(title);
        teacherService.saveOrUpdate(teacher);
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
    @GetMapping("addOrUpdateTeacherSalary.html")
    public ModelAndView addOrUpdateTeacherSalaryHtml(ModelAndView modelAndView){
        modelAndView.setViewName("add/addOrUpdateTeacherSalary");
        return modelAndView;
    }
    @PostMapping("addOrUpdateTeacherSalary")
    public ModelAndView addOrUpdateTeacherSalary(@RequestParam(value = "id",required = false)String id,
                                                 @RequestParam(value = "title",defaultValue ="null")String title,
                                                 HttpServletRequest request,
                                                 ModelAndView modelAndView){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTitle(title);
        modelAndView.setViewName("add/addOrUpdateTeacherSalary");
        modelAndView.addObject("msg","成功！");
        teacherService.saveOrUpdate(teacher);
        return modelAndView;
    }


}
