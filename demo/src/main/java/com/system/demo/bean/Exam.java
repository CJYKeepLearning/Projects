package com.system.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exam {
    private Long cno;
    private Long sno;
    private Long grade;
}
