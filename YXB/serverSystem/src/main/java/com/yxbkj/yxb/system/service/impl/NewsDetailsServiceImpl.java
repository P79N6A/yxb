package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.NewsDetailsMapper;
import com.yxbkj.yxb.system.mapper.NewsMapper;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.NewsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsDetailsServiceImpl extends ServiceImpl<NewsDetailsMapper, News> implements NewsDetailsService {
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private ConfigService sonfigService;

    public List<News> selectId(String newsId) {
        List<News> news = newsMapper.selectList(new EntityWrapper<News>().eq("news_id",newsId).eq("validity","10000001"));
        return news;
    }

    @Override
    public Result<Map<String, Object>> details(String newsId) {
        Result<Map<String, Object>> result = null;
        List<News> news = selectId(newsId);
        String systemImageUrl = sonfigService.getConfigValue("systemImageUrl");
        Map<String, Object> map = new HashMap<>();

        for (News snew : news){
            snew.setImg(systemImageUrl+snew.getImg());
        }
        map.put("test", news);
        result = new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        return result;
    }

}
