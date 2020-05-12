package com.example.test_token.mapper;

import com.example.test_token.auth.JwtUser;
import com.example.test_token.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-12
 */
@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into auth_user(username,password,roles,phone,image_url,last_login_time,register_time) values(#{username},#{password},#{roles},#{phone},#{imageUrl},#{lastLoginTime},#{registerTime})")
    void addUserInfo(UserInfo userInfo);

    @Select("select username,password,roles rolesJsonString  from auth_user where username = #{username}")
    JwtUser queryUserInfoByUsername(String username);
}
