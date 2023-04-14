package com.omggpt.bj.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
public class JwtConfig {

    @Value("${omg.jwt.acessTokenExpire:180}")
    private Integer accessTokenExpire;

    /**
     * 主题
     */
    @Value("${omg.jwt.accessTokenSecret:1873hdjh3jdhjekh}")
    private String accessTokenSecret;

    @Value("${omg.jwt.refreshTokenExpire:180}")
    private Integer refreshTokenExpire;

    @Value("${omg.jwt.refreshTokenSecret:1873hdjh3jdhjekh}")
    private String refreshTokenSecret;

}
