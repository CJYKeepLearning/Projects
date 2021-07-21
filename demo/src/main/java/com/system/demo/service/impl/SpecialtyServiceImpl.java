package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Specialty;
import com.system.demo.mapper.SpecialtyMapper;
import com.system.demo.service.SpecialtyService;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl extends ServiceImpl<SpecialtyMapper, Specialty> implements SpecialtyService {
}
