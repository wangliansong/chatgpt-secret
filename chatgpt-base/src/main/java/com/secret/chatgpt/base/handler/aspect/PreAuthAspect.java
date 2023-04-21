package com.secret.chatgpt.base.handler.aspect;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.secret.chatgpt.base.config.ChatConfig;
import com.secret.chatgpt.base.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author hncboy
 * @date 2023/3/23 00:19
 * 填写注释
 */
@Aspect
@Component
public class PreAuthAspect {

    @Resource
    private ChatConfig chatConfig;

    @Pointcut("@annotation(com.secret.chatgpt.base.annotation.FrontPreAuth) || @within(com.secret.chatgpt.base.annotation.FrontPreAuth)")
    public void pointcut() {

    }

    /**
     * 切 方法 和 类上的 @PreAuth 注解
     *
     * @param point 切点
     * @return Object
     * @throws Throwable 没有权限的异常
     */
    @Around("pointcut()")
    public Object checkAuth(ProceedingJoinPoint point) throws Throwable {
        // 没有设置鉴权
        if (BooleanUtil.isFalse(chatConfig.hasAuth())) {
            return point.proceed();
        }

        String authorization = ServletUtil.getHeader(WebUtil.getRequest(), "Authorization", StandardCharsets.UTF_8);
        /************************************
         * TODO_WANGLS wangls @Date: 2023-04-12 12:34:52
         *  注释: 鉴权
         *
         */
        //        if (StrUtil.isBlank(authorization) || !authorization.replace("Bearer ", "").trim().equals(chatConfig.getAuthSecretKey().trim())) {
//            throw new AuthException("Error: 无访问权限 | No access rights");
//        }

        return point.proceed();
    }
}
