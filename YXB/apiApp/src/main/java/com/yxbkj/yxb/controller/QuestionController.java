package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "QuestionController",description = "获取问题答案")
@RestController
@RequestMapping("/question")

public class QuestionController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    @ApiOperation(value = "获取问题答案",notes = "获取问题答案")
    @GetMapping("/page")
    public Result<List<Map<String, Object>>> page(Integer limit, Integer offset){
        return serverSystemFeignClient.question(limit, offset);
    }
    @ApiOperation(value = "获取提问",notes = "获取提问")
    @GetMapping("/myquestion")
    public Result<List<Map<String, Object>>> myquestion(String token, Integer limit, Integer offset){
        return serverSystemFeignClient.myquestion(token, limit, offset);
    }
    @ApiOperation(value = "获取问答列表",notes = "获取问答列表")
    @GetMapping("/questionlist")
    public Result<List<Map<String, Object>>> questionlist(String token, Integer limit, Integer offset){
        return serverSystemFeignClient.questionList(token, limit, offset);
    }
    @ApiOperation(value = "获取我的答案列表",notes = "获取我的答案列表")
    @GetMapping("/myanswer")
    public Result<Map<String, Object>> myanswer(String token, Integer limit, Integer offset){
        return serverSystemFeignClient.myanswer(token, limit, offset);
    }
    @ApiOperation(value = "获取问答详情",notes = "获取问答详情")
    @GetMapping("/questionDetails")
    public Result<List<Map<String, Object>>> questionDetails(String questionId, String token, Integer limit, Integer offset){
        return serverSystemFeignClient.questionDetails(questionId, token, limit, offset);
    }
    @ApiOperation(value = "获取未读回答数量",notes = "获取未读回答数量")
    @GetMapping("/getAnswerIsRead")
    public Result<Map<String, Object>> getAnswerIsRead(String token){
        return serverSystemFeignClient.getAnswerIsRead(token);
    }
}
