package com.system.demo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@TableName("xxx"):xxx中写关系表中对应的表名
public class Student {
    private Long id;
    private String sname;
    private String sex;
    private Long age;
}
