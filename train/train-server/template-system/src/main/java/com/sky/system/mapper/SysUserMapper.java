package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.common.core.data.dto.UserDto;
import com.sky.common.core.data.po.SysRole;
import com.sky.common.core.data.po.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by skyyemperor on 2021-01-28 22:05
 * Description :
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectUserByUserName(String userName);

    SysUser selectUserByUserId(Long userId);

    List<SysUser> selectAllUser();

    List<SysRole> selectRolesByUserId(Long userId);

    List<String> selectPermsByUserId(Long userId);

    UserDto selectLessUserInfoByUserId(Long userId);

    boolean insertUserRoleLink(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);


}