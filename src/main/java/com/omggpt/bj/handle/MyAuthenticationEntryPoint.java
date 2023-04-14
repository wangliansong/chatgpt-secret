package com.omggpt.bj.handle;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 *
 *
 * @author Json
 * @date 2021/11/10 11:20
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(4);
        // 这里写死只做测试  请以实际为主
        map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("message", "请登录！");
        response.getWriter().println(JSONObject.toJSONString(map));
        response.getWriter().flush();
    }
}