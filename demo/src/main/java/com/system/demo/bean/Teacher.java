package com.system.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {
    @TableId
    private String id;
    private String tname;
    private Long title;
    private Long email;
    private String sex;
    private String phone;
    private String city;
    private String area;
    private String code;
}
