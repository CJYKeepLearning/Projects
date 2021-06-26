package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.TC;
import com.system.demo.mapper.TCMapper;
import com.system.demo.service.TCService;
import org.springframework.stereotype.Service;

@Service
public class TCServiceImpl extends ServiceImpl<TCMapper, TC> implements TCService {
}
