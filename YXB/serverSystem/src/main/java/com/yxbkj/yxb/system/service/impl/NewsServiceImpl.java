package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.NewsMapper;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private ConfigService sonfigService;

    @Override
    public Result<Map<String, Object>> news(Integer limit, Integer offset) {
        Result<Map<String, Object>> result = null;

        Page<News> page = new Page<>(limit, offset);

        Page<News> page1 = selectPage(page, new EntityWrapper<News>().eq("validity",10000001).orderBy("sort desc"));

        List<News> records = page1.getRecords();

        String systemImageUrl = sonfigService.getConfigValue("systemImageUrl");
        Map<String, Object> map = new HashMap<>();

        for (News news : records){
            news.setImg(systemImageUrl+news.getImg());
        }
        map.put("test", records);
        result = new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        return result;
    }

}
