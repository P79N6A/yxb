package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Answer;

import java.util.List;
import java.util.Map;

public interface AnswerMapper extends BaseMapper<Answer> {
    List<Map<String, Object>> questionPage(String questionId);
}
