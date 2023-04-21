package com.secret.chatgpt.front.uitls;

import com.alibaba.fastjson.JSONObject;
import com.secret.chatgpt.front.entity.UserInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

/************************************
 * TODO_WANGLS wangls @Date: 2023-04-20 11:50:16
 *  注释: 从token中获取用户的信息，发送请求的时候需要把token设置到ThreadLocal
 *
 */
@Slf4j
public class UserInfoFromTokenUntils {
    @Nullable
    public static UserInfo getUserInfo() {
        String token = (String) PerformaceCalcContext.getValue("token");
        if (null != token && !token.isEmpty()){
            Claims claims = JwtUtils.parserToken(token);
            String user = (String)claims.get(JwtUtils.JWT_PAYLOAD_USER_KEY);
            UserInfo userInfo = JSONObject.parseObject(user, UserInfo.class);
            return userInfo;
        }
        return null;

    }
}
