package com.secret.chatgpt.front.controller;

import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Tag(name = "登录接口")
@RestController
@RequestMapping("/api")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
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