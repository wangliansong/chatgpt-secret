package com.secret.chatgpt.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.chatgpt.base.domain.entity.UserMemberRelationDO;
import com.secret.chatgpt.front.mapper.UserMemberRelationMapper;
import com.secret.chatgpt.front.service.UserMemberRelationService;
import org.springframework.stereotype.Service;

@Service
public class UserMemberRelationServiceImpl extends ServiceImpl<UserMemberRelationMapper, UserMemberRelationDO> implements UserMemberRelationService {


    @Override
    public UserMemberRelationDO selectByUserId(Long userId) {
        QueryWrapper<UserMemberRelationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        UserMemberRelationDO userMemberRelationDO = baseMapper.selectOne(queryWrapper);
        return userMemberRelationDO;
    }


}
