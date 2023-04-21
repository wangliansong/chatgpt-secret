package com.secret.chatgpt.front.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "登录返回结果")
public class LoginResponseVO {
    @Schema(title = "状态码")
    private int code;

    @Schema(title = "message")
    private String message;
    @Schema(title = "token")
    private String token;
}
