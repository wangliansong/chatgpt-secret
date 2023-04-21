package com.secret.chatgpt.front.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.mapper.UserMapper;
import com.secret.chatgpt.front.uitls.JwtUtils;
import org.redisson.api.RedissonClient;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录校验
 * <p>
 * 第一种方式 我们这里用框架自带的 过滤器实现
 * 第二种方式 可以自己实现登录接口  去认证 其实也是 AuthenticationManager。authenticate 去认证
 *
 * @author Json
 * @date 2021/11/1 13:47
 */
public class JwtLoginFilterbak extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final RedissonClient redissonClient;

    public JwtLoginFilterbak(AuthenticationManager authenticationManager, UserMapper userMapper, RedissonClient redissonClient) {
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.redissonClient = redissonClient;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 相当于登录 认证
        String username = obtainUsername(request);
        logger.info(username);
        String password = obtainPassword(request);
        logger.info(password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 一旦调用 springSecurity认证登录成功，立即执行该方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //登录成功时，返回json格式进行提示
        String userName = obtainUsername(request);
        Object details = authResult.getDetails();
        Object s = authResult.getPrincipal();
//        Object s = authResult.getPrincipal();
        UserInfo user = userMapper.queryUserInfoByUserName(userName);
        user.setPassword(null);
        String jwt = JwtUtils.createAccessToken(user.getUserName());
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> tokenMap = new HashMap<>(4);

        // 这里写死只做测试  请以实际为主

        tokenMap.put("status","Success");
        tokenMap.put("code","200");
        tokenMap.put("message", "登陆成功！");
        tokenMap.put("token", jwt);
        map.put("status","Success");
        map.put("code", "200");
        map.put("message", "登陆成功！");
        map.put("data", tokenMap);
        response.addHeader("Authorization", jwt);
        response.getWriter().println(JSONObject.toJSONString(map));
        response.getWriter().flush();
        response.getWriter().close();
        redissonClient.getBucket(JwtUtils.loginUserInfoRedisKey + userName).set(JSONObject.toJSONString(user));
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);
    }

    @Nullable
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    @Nullable
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }

    /**
     * 一旦调用 springSecurity认证失败 ，立即执行该方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        //登录失败时，返回json格式进行提示
        Map<String, Object> map = new HashMap<String, Object>(4);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        PrintWriter out = response.getWriter();
        if (ex instanceof BadCredentialsException) {
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "账号或密码错误！");
        } else {
            // 这里还有其他的 异常 。。 比如账号锁定  过期 等等。。。
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "登陆失败！");
        }
        out.write(new ObjectMapper().writeValueAsString(map));
        response.getWriter().println(JSONObject.toJSONString(map));
        response.getWriter().flush();
        response.getWriter().close();
    }
}