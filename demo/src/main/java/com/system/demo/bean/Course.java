package com.system.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    private Long id;
    private String cname;
    private Long credit;
    private Long weekhours;
}
