package com.secret.chatgpt.front.filter;

import com.alibaba.fastjson.JSONObject;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.mapper.UserMapper;
import com.secret.chatgpt.front.uitls.JwtUtils;
import io.jsonwebtoken.Claims;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请求校验
 *
 * @author Json
 * @date 2021/11/6 14:34
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {


    private RedissonClient redissonClient;
    private final UserMapper userMapper;


    public JwtVerifyFilter(AuthenticationManager authenticationManager,UserMapper userMapper, RedissonClient redissonClient) {
        super(authenticationManager);
        this.redissonClient = redissonClient;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //配置白名单
        if (!request.getServletPath().equals("/api/register")) {

            //String authorization = request.getHeader("Authorization");
            String authorization = request.getHeader("token");

            if (authorization == null) {
                throw new ServletException("请先登录！");
            }
            Claims claims = JwtUtils.parserToken(authorization);
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authorities.add(new SimpleGrantedAuthority("user:resource"));
            String user = (String) (claims.get(JwtUtils.JWT_PAYLOAD_USER_KEY));
            UserInfo userInfo = JSONObject.parseObject(user, UserInfo.class);
            RBucket<String> bucket = redissonClient.getBucket(JwtUtils.loginUserInfoRedisKey + userInfo.getUserName());
            if (bucket == null) {
                throw new ServletException("请先登录！");
            }
            //TODO_WANGLS 注释：如果同一个用户当前从前端传过来的token，跟redis中的token不一致
            //TODO_WANGLS 注释：则判断那个新
            String tokenFromRedis = bucket.get();
            if (!authorization.equals(tokenFromRedis)){
                Claims claimsFromRedis = JwtUtils.parserToken(tokenFromRedis);
                Date redisExpiration = claimsFromRedis.getExpiration();
                Date expiration = claims.getExpiration();
                //TODO_WANGLS 注释： 如果当前登录的token时间大，则放入 reids 并放行，否则不放行
                if(expiration.after(redisExpiration)){
                    redissonClient.getBucket(JwtUtils.loginUserInfoRedisKey + user).set(authorization);
                    UserInfo newUserInfo = JSONObject.parseObject(tokenFromRedis, UserInfo.class);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            (newUserInfo, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    newUserInfo.setLastLoginTime(new Date());
                    userMapper.updateById(newUserInfo);
                }else {

                    throw new ServletException("请重新登录！");
                }
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (userInfo, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }
        chain.doFilter(request, response);
    }
}