package com.foling.community.mapper;

import com.foling.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author foling
 * @Date2021-07-25 16:56
 * @Version 1.0
 * @Other Be happy~
 **/
@Component
@Mapper
public interface QuestionExtMapper {
    void incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
}
