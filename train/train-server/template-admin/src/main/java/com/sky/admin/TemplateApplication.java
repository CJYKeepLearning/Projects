package com.sky.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sky.**")
@MapperScan("com.sky.*.mapper")
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
