package com.foling.community.controller;

import com.foling.community.dto.QuestionDTO;
import com.foling.community.mapper.QuestionMapper;
import com.foling.community.mapper.UserMapper;
import com.foling.community.model.Question;
import com.foling.community.model.User;
import com.foling.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String tokenValue = cookie.getValue();
                    User user = userMapper.findByToken(tokenValue);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
