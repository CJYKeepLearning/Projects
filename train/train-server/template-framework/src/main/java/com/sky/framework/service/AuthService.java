package com.sky.framework.service;

import com.sky.common.core.data.dto.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 */
@Component
@Slf4j
public class AuthService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证
     *
     * @return 结果
     */
    public String[] login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.login(loginUser);
    }


    /**
     * refresh Token
     *
     * @param refreshToken
     * @return
     */
    public String[] refresh(String refreshToken) {
        return tokenService.refresh(refreshToken);
    }

}
