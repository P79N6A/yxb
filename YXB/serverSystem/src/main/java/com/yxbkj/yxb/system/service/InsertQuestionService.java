package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface InsertQuestionService extends IService<Question> {
    Result<Map<String, Object>> insertQuestion(String questionContent, String token);
}
