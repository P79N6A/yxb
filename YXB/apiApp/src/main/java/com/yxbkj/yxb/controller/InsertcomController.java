package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "InsertcomController",description = "添加评论and点赞")
@RestController
@RequestMapping("/insert")
public class InsertcomController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    @ApiOperation(value = "添加评论",notes = "添加评论")
    @GetMapping("/comment")
    public Result<Map<String, Object>> getInsertcom(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = true)String beCommentedId,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                 @ApiParam(value = "评论内容")@RequestParam(value = "content",required = true)String content,
                                                 @ApiParam(value = "评论类型 news/comment的ID")@RequestParam(value = "commentType",required = true)String commentType) {
        return serverSystemFeignClient.getInsertcomment(beCommentedId, token, content, commentType);
    }
    @ApiOperation(value = "添加评论",notes = "添加评论")
    @PostMapping("/comment")
    public Result<Map<String, Object>> postInsertcom(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = true)String beCommentedId,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                 @ApiParam(value = "评论内容")@RequestParam(value = "content",required = true)String content,
                                                 @ApiParam(value = "评论类型 news/comment的ID")@RequestParam(value = "commentType",required = true)String commentType) {
        return serverSystemFeignClient.postInsertcomment(beCommentedId, token, content, commentType);
    }
    @ApiOperation(value = "点赞添加",notes = "点赞添加")
    @GetMapping("/like")
    public Result<Map<String, Object>> insertli(@ApiParam(value = "被点赞ID")@RequestParam(value = "beLikedId",required = true)String beLikedId,
                                                @ApiParam(value = "令牌")@RequestParam(value="token")String token,
                                                @ApiParam(value = "点赞类型")@RequestParam(value = "likeType",required = true)String likeType) {
        return serverSystemFeignClient.insertlike(beLikedId, token, likeType);
    }
    @ApiOperation(value = "收藏添加",notes = "收藏添加")
    @GetMapping("/collection")
    public Result<Map<String, Object>> insertcoll(@ApiParam(value = "被收藏ID")@RequestParam(value = "beCollectedId",required = true)String beCollectedId,
                                                  @ApiParam(value = "token")@RequestParam(value="token")String token,
                                                  @ApiParam(value = "收藏类型")@RequestParam(value = "collectionType",required = true)String collectionType) {
        return serverSystemFeignClient.insertcollection(beCollectedId, token, collectionType);
    }
    @ApiOperation(value = "问题添加",notes = "问题添加")
    @GetMapping("/question")
    public Result<Map<String, Object>> insertque(@ApiParam(value = "问题标题")@RequestParam(value = "questionContent",required = true)String questionContent,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token) {
        return serverSystemFeignClient.insertquestion(questionContent, token);
    }


    @ApiOperation(value = "回答添加",notes = "回答添加")
    @PostMapping("/answer")
    public Result<Map<String, Object>> postInsertans(@ApiParam(value = "回答问题ID")@RequestParam(value = "questionId",required = true)String questionId,
                                                 @ApiParam(value = "回答内容",required = true)@RequestParam(value="answerContent")String answerContent,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token) {
        return serverSystemFeignClient.postInsertAnswer(questionId, answerContent, token);
    }
    @ApiOperation(value = "回答添加",notes = "回答添加")
    @GetMapping("/answer")
    public Result<Map<String, Object>> getInsertans(@ApiParam(value = "回答问题ID")@RequestParam(value = "questionId",required = true)String questionId,
                                                 @ApiParam(value = "回答内容",required = true)@RequestParam(value="answerContent")String answerContent,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token) {
        return serverSystemFeignClient.getInsertAnswer(questionId, answerContent, token);
    }
}
