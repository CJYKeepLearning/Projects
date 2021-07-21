package com.system.demo.bean;
/*
import com.baomidou.mybatisplus.annotation.TableField;*/
/*import com.github.jeffreyning.mybatisplus.anno.MppMultiId;*/
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teach {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sno;
    private String cno;
    private Long grade;
    private String tno;
}
