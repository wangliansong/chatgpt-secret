package com.secret.chatgpt.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.chatgpt.base.domain.entity.SensitiveWordDO;
import com.secret.chatgpt.base.mapper.SensitiveWordMapper;
import com.secret.chatgpt.base.service.SensitiveWordService;
import org.springframework.stereotype.Service;

/**
 * @author hncboy
 * @date 2023/3/28 20:47
 * 敏感词业务实现类
 */
@Service("CommonSensitiveWordServiceImpl")
public class SensitiveWordServiceImpl  extends ServiceImpl<SensitiveWordMapper, SensitiveWordDO> implements SensitiveWordService {
}
