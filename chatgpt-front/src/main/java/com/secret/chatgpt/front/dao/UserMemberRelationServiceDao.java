package com.secret.chatgpt.front.dao;

import com.secret.chatgpt.base.domain.entity.UserMemberRelationDO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface UserMemberRelationServiceDao {
    @Update("update gpt_user_member_relation set use_chat_num=#{useChatNum},CREATED_TIME=#{createdTime},UPDATED_TIME=#{updatedTime} where user_id=#{userId}")
    void updateChatNum(UserMemberRelationDO userMemberRelationDO);

}
