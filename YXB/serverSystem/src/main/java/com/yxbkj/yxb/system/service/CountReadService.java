package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Answer;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface CountReadService extends IService<Answer> {
    Result<Map<String, Object>> countRead(String token);
}
