package com.foling.community.controller;

import com.foling.community.dto.PaginationDTO;
import com.foling.community.dto.QuestionDTO;
import com.foling.community.mapper.UserMapper;
import com.foling.community.model.User;
import com.foling.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size,
                          Model model,
                          HttpServletRequest request){

        User user =(User) request.getSession().getAttribute("user");
        if (user == null ){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";
    }
}
