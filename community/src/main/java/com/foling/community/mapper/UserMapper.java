package com.foling.community.mapper;

import com.foling.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
