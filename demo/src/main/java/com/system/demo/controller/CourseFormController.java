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
import java.util.List;

@Slf4j
@Controller
public class CourseFormController {
    @Autowired
    CourseService courseService;

    @Autowired
    TeachService teachService;

    @Autowired
    CourseSpService courseSpService;

    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    StudentService studentService;

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
    public String addCourse(@RequestParam(value = "id",defaultValue = "-1")String id,
                            @RequestParam(value = "cname",defaultValue = "-1")String cname,
                            @RequestParam(value = "credit",defaultValue = "-1")Long credit,
                            @RequestParam(value = "weekhours",defaultValue = "-1")Long weekhours,
                            @RequestParam(value = "cType",defaultValue = "-1")String cType,
                             Model model){
            Course course = new Course();
            course.setId(id);
            course.setCname(cname);
            course.setCredit(credit);
            course.setWeekhours(weekhours);
            course.setCType(cType);
            if(courseService.getById(id)!=null){
                model.addAttribute("msg","id已经存在!添加失败");
            }else{
                courseService.save(course);
                model.addAttribute("msg","添加成功");
            }
/*            if (cType.equals("必修课")){
                QueryWrapper<CourseSp> queryWrapper = new QueryWrapper();
                queryWrapper = queryWrapper.ge("cno",id);
                List<CourseSp> courseSpList = courseSpService.list(queryWrapper);
                for (CourseSp list:courseSpList) {
                    Long sp = list.getSp();
                    QueryWrapper<ClassRoom> classRoomQueryWrapper = new QueryWrapper<>();
                    //得到对应的班级
                    classRoomQueryWrapper = classRoomQueryWrapper.ge("sp_no",sp);
                    List<ClassRoom> classRooms = classRoomService.list(classRoomQueryWrapper);
                    for (ClassRoom classRoom:classRooms){
                        Long classRoomId = classRoom.getId();
                        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                        List<Student> students = studentService.list(studentQueryWrapper);
                        for (Student student:students
                        ) {
                            Teach teach = new Teach();
                            teach.setSno(student.getId());
                            teach.setCno(id);
                            teachService.save(teach);
                        }
                    }
                }
            }*/
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
    public String updateCourse(@RequestParam(value = "id",defaultValue = "-1")String id,
                                @RequestParam(value = "cname")String cname,
                                @RequestParam(value = "credit")Long credit,
                                @RequestParam(value = "weekhours")Long weekhours,
                                Model model){
            Course course = new Course();
            course.setId(id);
            course.setCname(cname);
            course.setCredit(credit);
            course.setWeekhours(weekhours);
            boolean isUpdate = courseService.updateById(course);
            if (isUpdate){
                model.addAttribute("msg","更新成功");
            }else {
                model.addAttribute("msg","id不存在！更新失败");
            }
        return "update/updateCourse";
    }

    //删除课程信息
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id,Model model){
        QueryWrapper<Teach> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.ge("cno",id);
        List<Teach> list = teachService.list(queryWrapper);
        if(list.size()!=0){
            model.addAttribute("msg","该课已有学生选修，不能删除！");
        }else{
            courseService.removeById(id);
        }
        return "redirect:/dynamic_course";
    }

}
