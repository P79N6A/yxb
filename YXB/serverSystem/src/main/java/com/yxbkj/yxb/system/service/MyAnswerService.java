package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.MyAnswer;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

public interface MyAnswerService extends IService<Answer> {
    Result<Map<String, Object>> myAnswer(String token, Integer limit, Integer offset);
}
