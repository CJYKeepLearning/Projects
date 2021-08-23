package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.Course;
import com.system.demo.bean.TC;
import com.system.demo.bean.Teach;
import com.system.demo.bean.Teacher;
import com.system.demo.service.CourseService;
import com.system.demo.service.TCService;
import com.system.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.List;

@Controller
public class TCFormController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    TCService tcService;
    //查询教师授课情况
    @PostMapping("/getTCList")
    public String getTeachList(@RequestParam("tno")String tno, Model model){
        QueryWrapper<TC> tcQueryWrapper = new QueryWrapper<>();
        tcQueryWrapper = tcQueryWrapper.eq("tno",tno);

        List<TC> list = tcService.list(tcQueryWrapper);
        model.addAttribute("list",list);
        List<Teacher> teacherList = teacherService.list();
        for (Teacher teacher:teacherList
             ) {
            if (teacher.getId().equals(tno))
                model.addAttribute("tname",teacher.getTname());
        }
        List<Course> courseList = courseService.list();
        model.addAttribute("courseList",courseList);
        return "table/dynamic_tc";
    }
    @RequestMapping("addTC.html")
    public ModelAndView swapToPageAddTC(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add/addTC");
        return modelAndView;
    }
    @RequestMapping("addTCSelf.html")
    public ModelAndView swapToPageAddTCSelf(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add/addTCSelf");
        return modelAndView;
    }
    @PostMapping("addTC")
    public ModelAndView addTC(@RequestParam(value = "tno",required = false)String tno,
                              @RequestParam("cno")String cno,
                              @RequestParam("classroom")String classroom,
                              HttpServletRequest request,
                              ModelAndView modelAndView){
        if (StringUtils.isEmpty(tno)){
            tno = request.getSession().getAttribute("account").toString();
            modelAndView.setViewName("add/addTCSelf");
        }else {
            modelAndView.setViewName("add/addTC");
        }
        QueryWrapper<TC> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("tno",tno);
        queryWrapper = queryWrapper.eq("cno",cno);
        List<TC> list = tcService.list(queryWrapper);
        if (teacherService.getById(tno)!=null && courseService.getById(cno)!=null){
            TC tc = new TC();
            tc.setClassroom(classroom);
            tc.setCno(cno);
            tc.setTno(tno);
            if (list.size()!=0){
                tc.setId(list.get(0).getId());
            }
            tcService.saveOrUpdate(tc);
            modelAndView.addObject("msg","成功！");
        }else {
            modelAndView.addObject("msg","输入数据不合法！");
        }
        return modelAndView;
    }
}
