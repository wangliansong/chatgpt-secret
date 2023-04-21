-- 创建 database
CREATE DATABASE IF NOT EXISTS chat DEFAULT CHARACTER SET utf8;

USE chat;

-- 聊天室表
CREATE TABLE IF NOT EXISTS chat_room (
    id BIGINT PRIMARY KEY COMMENT '主键',
    user_id BIGINT COMMENT '用户id',
    ip VARCHAR(255) NULL COMMENT 'ip',
    conversation_id VARCHAR(64) UNIQUE NULL COMMENT '对话 id，唯一',
    first_chat_message_id BIGINT UNIQUE NOT NULL  COMMENT '第一条消息主键',
    first_message_id VARCHAR(64) UNIQUE NOT NULL COMMENT '第一条消息',
    title VARCHAR(255) NOT NULL COMMENT '对话标题，从第一条消息截取',
    api_type VARCHAR(20) NOT NULL COMMENT 'API 类型',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT  '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天室表';

-- 聊天消息表
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT PRIMARY KEY COMMENT '主键',
    user_id BIGINT COMMENT '用户id',
    message_id VARCHAR(64) UNIQUE NOT NULL COMMENT '消息 id',
    parent_message_id VARCHAR(64) COMMENT '父级消息 id',
    parent_answer_message_id VARCHAR(64) COMMENT '父级回答消息 id',
    parent_question_message_id VARCHAR(64) COMMENT '父级问题消息 id',
    context_count BIGINT NOT NULL COMMENT '上下文数量',
    question_context_count BIGINT NOT NULL COMMENT '问题上下文数量',
    message_type INTEGER NOT NULL COMMENT '消息类型枚举',
    chat_room_id BIGINT NOT NULL COMMENT '聊天室 id',
    conversation_id VARCHAR(255) NULL COMMENT '对话 id',
    api_type VARCHAR(20) NOT NULL COMMENT 'API 类型',
    api_key VARCHAR(255) NULL COMMENT 'ApiKey',
    content VARCHAR(5000) NOT NULL COMMENT '消息内容',
    original_data TEXT COMMENT '消息的原始请求或响应数据',
    response_error_data TEXT COMMENT '错误的响应数据',
    prompt_tokens BIGINT COMMENT '输入消息的 tokens',
    completion_tokens BIGINT COMMENT '输出消息的 tokens',
    total_tokens BIGINT COMMENT '累计 Tokens',
    ip VARCHAR(255) NULL COMMENT 'ip',
    status INTEGER NOT NULL COMMENT '聊天记录状态',
    is_hide TINYINT NOT NULL DEFAULT 0 COMMENT '是否隐藏 0 否 1 是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';

-- 敏感词表
CREATE TABLE IF NOT EXISTS sensitive_word (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    word VARCHAR(255) NOT NULL COMMENT '敏感词内容',
    status INTEGER NOT NULL COMMENT '状态 1 启用 2 停用',
    is_deleted INTEGER DEFAULT 0 COMMENT '是否删除 0 否 NULL 是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感词表';

CREATE TABLE `omggpt_user` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `user_code` varchar(255) DEFAULT NULL,
   `user_name` varchar(200) DEFAULT NULL,
   `password` varchar(255) DEFAULT NULL,
   `create_time` datetime DEFAULT NULL,
   `last_login_time` datetime DEFAULT NULL,
   `update_time` datetime DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `username-idx` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb3_general_ci

DROP TABLE IF EXISTS gpt_member_package;
CREATE TABLE gpt_member_package(
   mp_id INT NOT NULL AUTO_INCREMENT  COMMENT '套餐id' ,
   mp_code VARCHAR(32)    COMMENT '编码' ,
   mp_name VARCHAR(255)    COMMENT '中文名称' ,
   mp_price VARCHAR(255)    COMMENT '价格' ,
   mp_max_chat VARCHAR(255)    COMMENT '最大次数' ,
   mp_expireDate VARCHAR(255)    COMMENT '过期时间' ,
   CREATED_TIME DATETIME    COMMENT '创建时间' ,
   UPDATED_BY VARCHAR(32)    COMMENT '更新人' ,
   UPDATED_TIME DATETIME    COMMENT '更新时间' ,
   PRIMARY KEY (mp_id)
)  COMMENT = '会员套餐表';

DROP TABLE IF EXISTS gpt_user_member_relation;
CREATE TABLE gpt_user_member_relation(
     mr_id INT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
     user_id VARCHAR(32)    COMMENT '用户id' ,
     mp_id VARCHAR(255)    COMMENT '套餐id' ,
     status VARCHAR(255)    COMMENT '状态' ,
     chat_num VARCHAR(255)    COMMENT '发送会话次数' ,
     chat_total VARCHAR(255)    COMMENT '总次数' ,
     start_time VARCHAR(255)    COMMENT '套餐开始时间' ,
     end_time VARCHAR(255)    COMMENT '套餐结束时间' ,
     CREATED_BY VARCHAR(32)    COMMENT '创建人' ,
     CREATED_TIME DATETIME    COMMENT '创建时间' ,
     UPDATED_BY VARCHAR(32)    COMMENT '更新人' ,
     UPDATED_TIME DATETIME    COMMENT '更新时间' ,
     PRIMARY KEY (mr_id)
)  COMMENT = '用户会员关系表';

