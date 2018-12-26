package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    private DeleteLikeService deleteService;
    @Autowired
    private DeleteCollectionService deleteCollectionService;
    @Autowired
    private DeleteCommentService deleteCommentService;
    @Autowired
    private DeleteQuestionService deleteQuestionService;
    @Autowired
    private DeleteAnswerService deleteAnswerService;



    //删除点赞
    @GetMapping("/comment")
    public Result<Map<String, Object>> comment(String commentId) {

        return deleteCommentService.deleteComment(commentId);
    }
    //删除评论
    @GetMapping("/like")
    public Result<Map<String, Object>> like(String beLikedId, String token) {

        return deleteService.deleteLike(beLikedId, token);
    }
    //删除收藏
    @GetMapping("/collection")
    public Result<Map<String, Object>> collection(String beCollectedId, String token) {

        return deleteCollectionService.deleteCollection(beCollectedId, token);
    }
    //删除我的提问
    @GetMapping("/deleteQuestion")
    public Result<Map<String, Object>> deleteQuestion(String questionId, String token) {

        return deleteQuestionService.deleteQuetion(questionId, token);
    }
    //删除我的回答
    @GetMapping("/deleteAnswer")
    public Result<Map<String, Object>> deleteAnswer(String answerId, String token) {

        return deleteAnswerService.deleteAnswer(answerId, token);
    }
}
