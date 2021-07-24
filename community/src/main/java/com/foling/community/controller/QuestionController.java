package com.foling.community.controller;

import com.foling.community.dto.QuestionDTO;
import com.foling.community.mapper.QuestionMapper;
import com.foling.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String questions(@PathVariable("id")Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
