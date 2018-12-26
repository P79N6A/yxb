package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface NewsService extends IService<News> {
    Result<Map<String, Object>> news (Integer limit, Integer offset);
}
