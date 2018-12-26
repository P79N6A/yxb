package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.InsertAnswerMapper;
import com.yxbkj.yxb.system.mapper.InsertQuestionMapper;
import com.yxbkj.yxb.system.mapper.MemberInfoMapper;
import com.yxbkj.yxb.system.mapper.QuestionMapper;
import com.yxbkj.yxb.system.service.InsertAswerService;
import com.yxbkj.yxb.system.service.InsertQuestionService;
import com.yxbkj.yxb.system.websocket.WebSocket;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.sensitive.SensitiveWord;
import com.yxbkj.yxb.util.umeng.UmengUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;


@Service
public class InsertAnswerServiceImpl extends ServiceImpl<InsertAnswerMapper, Answer> implements InsertAswerService {

    @Autowired
    private InsertAnswerMapper insertAnswerMapper;
    @Autowired
    private MemberInfoMapper memberMapper;
    @Autowired
    private InsertQuestionMapper insertQuestionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public MemberInfo selectMember(String memberId){
        MemberInfo memberInfo1 = new MemberInfo();
        memberInfo1.setMemberId(memberId);
        MemberInfo memberInfo2 = memberMapper.selectOne(memberInfo1);
        return memberInfo2;
    }
    @Override
    public Result<Map<String, Object>> insertAswer(String questionId, String answerContent,String token) {
        //用户数据
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        long startNumer = System.currentTimeMillis();
        SensitiveWord sw = new SensitiveWord("CensorWords.txt");
        sw.InitializationWork();
        sw.filterInfo(answerContent);
        long endNumber = System.currentTimeMillis();
        HashMap<String, Object> map1 = new HashMap<>();
        if (sw.sensitiveWordList.size() > 0){
            map1.put("包含敏感字", sw.sensitiveWordList);
            return new Result<>(Code.FAIL, "包含敏感字", map1, Code.IS_ALERT_NO);
        }
        MemberInfo memberInfo = selectMember(memberId);
        String memberName = memberInfo.getNickname();
        Answer answer = new Answer();
        answer.setCreator(memberName);
        answer.setCreatorId(memberId);
        answer.setCreatorTime(DateUtils.getSysDate());
        answer.setId(StringUtil.getUuid());
        answer.setQuestionId(questionId);
        answer.setAnswerContent(answerContent);
        answer.setValidity("10000001");
        answer.setAnswerId("ANS" + StringUtil.getCurrentDateStr());
        Integer insert = insertAnswerMapper.insert(answer);
        Map<String, Object> map = new HashMap<>();
        map.put("insert", insert);

        //推送消息
        Question question = new Question();
        question.setQuestionId(questionId);
        Question question1 = insertQuestionMapper.selectOne(question);
        String creatorId = question1.getCreatorId();
        Map<String, String> maps = new HashMap<>();
        maps.put("type", "1");
        maps.put("questionId", questionId);
        UmengUtils.sendAndroidMessage(creatorId, "你有新的消息", "你的提问有人回答了", maps);
        WebSocket.sendHtmlMessage(creatorId, "你有新的消息", "你的提问有人回答了", maps);

        return new Result<>(Code.SUCCESS, "回答成功", map, Code.IS_ALERT_NO);
    }
}
