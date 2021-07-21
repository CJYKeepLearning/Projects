package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.TC;
import com.system.demo.bean.Teach;
import com.system.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Wrapper;
import java.util.List;

@Controller
public class TeachFormController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    TeachService teachService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TCService tcService;

    //根据sno得到个人的选课信息
    @PostMapping("/getTeachList")
    public ModelAndView getTeachList(@RequestParam(value = "sno")Long sno, ModelAndView model){
        QueryWrapper<Teach> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno",sno);
        List<Teach> teachs = teachService.list(queryWrapper);
        model.addObject("teachs",teachs);
        model.setViewName("table/dynamic_teach");
        return model;
    }
    @GetMapping("/addTeach.html")
    public String swapToPageAddTeach(){
        return "add/addTeach";
    }
    @PostMapping("/addTeach")
    public String addTeach(@RequestParam(value = "sno",defaultValue = "-1")String sno,
                           @RequestParam(value = "cno",defaultValue = "-1")String cno,
                           @RequestParam(value = "grade",defaultValue = "-1")Long grade,
                           @RequestParam(value = "tno",defaultValue = "-1")String tno, Model model) {
        if (sno.equals("-1") || cno.equals("-1")  || grade == -1 ||studentService.getById(sno) == null && courseService.getById(cno) == null) {
            model.addAttribute("msg", "您输入的数据不合法！");
            return "add/addTeach";
        }
        //tno为空
        if (!tno.equals("-1")) {
            Teach teach = new Teach(null, sno, cno, grade, null);
            QueryWrapper<Teach> queryWrapper = new QueryWrapper<>();
            queryWrapper = queryWrapper.ge("sno", sno);
            //sno相同
            if(teachService.list(queryWrapper).size()!=0){
                queryWrapper = queryWrapper.ge("cno", cno);
                //cno相同
                if (teachService.list(queryWrapper).size() != 0){
                    Long id = teachService.list(queryWrapper).get(0).getId();
                    teach.setId(id);
                    teachService.saveOrUpdate(teach);
                }else{
                    teachService.save(teach);
                }
            }else {
                teachService.save(teach);
            }
            model.addAttribute("msg", "添加成功!");
        } else {
            QueryWrapper<TC> queryWrapper = new QueryWrapper<>();
            queryWrapper = queryWrapper.ge("tno", tno);
            queryWrapper = queryWrapper.ge("cno", cno);
            List<TC> list = tcService.list(queryWrapper);
            //讲授课程
            if (list.size() != 0) {
                Teach teach = new Teach();
                teach.setSno(sno);
                teach.setCno(cno);
                teach.setGrade(grade);
                teach.setTno(tno);
                QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<>();
                teachQueryWrapper = teachQueryWrapper.ge("sno", sno);
                //sno相同
                if(teachService.list(teachQueryWrapper).size()!=0){
                    teachQueryWrapper = teachQueryWrapper.ge("cno", cno);
                    //cno相同
                    if (teachService.list(teachQueryWrapper).size() != 0){
                        Long id = teachService.list(teachQueryWrapper).get(0).getId();
                        teach.setId(id);
                        teachService.saveOrUpdate(teach);
                    }else{
                        teachService.save(teach);
                    }
                }else {
                    teachService.save(teach);
                }
                model.addAttribute("msg", "添加成功!");
            } else {
                model.addAttribute("msg", "您输入的数据不合法！");
            }
        }
        return "add/addTeach";
    }
    @GetMapping("/deleteTeach")
    public ModelAndView deleteTeach(@RequestParam("cno")Long cno,
                                    @RequestParam("sno")Long sno,ModelAndView modelAndView){
        QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<>();
        teachQueryWrapper = teachQueryWrapper.ge("sno",sno);
        teachQueryWrapper = teachQueryWrapper.ge("cno",cno);
        Long id = teachService.list(teachQueryWrapper).get(0).getId();
        teachService.removeById(id);
        teachQueryWrapper = new QueryWrapper<>();
        teachQueryWrapper = teachQueryWrapper.ge("sno",sno);
        List list = teachService.list(teachQueryWrapper);
        modelAndView.addObject("teachs",list);
        modelAndView.setViewName("table/dynamic_teach");
        return modelAndView;
    }
    @RequestMapping("/addTeachSelf.html{id}")
    public ModelAndView addTeachSelfHTML(ModelAndView modelAndView,@PathVariable("id")String id){
        modelAndView.setViewName("add/addTeachSelf");
        modelAndView.addObject("id",id);
        return modelAndView;
    }
    @PostMapping("addTeachSelf/{id}")
    public ModelAndView addTeachSelf(@PathVariable(value = "id",required = false)String id,
                                    @RequestParam(value = "cno")String cno,
                                    @RequestParam(value = "grade")Long grade,
                                    @RequestParam(value = "tno")String tno, ModelAndView model){
        Teach teach = new Teach();
        teach.setCno(cno);
        teach.setSno(id);
        teach.setGrade(grade);
        teach.setTno(tno);
        teachService.save(teach);
        model.setViewName("add/addTeachSelf");
        model.addObject("id",id);
        return model;
    }
}
