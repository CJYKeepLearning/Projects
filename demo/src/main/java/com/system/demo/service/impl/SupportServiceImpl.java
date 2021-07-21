package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Support;
import com.system.demo.mapper.SupportMapper;
import com.system.demo.service.SupportService;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl extends ServiceImpl<SupportMapper, Support> implements SupportService {
}
