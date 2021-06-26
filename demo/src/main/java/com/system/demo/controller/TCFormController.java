package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public String getTeachList(@RequestParam("tno")Long tno, Model model){
        QueryWrapper<TC> tcQueryWrapper = new QueryWrapper<>();
        tcQueryWrapper = tcQueryWrapper.ge("tno",tno);
        List<TC> list = tcService.list(tcQueryWrapper);
        model.addAttribute("list",list);
        return "table/dynamic_tc";
    }
    @RequestMapping("addTC.html")
    public ModelAndView swapToPageAddTC(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add/addTC");
        return modelAndView;
    }
    @PostMapping("addTC")
    public ModelAndView addTC(@RequestParam("tno")Long tno,@RequestParam("cno")Long cno,
                              ModelAndView modelAndView){
        QueryWrapper<TC> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.ge("tno",tno);
        queryWrapper = queryWrapper.ge("cno",cno);
        List<TC> list = tcService.list(queryWrapper);
        if (teacherService.getById(tno)!=null && courseService.getById(cno)!=null &&  list.size()==0){
            TC tc = new TC(null,tno,cno);
            tcService.save(tc);
            modelAndView.addObject("msg","添加成功！");
        }else {
            modelAndView.addObject("msg","输入数据不合法！");
        }
        modelAndView.setViewName("add/addTC");
        return modelAndView;
    }
}
