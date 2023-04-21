package com.secret.chatgpt.front.dao;

import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.entity.UserInfo2;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserInfoDao {
    @Select("select username,password from t_user where username=#{username}")
    UserInfo2 findByUsername2(@Param("username") String username);

    @Select("select username,password from omggpt_user where username=#{username}")
    UserInfo findByUsername(@Param("username") String username);
}
