package com.secret.chatgpt.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.chatgpt.front.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

     @Select("select * from omggpt_user where user_name = #{userName}")
     UserInfo queryUserInfoByUserName(String userName);

}
