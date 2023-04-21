package com.secret.chatgpt.front.service.impl;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.secret.chatgpt.base.config.ChatConfig;
import com.secret.chatgpt.base.domain.entity.UserMemberRelationDO;
import com.secret.chatgpt.base.enums.ApiTypeEnum;
import com.secret.chatgpt.base.exception.ServiceException;
import com.secret.chatgpt.base.util.ObjectMapperUtil;
import com.secret.chatgpt.front.api.apikey.ApiKeyChatClientBuilder;
import com.secret.chatgpt.front.domain.request.ChatProcessRequest;
import com.secret.chatgpt.front.domain.vo.ChatConfigVO;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.handler.emitter.ChatMessageEmitterChain;
import com.secret.chatgpt.front.handler.emitter.IpRateLimiterEmitterChain;
import com.secret.chatgpt.front.handler.emitter.ResponseEmitterChain;
import com.secret.chatgpt.front.handler.emitter.SensitiveWordEmitterChain;
import com.secret.chatgpt.front.service.ChatService;
import com.secret.chatgpt.front.service.UserMemberRelationService;
import com.secret.chatgpt.front.uitls.UserInfoFromTokenUntils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;

/**
 * @author hncboy
 * @date 2023/3/22 19:41
 * 聊天相关业务实现类
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatConfig chatConfig;
    @Autowired
    private UserMemberRelationService userMemberRelationService;

    @Override
    public ChatConfigVO getChatConfig() {
        ChatConfigVO chatConfigVO = new ChatConfigVO();
        chatConfigVO.setApiModel(chatConfig.getApiTypeEnum());
        if (chatConfig.getApiTypeEnum() == ApiTypeEnum.ACCESS_TOKEN || BooleanUtil.isFalse(chatConfig.getIsShowBalance())) {
            chatConfigVO.setBalance(StrUtil.DASHED);
        } else {
            // TODO 加缓存
            chatConfigVO.setBalance(String.valueOf(ApiKeyChatClientBuilder.buildOpenAiClient().creditGrants().getTotalAvailable()));
        }
        chatConfigVO.setHttpsProxy(StrUtil.isAllNotEmpty(chatConfig.getHttpProxyHost(), String.valueOf(chatConfig.getHttpProxyPort()))
                ? String.format("%s:%s", chatConfig.getHttpProxyHost(), chatConfig.getHttpProxyPort())
                : StrPool.DASHED);
        chatConfigVO.setReverseProxy(chatConfig.getApiReverseProxy());
        chatConfigVO.setSocksProxy(StrPool.DASHED);
        chatConfigVO.setTimeoutMs(chatConfig.getTimeoutMs());
        return chatConfigVO;
    }

    @Override
    public ResponseBodyEmitter chatProcess(ChatProcessRequest chatProcessRequest) {
        UserInfo userInfo = UserInfoFromTokenUntils.getUserInfo();

        UserMemberRelationDO userMemberRelationDO = userMemberRelationService.selectByUserId(userInfo.getId());
        Long useChatNum = userMemberRelationDO.getUseChatNum();
        Long maxChat = userMemberRelationDO.getMaxChat();
        //LocalDateTime endTime = userMemberRelationDO.getEndTime();
        if (useChatNum >= maxChat){
            throw new ServiceException("您的使用次数已用尽，请充值");
        }
        // 超时时间设置 3 分钟
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
        emitter.onCompletion(() -> log.debug("请求参数：{}，Front-end closed the emitter connection.", ObjectMapperUtil.toJson(chatProcessRequest)));
        emitter.onTimeout(() -> log.error("请求参数：{}，Back-end closed the emitter connection.", ObjectMapperUtil.toJson(chatProcessRequest)));

        // 构建 emitter 处理链路
        ResponseEmitterChain ipRateLimiterEmitterChain = new IpRateLimiterEmitterChain();
        ResponseEmitterChain sensitiveWordEmitterChain = new SensitiveWordEmitterChain();
        sensitiveWordEmitterChain.setNext(new ChatMessageEmitterChain());
        ipRateLimiterEmitterChain.setNext(sensitiveWordEmitterChain);
        ipRateLimiterEmitterChain.doChain(chatProcessRequest, emitter);
        return emitter;
    }
}
