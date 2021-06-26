package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.TeacherTitle;
import com.system.demo.mapper.TeacherTitleMapper;
import com.system.demo.service.TeacherTitleService;
import org.springframework.stereotype.Service;

@Service
public class TeacherTitleImpl extends ServiceImpl<TeacherTitleMapper, TeacherTitle> implements TeacherTitleService {
}
