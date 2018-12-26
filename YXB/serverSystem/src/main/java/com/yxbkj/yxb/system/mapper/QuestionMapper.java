package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.PageQuestion;
import feign.Param;

import java.util.List;
import java.util.Map;

public interface QuestionMapper extends BaseMapper<PageQuestion> {
    List<Map<String, Object>> questionPage();
}