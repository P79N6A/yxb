package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Sensitive;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface SensitiveService extends IService<Sensitive> {
    Result<Map<String, Object>> str(String content);
}
