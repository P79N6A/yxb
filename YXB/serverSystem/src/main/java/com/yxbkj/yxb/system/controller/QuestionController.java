package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")

public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private MyQuestionService myQuestionService;
    @Autowired
    private QuestionListService questionListService;
    @Autowired
    private MyAnswerService myAnswerService;
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private CountReadService countReadService;
//    @GetMapping("/question")
//    public Result<Map<String, Object>> question() {
//
//        return questionService.selectQuestion();
//    }
    @GetMapping("/page")
    public Result<List<Map<String, Object>>> page(Integer limit, Integer offset){
        return questionService.questionPage(limit, offset);
    }
    @GetMapping("/myquestion")
    public Result<List<Map<String, Object>>> myquestion(String token, Integer limit, Integer offset){
        return myQuestionService.myQuestion(token, limit, offset);
    }
    @GetMapping("/questionlist")
    public Result<List<Map<String, Object>>> questionlist(String token, Integer limit, Integer offset){
        return questionListService.questionList(token, limit, offset);
    }
    @GetMapping("/myanswer")
    public Result<Map<String, Object>> myanswer(String token, Integer limit, Integer offset){
        return myAnswerService.myAnswer(token, limit, offset);
    }
    @GetMapping("/questionDetails")
    public Result<List<Map<String, Object>>> questionDetails(String questionId, String token, Integer limit, Integer offset){
        return detailsService.questionDetails(questionId, token, limit, offset);
    }
    @GetMapping("/getAnswerIsRead")
    public Result<Map<String, Object>> getAnswerIsRead(String token){
        return countReadService.countRead(token);
    }
}
