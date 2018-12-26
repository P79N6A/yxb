package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.app.QuestionId;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 我的提问 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface MyQuestionMapper extends BaseMapper<Question> {
    List<Question> myQuestion(@Param("creatorId") String creatorId,
                              @Param("limit") Integer limit,
                              @Param("offset") Integer offset);
    List<QuestionId> question(@Param("questionId") String questionId,
                              @Param("limit") Integer limit,
                              @Param("offset") Integer offset);
    int myQuestionCount (@Param("creatorId") String creatorId);
}
