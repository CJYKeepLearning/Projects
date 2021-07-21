package com.system.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherTitle {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long teacherId;
    private Long titleId;
}
