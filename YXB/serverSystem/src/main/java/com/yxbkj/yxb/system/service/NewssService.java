package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.app.Newss;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface NewssService extends IService<Newss> {
    Result<Map<String, Object>> newss(String columnType, Integer limit, Integer offset);
    Result<Map<String, Object>> read(String newId);
    Result<Boolean> notnews(String newId, String token);
    Result<Boolean> notcoll(String newId, String token);
}
