package com.omggpt.bj.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OmgRedissonConfig {
 
    @Value("${spring.redis.host}")
    String redisHost;
 
    @Value("${spring.redis.port}")
    String redisPort;
 
    @Value("${spring.redis.password}")
    String redisPassword;
 
    @Value("${spring.redis.timeout}")
    Integer redisTimeout;
 
    /**
     * Redisson配置
     * @return
     */
    @Bean
    RedissonClient redissonClient() {
      //1、创建配置
        Config config = new Config();
        
        redisHost = redisHost.startsWith("redis://") ? redisHost : "redis://" + redisHost;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redisHost + ":" + redisPort)
                .setTimeout(redisTimeout);
        
        if (!StringUtils.isEmpty(redisPassword)) {
            serverConfig.setPassword(redisPassword);
        }
        
        return Redisson.create(config);
    }
    
}