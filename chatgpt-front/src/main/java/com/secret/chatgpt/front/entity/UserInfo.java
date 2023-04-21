package com.secret.chatgpt.front.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("omggpt_user")
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "user_code")
    private String userCode;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "password")
    private String password;
    @TableField(value = "create_time")
    private Date  createTime;
    @TableField(value = "update_time")
    private Date  updateTime;
    @TableField(value = "last_Login_time")
    private Date lastLoginTime;
}
