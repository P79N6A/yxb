package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.app.MyCollection;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface CollectionService extends IService<Collection> {
    Result<Map<String, Object>> myCollection(String token, Integer limit, Integer offset);
}
