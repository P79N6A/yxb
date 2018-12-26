package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.app.Questions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 咨询搜索 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface QuestionSelectMapper extends BaseMapper<Questions> {
    List<Questions> selectQuestion(@Param("title") String title);
}
