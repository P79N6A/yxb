package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.AnswerMapper;
import com.yxbkj.yxb.system.mapper.DeleteAnswerMapper;
import com.yxbkj.yxb.system.mapper.DeleteQuestionMapper;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.service.DeleteAnswerService;
import com.yxbkj.yxb.system.service.DeleteQuestionService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeleteQuestionServiceImpl extends ServiceImpl<DeleteQuestionMapper, Question> implements DeleteQuestionService {
    @Autowired
    private DeleteQuestionMapper deleteQuestionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Result<Map<String, Object>> deleteQuetion(String questionId, String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        Answer answer = new Answer();
        Question question = new Question();
        List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("question_id", questionId));
        for (int i = 0; i < answers.size(); i++) {
            String id1 = answers.get(i).getId();
            answer.setId(id1);
            answer.setValidity("10000002");
            Integer updateAnswer = answerMapper.updateById(answer);
        }
        question.setValidity("10000002");
        Integer updateQuestion = deleteQuestionMapper.update(question, new EntityWrapper<Question>().eq("question_id", questionId).eq("creator_id", memberId));
        Map<String, Object> map = new HashMap<>();
        map.put("delete", updateQuestion);
        return new Result<>(Code.SUCCESS, "删除成功!", map, Code.IS_ALERT_NO);
    }
}
