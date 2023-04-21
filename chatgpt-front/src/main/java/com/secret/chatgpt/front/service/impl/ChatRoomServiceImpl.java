package com.secret.chatgpt.front.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.chatgpt.base.domain.entity.ChatMessageDO;
import com.secret.chatgpt.base.domain.entity.ChatRoomDO;
import com.secret.chatgpt.base.util.WebUtil;
import com.secret.chatgpt.front.entity.UserInfo;
import com.secret.chatgpt.front.mapper.ChatRoomMapper;
import com.secret.chatgpt.front.service.ChatRoomService;
import com.secret.chatgpt.front.uitls.UserInfoFromTokenUntils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hncboy
 * @date 2023/3/25 16:31
 * 聊天室相关业务实现类
 */
@Service("FrontChatRoomServiceImpl")
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoomDO> implements ChatRoomService {

    @Override
    public ChatRoomDO createChatRoom(ChatMessageDO chatMessageDO) {
        UserInfo userInfo = UserInfoFromTokenUntils.getUserInfo();
        ChatRoomDO chatRoom = new ChatRoomDO();
        chatRoom.setId(IdWorker.getId());
        assert userInfo != null;
        chatRoom.setUserId(userInfo.getId());
        chatRoom.setApiType(chatMessageDO.getApiType());
        chatRoom.setIp(WebUtil.getIp());
        chatRoom.setFirstChatMessageId(chatMessageDO.getId());
        chatRoom.setFirstMessageId(chatMessageDO.getMessageId());
        // 取一部分内容当标题
        chatRoom.setTitle(StrUtil.sub(chatMessageDO.getContent(), 0, 50));
        chatRoom.setCreateTime(new Date());
        chatRoom.setUpdateTime(new Date());
        save(chatRoom);
        return chatRoom;
    }
}
