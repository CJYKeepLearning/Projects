package com.system.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.demo.bean.ClassRoom;
import com.system.demo.mapper.ClassRoomMapper;
import com.system.demo.service.ClassRoomService;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomServiceImpl extends ServiceImpl<ClassRoomMapper, ClassRoom> implements ClassRoomService {
}
