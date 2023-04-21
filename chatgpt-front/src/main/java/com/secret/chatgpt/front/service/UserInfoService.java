package com.secret.chatgpt.front.service;

import com.secret.chatgpt.front.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    UserInfo findByUsername(String username);
}
