package com.secret.chatgpt.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.chatgpt.base.domain.entity.UserMemberRelationDO;
import org.springframework.stereotype.Service;

@Service
public interface UserMemberRelationService extends IService<UserMemberRelationDO> {
    UserMemberRelationDO selectByUserId(Long userId);

}
