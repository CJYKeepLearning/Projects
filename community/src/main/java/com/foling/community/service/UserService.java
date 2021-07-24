package com.foling.community.service;

import com.foling.community.mapper.UserMapper;
import com.foling.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null){
            //插入
            user.setGmtCreate(String.valueOf(System.currentTimeMillis()));
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(String.valueOf(System.currentTimeMillis()));
            userMapper.update(dbUser);
        }
    }
}
