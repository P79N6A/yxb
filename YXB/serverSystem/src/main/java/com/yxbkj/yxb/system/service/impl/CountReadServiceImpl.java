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
import com.yxbkj.yxb.system.service.CountReadService;
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
public class CountReadServiceImpl extends ServiceImpl<MyAnswerMapper, Answer> implements CountReadService {
    @Autowired
    private QuestionListMapper questionListMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;


    @Override
    public Result<Map<String, Object>> countRead(String token) {
        String memberid = redisTemplateUtils.getStringValue(token);
        if (StringUtil.isEmpty(memberid)) {
            return new Result<>(Code.SUCCESS, "token为空", null, Code.IS_ALERT_NO);
        }
//        String memberid = token;
        List<Question> questionsList = questionListMapper.selectList(new EntityWrapper<Question>().eq("creator_id", memberid).eq("validity", 10000001));
        Map<String, Object> map = new HashMap<>();
        Integer s = 0;
        for (int i = 0; i < questionsList.size(); i++) {
            String questionId = questionsList.get(i).getQuestionId();
            Integer integer = answerMapper.selectCount(new EntityWrapper<Answer>().eq("is_read", 10000562).eq("question_id", questionId).eq("validity", 10000001));
            s += integer;
        }
        map.put("isRead", s);
        return new Result<>(Code.SUCCESS, "查询成功", map, Code.IS_ALERT_NO);
    }
}
