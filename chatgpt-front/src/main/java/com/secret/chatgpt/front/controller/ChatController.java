package com.secret.chatgpt.front.controller;

import com.secret.chatgpt.base.annotation.FrontPreAuth;
import com.secret.chatgpt.base.handler.response.R;
import com.secret.chatgpt.front.domain.request.ChatProcessRequest;
import com.secret.chatgpt.front.domain.vo.ChatConfigVO;
import com.secret.chatgpt.front.service.ChatService;
import com.secret.chatgpt.front.uitls.PerformaceCalcContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hncboy
 * @date 2023/3/22 19:47
 * 聊天相关接口
 */
@FrontPreAuth
@AllArgsConstructor
@Tag(name = "聊天相关接口")
@RestController
@RequestMapping("/api")
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "配置信息")
    @PostMapping("/config")
    public R<ChatConfigVO> chatConfig() {
        return R.data(chatService.getChatConfig());
    }

    @Operation(summary = "消息处理")
    @PostMapping("/chat-process")
    public ResponseBodyEmitter chatProcess(@RequestBody @Validated ChatProcessRequest chatProcessRequest, HttpServletRequest request, HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String token = request.getHeader("token");
        //log.info("token |{}|",token);
        PerformaceCalcContext.setValue("token",token);
        return chatService.chatProcess(chatProcessRequest);
    }
}
