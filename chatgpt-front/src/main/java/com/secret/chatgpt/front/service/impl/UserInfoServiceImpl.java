package com.secret.chatgpt.front.service.impl;

import com.secret.chatgpt.front.dao.UserInfoDao;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        UserInfo userInfo = userInfoDao.findByUsername(username);
        return userInfo;
    }
}
