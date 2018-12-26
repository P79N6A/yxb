package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.InsertQuestionMapper;
import com.yxbkj.yxb.system.mapper.MemberInfoMapper;
import com.yxbkj.yxb.system.service.InsertQuestionService;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.sensitive.SensitiveWord;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;


@Service
public class InsertQuestionServiceImpl extends ServiceImpl<InsertQuestionMapper, Question> implements InsertQuestionService {

    @Autowired
    private InsertQuestionMapper insertQuestionMapper;
    @Autowired
    private MemberInfoMapper memberMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public MemberInfo selectMember(String memberId){
        MemberInfo memberInfo1 = new MemberInfo();
        memberInfo1.setMemberId(memberId);
        MemberInfo memberInfo2 = memberMapper.selectOne(memberInfo1);
        return memberInfo2;
    }

    @Override
    public Result<Map<String, Object>> insertQuestion(String questionContent, @ApiParam(value = "令牌",required = true)@RequestParam(value = "token")String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        long startNumer = System.currentTimeMillis();
        SensitiveWord sw = new SensitiveWord("CensorWords.txt");
        sw.InitializationWork();
        sw.filterInfo(questionContent);
        long endNumber = System.currentTimeMillis();
        HashMap<String, Object> map1 = new HashMap<>();
        if (sw.sensitiveWordList.size() > 0){
            map1.put("包含敏感字", sw.sensitiveWordList);
            return new Result<>(Code.FAIL, "包含敏感字", map1, Code.IS_ALERT_NO);
        }
        //用户数据
        MemberInfo memberInfo = selectMember(memberId);
        String memberName = memberInfo.getNickname();
        Question question = new Question();
        question.setCreator(memberName);
        question.setCreatorId(memberId);
        question.setCreatorTime(DateUtils.getSysDate());
        question.setId(StringUtil.getUuid());
        question.setQuestionId("QUE" + StringUtil.getCurrentDateStr());
        question.setQuestionContent(questionContent);
        question.setValidity("10000001");
        Integer insert = insertQuestionMapper.insert(question);
        Map<String, Object> map = new HashMap<>();
        map.put("insert", insert);
        return new Result<>(Code.SUCCESS, "提问成功", map, Code.IS_ALERT_NO);
    }
}
