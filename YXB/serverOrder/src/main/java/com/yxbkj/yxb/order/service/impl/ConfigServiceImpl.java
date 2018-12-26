package com.yxbkj.yxb.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Config;
import com.yxbkj.yxb.order.mapper.ConfigMapper;
import com.yxbkj.yxb.order.service.ConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置管理 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-18
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Override
    public String getConfigValue(String key) {
        EntityWrapper<Config> wrapper = new EntityWrapper<>();
        wrapper.eq("config_key",key);
        Config config = selectOne(wrapper);
        return config==null?"":config.getConfigValue();
    }
}
