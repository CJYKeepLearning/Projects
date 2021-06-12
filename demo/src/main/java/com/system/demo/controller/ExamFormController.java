package com.system.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.demo.bean.Exam;
import com.system.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Wrapper;
import java.util.List;

@Controller
public class ExamFormController {
    @Autowired
    ExamService examService;

    //删除某人的考试记录
    @GetMapping("/deleteExam")
    public String deleteExam(@RequestParam(value = "id")Long id,Model model){
        examService.removeById(id);
        return "table/dynamic_exam";
    }
    //根据sno得到个人的选课信息
    @PostMapping("/getExamList")
    public String getExamList(@RequestParam(value = "sno")Long sno, Model model){
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        //通过queryWrapper设置条件
        //查询id>=30记录
        //第一个参数字段名称，第二个参数设置值
        queryWrapper.eq("sno",sno);
        List<Exam> exams = examService.list(queryWrapper);
        model.addAttribute("exams",exams);
        //ge表示大于等于、gt表示大于、le表示小于等于、lt表示小于
        //eq等于、ne不等于
        //between
/*        //查询年龄20-30范围 1.代表字段  2.代表开始值  3.代表结束值
        queryWrapper.between("age",20,30);
        //like模糊查询
        queryWrapper.like("username","吸");
        //orderByDesc 降序  ASC升序
        queryWrapper.orderByDesc("id");
        //last sql语句最后拼接
        queryWrapper.last("limit 1");
        //查询指定的列
        queryWrapper.select("id","name");*/
        return "table/dynamic_exam";
    }
}
