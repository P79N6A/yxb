package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsDetailsService newsDetailsService;
    @Autowired
    private NewsSelectService newsSelectService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private NewssService newssService;
    @GetMapping("/news")
    public Result<Map<String, Object>> news(Integer limit, Integer offset) {
        return newsService.news(limit, offset);
    }
    @GetMapping("/newss")
    public Result<Map<String, Object>> newss(String columnType, Integer limit, Integer offset) {
        return newssService.newss(columnType, limit, offset);
    }
    @GetMapping("/read")
    public Result<Map<String, Object>> read(String newId) {
        return newssService.read(newId);
    }

    //我的收藏
    @GetMapping("/coll")
    public Result<Map<String, Object>> collection(@ApiParam(value = "令牌",required = true)@RequestParam(value="token", required = true)String token,
                                                  Integer limit,
                                                  Integer offset) {

        return collectionService.myCollection(token, limit, offset);
    }

    //评论查询
    @GetMapping("/comment")
    public Result<Map<String, Object>> comment(String beCommentedId, String token, Integer limit, Integer offset) {
        return commentService.comment(beCommentedId, token, limit, offset);
    }
    //查询该用户是否点赞
    @GetMapping("/notlike")
    public Result<Boolean> notlike(String newsId,String token) {
        return newssService.notnews(newsId, token);
    }
    //查询该用户是否收藏
    @GetMapping("/notcoll")
    public Result<Boolean> notcoll(String newsId,String token) {
        return newssService.notcoll(newsId, token);
    }

    @GetMapping("/details")
    public Result<Map<String, Object>> news(String newsId) {
        return newsDetailsService.details(newsId);
    }
    @GetMapping("/select")
    public Result<List<Map<String, Object>>> selectnews(String title) {
        return newsSelectService.selectNews(title);
    }
}
