package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Comment;
import com.yxbkj.yxb.entity.app.InsertComment;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.CommentMapper;
import com.yxbkj.yxb.system.mapper.DeleteCommentMapper;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.service.DeleteCommentService;
import com.yxbkj.yxb.system.service.DeleteLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class DeleteCommentServiceImpl extends ServiceImpl<DeleteCommentMapper, InsertComment> implements DeleteCommentService {
    @Autowired
    private DeleteCommentMapper deleteCommentMapper;

    @Override
    public Result<Map<String, Object>> deleteComment(String commentId) {
        EntityWrapper<InsertComment> likeEntityWrapper = new EntityWrapper<>();
        likeEntityWrapper.eq("comment_id", commentId);

        Integer delete = deleteCommentMapper.delete(likeEntityWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("delete", delete);
        return new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
    }
}
