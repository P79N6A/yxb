package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Newss;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

public interface NewsSelectService extends IService<Newss> {
    Result<List<Map<String, Object>>> selectNews(String title);
}
