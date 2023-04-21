package com.secret.chatgpt.front.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.secret.chatgpt.base.config.ChatConfig;
import com.secret.chatgpt.base.exception.ServiceException;
import com.secret.chatgpt.front.dao.UserInfoDao;
import com.secret.chatgpt.front.domain.request.VerifySecretRequest;
import com.secret.chatgpt.front.domain.vo.ApiModelVO;
import com.secret.chatgpt.front.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author hncboy
 * @date 2023/3/22 20:05
 * 鉴权相关业务实现类
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private ChatConfig chatConfig;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public String verifySecretKey(VerifySecretRequest verifySecretRequest) {
        if (BooleanUtil.isFalse(chatConfig.hasAuth())) {
            return "未设置密码";
        }

        if (StrUtil.isEmpty(verifySecretRequest.getToken())) {
            throw new ServiceException("Secret key is empty");
        }

        if (Objects.equals(verifySecretRequest.getToken(), chatConfig.getAuthSecretKey())) {
            return "Verify successfully";
        }

        throw new ServiceException("密钥无效 | Secret key is invalid");
    }

    @Override
    public ApiModelVO getApiModel() {
        ApiModelVO apiModelVO = new ApiModelVO();
        apiModelVO.setAuth(chatConfig.hasAuth());
        apiModelVO.setModel(chatConfig.getApiTypeEnum());
        return apiModelVO;
    }
}
