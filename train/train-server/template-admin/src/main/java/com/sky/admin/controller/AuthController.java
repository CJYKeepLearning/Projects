package com.sky.admin.controller;

import com.sky.common.core.data.po.SysUser;
import com.sky.common.core.result.error.AuthError;
import com.sky.common.core.result.Result;
import com.sky.common.util.MapBuildUtil;
import com.sky.framework.service.AuthService;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2021-01-29 0:09
 * Description :
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SysUserService userService;

    /**
     * 登录方法
     */
    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password) {
        String[] tokenAndRefreshToken = authService.login(username, password);
        if (tokenAndRefreshToken == null || tokenAndRefreshToken.length < 2) {
            return Result.getResult(AuthError.PASSWORD_WRONG);
        }

        return Result.success(MapBuildUtil.builder()
                .data("token", tokenAndRefreshToken[0])
                .data("refresh_token", tokenAndRefreshToken[1])
                .get()
        );
    }

    @PostMapping("/register")
    public Result register(@RequestParam String username,
                           @RequestParam String password) {
        return userService.register(new SysUser(username, password));
    }

    @PostMapping("/refresh")
    public Result refresh(@RequestParam String refreshToken) {
        String[] tokenAndRefreshToken = authService.refresh(refreshToken);
        if (tokenAndRefreshToken == null || tokenAndRefreshToken.length < 2) {
            return Result.getResult(AuthError.REFRESH_TOKEN_IS_NOT_VALID);
        }

        return Result.success(MapBuildUtil.builder()
                .data("token", tokenAndRefreshToken[0])
                .data("refresh_token", tokenAndRefreshToken[1])
                .get()
        );
    }


}
