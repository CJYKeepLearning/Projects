package com.sky.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.common.core.data.dto.UserDto;
import com.sky.common.core.data.po.SysRole;
import com.sky.common.core.data.po.SysUser;
import com.sky.common.core.result.Result;
import com.sky.common.core.result.error.AuthError;
import com.sky.system.mapper.SysUserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sky.system.service.SysUserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by skyyemperor on 2021-01-28 22:00
 * Description :
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SysUser getUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    public SysUser getUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    public Set<String> getRolesByUserId(Long userId) {
        List<SysRole> roles = userMapper.selectRolesByUserId(userId);
        Set<String> roleSet = new HashSet<>();
        for (SysRole role : roles) {
            if (role != null) {
                roleSet.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
            }
        }
        return roleSet;
    }

    public Set<String> getPermsByUserId(Long userId) {
        return new HashSet<>(userMapper.selectPermsByUserId(userId));
    }

    public UserDto getLessUserInfo(Long userId) {
        return userMapper.selectLessUserInfoByUserId(userId);
    }

    public boolean updateUser(SysUser user) {
        return userMapper.updateById(user) > 0;
    }

    public Result register(SysUser user) {
        SysUser lastUser = userMapper.selectUserByUserName(user.getUserName());
        if (lastUser != null) return Result.getResult(AuthError.USERNAME_HAS_EXIST);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        user = userMapper.selectUserByUserName(user.getUserName());
        userMapper.insertUserRoleLink(user.getUserId(), 3L); //roleId为3代表普通用户

        return Result.success();
    }

    public Result getUserList(){
        return Result.success(userMapper.selectAllUser());
    }

    public Result updateUserRole(Long userId, Long roleId) {
        userMapper.updateUserRole(userId, roleId);
        return Result.success();
    }
}

