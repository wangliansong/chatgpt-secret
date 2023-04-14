package com.omggpt.bj.filter;

import com.alibaba.fastjson.JSONObject;
import com.omggpt.bj.entity.UserInfo;
import com.omggpt.bj.service.IUserService;
import com.omggpt.bj.uitls.JwtUtils;
import io.jsonwebtoken.Claims;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 请求校验
 *
 * @author Json
 * @date 2021/11/6 14:34
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {


    private RedissonClient redissonClient;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RedissonClient redissonClient) {
        super(authenticationManager);
        this.redissonClient = redissonClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //配置白名单
        if (!request.getServletPath().equals("/api/register")) {

            String authorization = request.getHeader("Authorization");
            if (authorization == null) {
                throw new ServletException("请先登录！");
            }
            Claims claims = JwtUtils.parserToken(authorization);
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authorities.add(new SimpleGrantedAuthority("user:resource"));
            String userName = (String) (claims.get(JwtUtils.JWT_PAYLOAD_USER_KEY));
            RBucket<String> bucket = redissonClient.getBucket(JwtUtils.loginUserInfoRedisKey + userName);
            if (bucket == null) {
                throw new ServletException("请先登录！");
            }
            UserInfo userInfo = JSONObject.parseObject(bucket.get(), UserInfo.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (userInfo, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        chain.doFilter(request, response);
    }
}