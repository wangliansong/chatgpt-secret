package com.secret.chatgpt.front.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hncboy
 * @date 2023/3/22 23:03
 * 验证密码请求参数
 */
@Data
@Schema(title = "验证密码请求参数")
public class UserDto {

    @NotNull(message = "密码不能为空")
    @Schema(title = "用户名")
    private String password;
    @NotNull(message = "密码不能为空")
    @Schema(title = "密码")
    private String username;


}
