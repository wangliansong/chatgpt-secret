package com.secret.chatgpt.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.chatgpt.base.domain.entity.UserMemberRelationDO;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface UserMemberRelationMapper extends BaseMapper<UserMemberRelationDO> {

     //@Select("select * from omggpt_user where user_name = #{userName}")
     //UserInfo queryUserInfoByUserName(String userName);

    //@Update("update gpt_user_member_relation set use_chat_num=#{useChatNum},CREATED_TIME=#{createdTime},UPDATED_TIME=#{updatedTime} where user_id=#{userId}")
    //void updateChatNum(UserMemberRelationDO userMemberRelationDO);

//    default UserMemberRelationDO selectByUserId(Long userId){
//        QueryWrapper<UserMemberRelationDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id",userId);
//        return selectOne(queryWrapper);
//    }
}
