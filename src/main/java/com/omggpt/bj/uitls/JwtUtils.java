package com.omggpt.bj.uitls;

import com.omggpt.bj.config.JwtConfig;
import com.omggpt.bj.enums.ResultCodeEnum;
import com.omggpt.bj.exceptions.JwtApiException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt 工具类
 *
 * @author Json
 * @date 2021/11/15 16:38
 */
@Slf4j
public class JwtUtils {

    public static final String securityKey = "1873hdjh3jdhjekh1873hdjh3jdhjekh181873hdjh3jdhjekh1873hdjh3jdhjekh18";

    public static final String loginUserInfoRedisKey = "ekh1873hdjh3jdhjekh181873hd";

    /**
     * jwt 载荷信息 key
     */
    public static final String JWT_PAYLOAD_USER_KEY = "user";
    /**
     * 刷新token次数 默认为0起始
     */
    public static final String REFRESH_TOKEN_NUMBER = "refreshTokenNumber";

    /**
     * access-token
     */
    public static final String ACCESS_TOKEN = "access-token";


    /**
     * 加密token
     *
     * @param userInfo  载荷中的数据
     * @param jwtConfig jwt 配置
     * @return JWT
     */
    public static String createAccessToken(Object userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, 0);
        String sss =  Jwts.builder()
                .setClaims(map)
//                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + 36000 * 1000))
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
        System.out.println("登录返回的token:"+sss);
        return sss;
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig) {
        return createRefreshToken(userInfo, jwtConfig, 0);
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig, int refreshTokenNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, refreshTokenNumber);
        String sss =  Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getRefreshTokenSecret())
                .compact();
        return sss;
    }


    /**
     * 解析 refreshToken
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserAccessToken(String token) {
        return parserToken(token);
    }

    /**
     * 解析  token
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserRefreshToken(String token, JwtConfig jwtConfig) {
        return parserToken(token);
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public static Claims parserToken(String token) {
        try {
            System.out.println("请求携带的token:"+token);
            return Jwts.parser()
                    .setSigningKey(JwtUtils.securityKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("token{}过期", token, e);
            throw new JwtApiException(ResultCodeEnum.JWT_EXPIRED.getCode(), ResultCodeEnum.JWT_EXPIRED.getMessage());
        } catch (SignatureException e) {
            log.error("token=[{}], 签名", token, e);
            throw new JwtApiException(ResultCodeEnum.JWT_SIGNATURE.getCode(), ResultCodeEnum.JWT_SIGNATURE.getMessage());
        } catch (Exception e) {
            log.error("token=[{}]解析错误 message:{}", token, e.getMessage(), e);
            throw new JwtApiException(ResultCodeEnum.JWT_ERROR.getCode(), ResultCodeEnum.JWT_ERROR.getMessage());
        }
    }


    private static String createJTI() {
        return new String(java.util.Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }
}
