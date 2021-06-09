package com.system.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.demo.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
