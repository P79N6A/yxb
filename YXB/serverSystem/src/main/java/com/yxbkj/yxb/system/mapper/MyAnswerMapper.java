package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.MyAnswer;
import com.yxbkj.yxb.entity.app.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 我的回答 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface MyAnswerMapper extends BaseMapper<Answer> {
}
