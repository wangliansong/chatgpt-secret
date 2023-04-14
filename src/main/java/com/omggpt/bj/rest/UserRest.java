package com.omggpt.bj.rest;

import com.omggpt.bj.entity.UserInfo;
import com.omggpt.bj.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRest {
    private static Logger log = LoggerFactory.getLogger(UserRest.class);
    @Autowired
    IUserService userService;

    /**
     * 用户登录
     * @param userName
     * @param password
     */
    @GetMapping("/login")
    public void login(String userName,String password){

    }

    /**
     * 用户注册
     * @param userInfo
     */
    @PostMapping("/register")
    public void register(@RequestBody UserInfo userInfo){
        userService.register(userInfo);
    }
}
