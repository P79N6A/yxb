package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Comment;
import com.yxbkj.yxb.entity.app.InsertComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface CommentMapper extends BaseMapper<InsertComment> {
    List<InsertComment> comment(@Param("beCommentedId") String beCommentedId,
                          @Param("limit") Integer limit,
                          @Param("offset") Integer offset);
    List<InsertComment> mycomment(@Param("memberId") String memberId,
                            @Param("limit") Integer limit,
                          @Param("offset") Integer offset);
    int countLike(@Param("beCommentedId") String beCommentedId);
    int mycountcommentLike(@Param("memberId") String memberId);
    int countcomment(@Param("beCommentedId") String beCommentedId);
    int mycountcomment(@Param("memberId") String memberId);
}
