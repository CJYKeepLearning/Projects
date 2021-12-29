package com.sky.framework.service;

import com.sky.common.core.data.po.SysUser;
import com.sky.common.core.data.dto.LoginUser;
import com.sky.common.util.StringUtil;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户验证处理
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, userService.getPermsByUserId(user.getUserId()));
    }
}
