package com.sky.admin.controller;

import com.sky.common.core.data.dto.UserDto;
import com.sky.common.core.data.po.SysUser;
import com.sky.common.core.result.error.AuthError;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.util.MapBuildUtil;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

/**
 * Created by skyyemperor on 2021-02-07 19:49
 * Description :
 */
@RequestMapping("/user")
@RestController
@Validated
public class UserController {

    @Autowired
    private SysUserService userService;

    /**
     * 更新用户信息
     *
     * @param userId   用户ID
     * @param nickName 昵称
     * @param avatar   头像链接
     * @return
     */
    @PostMapping("/info/update")
    public Result updateUserInfo(@RequestParam("_uid_") Long userId,
                                 @Size(max = 50) @RequestParam(required = false) String nickName,
                                 @RequestParam(required = false) String avatar) {
        if (nickName == null && avatar == null)
            return Result.getResult(CommonError.PARAM_WRONG);
        userService.updateUser(new SysUser(userId, nickName, avatar));
        return Result.success();
    }


    /**
     * 获取用户信息(admin)
     */
    @GetMapping("/info")
    public Result getOtherUserInfo(@RequestParam("_uid_") Long uId,
                                   @RequestParam(required = false) Long userId) {
        if (userId != null) userId = uId; //若otherUserId为空，则获取本人的信息
        UserDto user = userService.getLessUserInfo(userId);
        if (user == null) return Result.getResult(AuthError.USER_NOT_EXIST);
        return Result.success(user);
    }

    @PreAuthorize("@pms.hasPerm('admin:user:*')")
    @GetMapping("/list")
    public Result getUserList() {
        return userService.getUserList();
    }

    @PreAuthorize("@pms.hasPerm('admin:user:*')")
    @PostMapping("/role")
    public Result addUser(@RequestParam Long userId, @RequestParam Long roleId) {
        return userService.updateUserRole(userId, roleId);
    }

    @PreAuthorize("@pms.hasPerm('admin:user:*')")
    @GetMapping("/info/name")
    public Result getUserInfoByName(@RequestParam String username) {
        SysUser u = userService.getUserByUserName(username);
        return Result.success(MapBuildUtil.builder()
                .data("username", u.getUserName())
                .data("role", u.getRoles().get(0).getRoleName())
                .get());
    }

}
