package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface InsertCollectionService extends IService<Collection> {
    Result<Map<String, Object>> insertCollection(String beCollectedId, String token, String collectionType);
}
