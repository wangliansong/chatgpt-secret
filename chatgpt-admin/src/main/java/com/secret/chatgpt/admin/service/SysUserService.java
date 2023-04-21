package com.secret.chatgpt.admin.service;

import com.secret.chatgpt.admin.domain.request.SysUserLoginRequest;

/**
 * @author hncboy
 * @date 2023/3/28 12:43
 * 系统用户相关接口
 */
public interface SysUserService {

    /**
     * 登录
     *
     * @param sysUserLoginRequest 登录参数
     */
    void login(SysUserLoginRequest sysUserLoginRequest);
}
