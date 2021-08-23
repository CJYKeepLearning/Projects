package com.system.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TC {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tno;
    private String cno;
    private String classroom;
}
