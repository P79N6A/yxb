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

import java.util.Map;

@Api(value = "DeleteController",description = "删除评论and点赞and收藏")
@RestController
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    @ApiOperation(value = "删除点赞",notes = "删除点赞")
    @GetMapping("/like")
    public Result<Map<String, Object>> deletelike(@ApiParam(value = "文章ID")@RequestParam(value = "beLikedId",required = true)String beLikedId,
                                                  @ApiParam(value = "token")@RequestParam(value = "token",required = true)String token) {
        return serverSystemFeignClient.deletelike(beLikedId, token);
    }
    @ApiOperation(value = "删除评论",notes = "删除评论")
    @GetMapping("/comment")
    public Result<Map<String, Object>> deletecomment(@ApiParam(value = "被删除点赞ID")@RequestParam(value = "commentId",required = true)String commentId) {
        return serverSystemFeignClient.deletecomment(commentId);
    }
    @ApiOperation(value = "删除收藏",notes = "删除收藏")
    @GetMapping("/collection")
    public Result<Map<String, Object>> deletecollection(@ApiParam(value = "文章ID")@RequestParam(value = "beCollectedId",required = true)String beCollectedId,
                                                        @ApiParam(value = "token")@RequestParam(value = "token",required = true)String token) {
        return serverSystemFeignClient.deletecollection(beCollectedId, token);
    }
    @ApiOperation(value = "删除我的提问",notes = "删除我的提问")
    @GetMapping("/deleteQuestion")
    public Result<Map<String, Object>> deleteQuestion(@ApiParam(value = "提问ID")@RequestParam(value = "questionId",required = true)String questionId,
                                                      @ApiParam(value = "token")@RequestParam(value = "token",required = true)String token) {
        return serverSystemFeignClient.deleteQuestion(questionId, token);
    }
    @ApiOperation(value = "删除我的回答",notes = "删除我的回答")
    @GetMapping("/deleteAnswer")
    public Result<Map<String, Object>> deleteAnswer(@ApiParam(value = "回答ID")@RequestParam(value = "answerId",required = true)String answerId,
                                                    @ApiParam(value = "token")@RequestParam(value = "token",required = true)String token) {
        return serverSystemFeignClient.deleteAnswer(answerId, token);
    }
}
