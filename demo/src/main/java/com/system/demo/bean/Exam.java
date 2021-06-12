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
public class Exam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sno;
/*    @MppMultiId
    @TableField(value = "cno")*/
    private Long cno;
/*    @MppMultiId
    @TableField(value = "sno")*/

    private Long grade;
}
