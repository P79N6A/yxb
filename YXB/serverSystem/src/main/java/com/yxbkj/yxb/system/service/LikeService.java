package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface LikeService extends IService<Like> {
    Result<Map<String, Object>> insertLike(String beLikedId, String token, String likeType);
}
