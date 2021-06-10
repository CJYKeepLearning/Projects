package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Exam;
import com.system.demo.mapper.ExamMapper;
import com.system.demo.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
}
