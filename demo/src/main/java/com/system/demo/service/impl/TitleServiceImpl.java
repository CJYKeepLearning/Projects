package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Title;
import com.system.demo.mapper.TitleMapper;
import com.system.demo.service.TitleService;
import org.springframework.stereotype.Service;

@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements TitleService {
}
