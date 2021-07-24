package com.foling.community.mapper;

import com.foling.community.dto.QuestionDTO;
import com.foling.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator=#{userId} limit #{offset},#{size} ")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(*) from question where creator=#{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id=#{userId} ")
    Question getById(@Param(value = "userId")Integer userId);

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    void update(Question question);
}
