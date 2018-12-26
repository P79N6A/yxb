package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.InsertComment;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface DeleteCommentService extends IService<InsertComment> {
    Result<Map<String, Object>> deleteComment(String commentId);
}
