package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

public interface DetailsService extends IService<Answer> {
    Result<List<Map<String, Object>>> questionDetails(String questionId, String token, Integer limit, Integer offset);
}

