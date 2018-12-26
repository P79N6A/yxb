package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.PageQuestion;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.AnswerMapper;
import com.yxbkj.yxb.system.mapper.QuestionMapper;
import com.yxbkj.yxb.system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, PageQuestion> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    public List<Answer> answer(String questionId) {
        List<Answer> answer = answerMapper.selectList(new EntityWrapper<Answer>().eq("question_id",questionId));
        return answer;
    }
    @Override
    public Result<List<Map<String, Object>>> questionPage(Integer limit, Integer offset) {
        Result<List<Map<String, Object>>> result = null;

        Page<PageQuestion> page = new Page<>(limit, offset);
        Page<PageQuestion> page1 = selectPage(page, new EntityWrapper<PageQuestion>().eq("validity",10000001).orderBy("sort"));
        List<PageQuestion> recodes = page1.getRecords();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < recodes.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("test", recodes.get(i));
            String questionid = recodes.get(i).getQuestionId();
            List<Answer> as = answer(questionid);
            map.put("as",as);
            maps.add(map);
        }

        result = new Result<>(Code.SUCCESS, "查询成功!", maps, Code.IS_ALERT_NO);
        return result;

    }


}