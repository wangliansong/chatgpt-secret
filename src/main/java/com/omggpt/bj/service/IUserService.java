package com.omggpt.bj.service;

import com.omggpt.bj.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {



    public void register(UserInfo user);

    public void login (UserInfo user);
}
