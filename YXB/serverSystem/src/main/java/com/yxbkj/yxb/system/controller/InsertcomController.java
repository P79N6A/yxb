package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/insert")
public class InsertcomController {
    @Autowired
    private InsertCommentService insertCommentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private InsertCollectionService insertCollectionService;
    @Autowired
    private InsertQuestionService insertQuestionService;
    @Autowired
    private InsertAswerService insertAswerService;

    //添加评论
    @PostMapping("/comment")
    public Result<Map<String, Object>> postComment(String beCommentedId,
                                               @ApiParam(value = "令牌",required = true)@RequestParam(value="token", required = true)String token,
                                               String content,
                                               String commentType) {

        return insertCommentService.insertcomment(beCommentedId, token, content, commentType);
    }
    //添加评论
    @GetMapping("/comment")
    public Result<Map<String, Object>> getComment(String beCommentedId,
                                               @ApiParam(value = "令牌",required = true)@RequestParam(value="token", required = true)String token,
                                               String content,
                                               String commentType) {

        return insertCommentService.insertcomment(beCommentedId, token, content, commentType);
    }
    //添加点赞
    @GetMapping("/like")
    public Result<Map<String, Object>> like(String beLikedId,String token, String likeType) {

        return likeService.insertLike(beLikedId, token, likeType);
    }
    //添加收藏
    @GetMapping("/collection")
    public Result<Map<String, Object>> collection(String beCollectedId,String token, String collectionType) {

        return insertCollectionService.insertCollection(beCollectedId, token, collectionType);
    }
    //添加问题
    @GetMapping("/question")
    public Result<Map<String, Object>> question(String questionContent, String token) {

        return insertQuestionService.insertQuestion(questionContent, token);
    }
    //添加回答
    @PostMapping("/answer")
    public Result<Map<String, Object>> postAnswer(String questionId, String answerContent, String token) {

        return insertAswerService.insertAswer(questionId, answerContent, token);
    }
    //添加回答
    @GetMapping("/answer")
    public Result<Map<String, Object>> getAnswer(String questionId, String answerContent, String token) {

        return insertAswerService.insertAswer(questionId, answerContent, token);
    }
}
