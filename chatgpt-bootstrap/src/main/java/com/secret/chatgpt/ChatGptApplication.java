package com.secret.chatgpt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wls
 * @date 2023/3/22 16:50
 * ChatGptApplication
 */
@MapperScan(value = {"com.secret.**.mapper"})
@MapperScan(value = {"com.secret.**.dao"})
@SpringBootApplication
public class ChatGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatGptApplication.class, args);
    }
}
