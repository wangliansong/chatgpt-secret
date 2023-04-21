package com.secret.chatgpt.base.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("gpt_member_package")
public class MemberPackageDO {

    @TableId(type = IdType.ASSIGN_UUID)
    private Long MpId;
    private String MpCode;
    private String MpName;
    private Double MpPrice;
    private Long MpMaxChat;
    private LocalDateTime MpExpireDate;

}
