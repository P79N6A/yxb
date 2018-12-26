package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.PageQuestion;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

public interface QuestionService extends IService<PageQuestion> {
    Result<List<Map<String, Object>>> questionPage(Integer limit, Integer offset);
}
