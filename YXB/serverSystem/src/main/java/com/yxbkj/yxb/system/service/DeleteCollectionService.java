package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface DeleteCollectionService extends IService<Collection> {
    Result<Map<String, Object>> deleteCollection(String beCollectedId, String token);
}
