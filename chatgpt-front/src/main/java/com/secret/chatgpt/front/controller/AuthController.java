package com.secret.chatgpt.front.controller;

import com.secret.chatgpt.base.handler.response.R;
import com.secret.chatgpt.front.domain.request.VerifySecretRequest;
import com.secret.chatgpt.front.domain.vo.ApiModelVO;
import com.secret.chatgpt.front.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hncboy
 * @date 2023/3/22 19:48
 * 鉴权相关接口
 */
@AllArgsConstructor
@Tag(name = "鉴权相关接口")
@RestController
@RequestMapping
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "模型信息")
    @PostMapping("/session")
    public R<ApiModelVO> getApiModel() {
        return R.data(authService.getApiModel());
    }

    @Operation(summary = "验证密码")
    @PostMapping("/verify")
    public R<Void> verifySecretKey(@RequestBody @Validated VerifySecretRequest verifySecretRequest) {
        return R.success(authService.verifySecretKey(verifySecretRequest));
    }
}
