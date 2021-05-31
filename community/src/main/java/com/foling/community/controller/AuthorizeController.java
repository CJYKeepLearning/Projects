package com.foling.community.controller;

import com.foling.community.dto.AccessTokenDTO;
import com.foling.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("ed649d9ab02352975f0c");
        accessTokenDTO.setClient_secret("fc83df741a4ae4fbe748bc2b9964e08cf9702166");
        githubProvider.getAccessTokenDTO(new AccessTokenDTO());
        return "index";
    }
}
