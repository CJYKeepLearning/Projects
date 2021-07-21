package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.CourseSp;
import com.system.demo.mapper.CourseSpMapper;
import com.system.demo.service.CourseSpService;
import org.springframework.stereotype.Service;

@Service
public class CourseSpServiceImpl extends ServiceImpl<CourseSpMapper, CourseSp> implements CourseSpService {
}
