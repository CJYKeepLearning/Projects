package com.foling.community.service;

import com.foling.community.dto.PaginationDTO;
import com.foling.community.dto.QuestionDTO;
import com.foling.community.mapper.QuestionMapper;
import com.foling.community.mapper.UserMapper;
import com.foling.community.model.Question;
import com.foling.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size){
        Integer totalCount = questionMapper.count();
        Integer totalPage= (totalCount + size -1 ) / size;

        if (page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //页面数据
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:questions
             ) {
           User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            //将question属性拷贝到questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {

        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage= (totalCount + size -1 ) / size;

        if (page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //页面数据
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:questions
        ) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            //将question属性拷贝到questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
