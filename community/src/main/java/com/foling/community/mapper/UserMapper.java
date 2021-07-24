package com.foling.community.mapper;

import com.foling.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
     void insert(User user);

    @Select("select * from user where token=#{tokenValue}")
    User findByToken(@Param("tokenValue") String tokenValue);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified=#{gmtModified}, avatar_url = #{avatarUrl} where account_id = #{accountId}")
    void update(User user);
}
