package com.secret.chatgpt.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.mapper.UserMapper;
import com.secret.chatgpt.front.service.IUserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedissonClient redissonClient;

    private static final long LOCK_WAIT_TIME = 0;
    Long REDIS_LEASE_TIME = -1L;

    private final static String registerKEY = "OMGGPT_REGISTER_KEY_HENKEIOYLMMEN34223";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头 后续再做功能权限
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        UserInfo userInfo = userMapper.queryUserInfoByUserName(username);
        if (null == userInfo) {
            throw new UsernameNotFoundException("用户不存在或密码不正确");
        }
        return User.builder().username(userInfo.getUserName())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .authorities(authorities).build();

    }

    @Override
    public void register(UserInfo user) {
        log.info("注册用户开始:user:{}", JSONObject.toJSONString(user));
        RLock lock = redissonClient.getLock(registerKEY.concat(user.getUserName()));
        boolean isGetLock = false;
        try {
            isGetLock = lock.tryLock(LOCK_WAIT_TIME, REDIS_LEASE_TIME, TimeUnit.MILLISECONDS);
            if (isGetLock) {
                QueryWrapper<UserInfo> queryParam = new QueryWrapper();
                queryParam.eq("user_name", user.getUserName());
                UserInfo userInfo = userMapper.selectOne(queryParam);
                if (null != userInfo) {
                    log.error("用户已存在，请检查!");
                    throw new Exception("用户已存在，请检查!");
                }
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                userMapper.insert(user);
            }
        } catch (Exception e) {
            log.error("执行[UserServiceImpl.register]|{}|{}|{}|",
                    JSONObject.toJSONString(user), e);
        } finally {
            if (isGetLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public void login(UserInfo user) {
    }

}
