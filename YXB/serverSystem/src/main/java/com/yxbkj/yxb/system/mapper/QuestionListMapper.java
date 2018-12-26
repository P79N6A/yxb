package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionListMapper extends BaseMapper<Question> {
    List<Question> questionList(@Param("limit") Integer limit, @Param("offset") Integer offset);
    Integer questionCount (String questionId);
}