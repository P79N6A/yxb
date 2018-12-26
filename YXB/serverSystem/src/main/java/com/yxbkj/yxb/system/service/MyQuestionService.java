package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

public interface MyQuestionService extends IService<Question> {
    Result<List<Map<String, Object>>> myQuestion(String token, Integer limit, Integer offset);
}
