package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Comment;
import com.yxbkj.yxb.entity.app.InsertComment;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface CommentService extends IService<InsertComment> {
    Result<Map<String, Object>> comment(String beCommentedId, String token, Integer limit, Integer offset);
}
