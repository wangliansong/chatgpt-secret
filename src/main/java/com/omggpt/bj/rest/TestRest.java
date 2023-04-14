package com.omggpt.bj.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRest {

    @GetMapping("/test")
    public void test(){
        System.out.println("123");
    }

    @GetMapping("/login")
    public void login(String userName,String password){
        System.out.println("123");
    }
}
