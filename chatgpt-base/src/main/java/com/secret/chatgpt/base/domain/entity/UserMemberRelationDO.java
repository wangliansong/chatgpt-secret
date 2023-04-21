package com.secret.chatgpt.base.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("gpt_user_member_relation")
public class UserMemberRelationDO {

    @TableId(type = IdType.ASSIGN_UUID)
    private Long mrId;
    private Long userId;
    private Long mpId;
    private int status;
    private Long useChatNum;
    private Long maxChat;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Date createdTime;
    private Date updatedTime;


}
