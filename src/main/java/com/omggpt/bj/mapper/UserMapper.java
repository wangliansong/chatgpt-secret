package com.omggpt.bj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.omggpt.bj.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

     @Select("select * from omggpt_user where user_name = #{userName}")
     UserInfo queryUserInfoByUserName(String userName);

}
