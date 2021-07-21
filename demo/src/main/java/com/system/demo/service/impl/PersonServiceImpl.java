package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.Person;
import com.system.demo.mapper.PersonMapper;
import com.system.demo.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
}
