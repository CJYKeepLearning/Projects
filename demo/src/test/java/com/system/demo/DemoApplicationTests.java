package com.system.demo;

import com.system.demo.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    StudentMapper studentMapper;
    @Test
    void contextLoads() {
        log.info(String.valueOf(studentMapper.selectById(1)));
    }

}
