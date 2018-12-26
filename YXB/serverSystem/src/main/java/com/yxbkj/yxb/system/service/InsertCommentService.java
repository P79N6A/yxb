package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.InsertComment;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface InsertCommentService extends IService<InsertComment> {
    Result<Map<String, Object>> insertcomment(String beCommentedId, String token, String content, String commentType);
}
