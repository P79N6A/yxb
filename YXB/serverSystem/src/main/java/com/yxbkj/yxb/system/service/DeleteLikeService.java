package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface DeleteLikeService extends IService<Like> {
    Result<Map<String, Object>> deleteLike(String likeId, String token);
}
