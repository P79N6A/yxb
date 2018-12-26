package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.app.MyAnswer;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.AnswerMapper;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.mapper.MyAnswerMapper;
import com.yxbkj.yxb.system.mapper.QuestionListMapper;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.MyAnswerService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyAnswerServiceImpl extends ServiceImpl<MyAnswerMapper, Answer> implements MyAnswerService {
    @Autowired
    private MyAnswerMapper myAnswerMapper;
    @Autowired
    private QuestionListMapper questionListMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public List<Question> question(String questionId) {
        List<Question> question = questionListMapper.selectList(new EntityWrapper<Question>().eq("question_id",questionId));
        return question;
    }
    public int answerCount(String questionId, String memberId){
        Integer answerCount = answerMapper.selectCount(new EntityWrapper<Answer>().eq("question_id", questionId).eq("creator_id", memberId));

        return answerCount;
    }
    @Override
    public Result<Map<String, Object>> myAnswer(String token, Integer limit, Integer offset) {
//        limit = (limit - 1) * offset;
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        Question question = new Question();
        Page<Answer> page = new Page<>(limit, offset);
        Page<Answer> page1 = selectPage(page,new EntityWrapper<Answer>().eq("creator_id", memberId).eq("validity", 10000001).orderBy("creator_time desc"));
        List<Answer> answerList = page1.getRecords();

        Integer integer = answerMapper.selectCount(new EntityWrapper<Answer>().eq("creator_id", memberId).eq("validity", 10000001));
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < answerList.size(); i++) {
            String questionId = answerList.get(i).getQuestionId();
            question.setQuestionId(questionId);
            question.setValidity("10000001");
            Question question1 = questionListMapper.selectOne(question);
            Integer answerCount = answerMapper.selectCount(new EntityWrapper<Answer>().eq("question_id", questionId).eq("validity", 10000001));
            if (question1 != null){
                answerList.get(i).setQuestionTitle(question1.getQuestionContent());
                answerList.get(i).setAnswerCount(answerCount);
            }else {
                answerList.get(i).setQuestionTitle(null);
                answerList.get(i).setAnswerCount(null);
            }
        }
        map.put("answer", answerList);
        map.put("answerNum", integer);
        return new Result<>(Code.SUCCESS, "我的回答", map, Code.IS_ALERT_NO);
    }
}
