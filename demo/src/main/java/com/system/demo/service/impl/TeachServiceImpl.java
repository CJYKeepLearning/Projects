package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Teach;
import com.system.demo.mapper.TeachMapper;
import com.system.demo.service.TeachService;
import org.springframework.stereotype.Service;

@Service
public class TeachServiceImpl extends ServiceImpl<TeachMapper, Teach> implements TeachService {

}
