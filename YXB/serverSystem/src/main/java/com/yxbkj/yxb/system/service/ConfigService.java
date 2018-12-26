package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Config;

/**
 * <p>
 * 配置管理 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-18
 */
public interface ConfigService extends IService<Config> {
   String getConfigValue(String key);
}
