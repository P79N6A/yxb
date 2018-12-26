package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.InsertComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 添加评论 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface InsertCommentMapper extends BaseMapper<InsertComment> {
    List<InsertComment> comment(@Param("beCommentedId") String beCommentedId,
                                @Param("memberId") String memberId,
                                @Param("content") String content,
                                @Param("commentType") String commentType);
}
