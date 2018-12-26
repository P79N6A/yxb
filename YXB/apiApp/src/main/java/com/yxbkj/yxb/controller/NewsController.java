package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "NewsController",description = "获取新闻列表and详情")
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    @ApiOperation(value = "old获取新闻列表",notes = "old获取新闻列表")
    @GetMapping("/news")
    public Result<Map<String, Object>> news( Integer limit, Integer offset) {
        return serverSystemFeignClient.news(limit, offset);
    }

    @ApiOperation(value = "new获取新闻列表",notes = "new获取新闻列表")
    @GetMapping("/newss")
    public Result<Map<String, Object>> newss(@ApiParam(value = "类别")@RequestParam(value = "columnType",required = false) String columnType, Integer limit, Integer offset) {
        return serverSystemFeignClient.newss(columnType, limit, offset);
    }

    @ApiOperation(value = "获取新闻详情",notes = "获取新闻详情")
    @GetMapping("/read")
    public Result<Map<String, Object>> read(String newId) {
        return serverSystemFeignClient.read(newId);
    }

    @ApiOperation(value = "获取我的收藏",notes = "获取我的收藏")
    @GetMapping("/coll")
    public Result<Map<String, Object>> collection(@ApiParam(value = "令牌")@RequestParam(value = "token") String  token,
                                                  Integer limit,
                                                  Integer offset) {
        return serverSystemFeignClient.coll(token, limit, offset);
    }

    @ApiOperation(value = "获取新闻详情",notes = "获取新闻详情")
    @GetMapping("/details")
    public Result<Map<String, Object>> details(String newsId) {
        return serverSystemFeignClient.details(newsId);
    }

    @ApiOperation(value = "咨询搜索",notes = "咨询搜索")
    @GetMapping("/select")
    public Result<List<Map<String, Object>>> select(String title) {
        return serverSystemFeignClient.selectnews(title);
    }

    @ApiOperation(value = "查询该用户是否对该文章点赞",notes = "查询该用户是否对该文章点赞")
    @GetMapping("/notlike")
    public Result<Boolean> notlike(@ApiParam(value = "文章ID")@RequestParam(value = "newsId",required = true, defaultValue = "")String newsId,
                                   String token) {
        return serverSystemFeignClient.notlike(newsId, token);
    }
    @ApiOperation(value = "查询该用户是否对该文章收藏",notes = "查询该用户是否对该文章收藏")
    @GetMapping("/notcoll")
    public Result<Boolean> notcoll(@ApiParam(value = "文章ID")@RequestParam(value = "newsId",required = true, defaultValue = "")String newsId,
                                   String token) {
        return serverSystemFeignClient.notcoll(newsId, token);
    }

    //评论查询
    @ApiOperation(value = "评论查询",notes = "评论查询")
    @GetMapping("/comment")
    public Result<Map<String, Object>> comment(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = true, defaultValue = "")String beCommentedId,
                                               @ApiParam(value = "令牌")@RequestParam(value = "token",required = false,defaultValue = "") String  token,
                                               Integer limit,
                                               Integer offset) {
        return serverSystemFeignClient.comment(beCommentedId, token, limit, offset);
    }
}
