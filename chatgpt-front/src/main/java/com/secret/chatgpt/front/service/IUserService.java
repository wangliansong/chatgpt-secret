package com.secret.chatgpt.front.service;

import com.secret.chatgpt.front.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {



    public void register(UserInfo user);

    public void login (UserInfo user);
}
