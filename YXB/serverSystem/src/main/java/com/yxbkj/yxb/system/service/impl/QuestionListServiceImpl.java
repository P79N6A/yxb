package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.*;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.QuestionListService;
import com.yxbkj.yxb.system.service.QuestionService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionListServiceImpl extends ServiceImpl<QuestionListMapper, Question> implements QuestionListService {

    @Autowired
    private QuestionListMapper questionListMapper;

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public List<Answer> answer(String questionId) {
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        List<Answer> answer = answerMapper.selectList(new EntityWrapper<Answer>().eq("question_id",questionId).eq("validity", 10000001).orderBy("sort desc, creator_time DESC "));
        for (int s = 0; s < answer.size(); s++) {
            String creatorId = answer.get(s).getCreatorId();
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setMemberId(creatorId);
            memberInfo.setValidity("10000001");
            MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
            if (memberInfo1 != null){
                answer.get(s).setHeadimg(systemImageUrl+memberInfo1.getHeadimg());
            }else {
                answer.get(s).setHeadimg(null);
            }
        }
        return answer;
    }
    public Integer count(String questionId) {
        Integer answerCount = questionListMapper.questionCount(questionId);
        return answerCount;
    }

    @Override
    public Result<List<Map<String, Object>>> questionList(String token, Integer limit, Integer offset) {
        limit = (limit - 1) * offset;
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        List<Question> questionlsit = questionListMapper.questionList(limit, offset);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < questionlsit.size(); i++) {
            if(questionlsit.get(i).getQuestionHeadimg() != null && !questionlsit.get(i).getQuestionHeadimg().equals("")){
                questionlsit.get(i).setQuestionHeadimg(systemImageUrl + questionlsit.get(i).getQuestionHeadimg());
            }else {
                String creatorId = questionlsit.get(i).getCreatorId();
                List<StaffInfo> staffInfoHeadImg = staffInfoMapper.selectList(new EntityWrapper<StaffInfo>().eq("staff_id", creatorId));
                if (staffInfoHeadImg.size() > 0){
                    for (int s = 0; s < staffInfoHeadImg.size(); s++) {
                            if (!StringUtil.isEmpty(staffInfoHeadImg.get(s).getHeadImg())){
                                questionlsit.get(i).setQuestionHeadimg(systemImageUrl + staffInfoHeadImg.get(s).getHeadImg());
                                if (!StringUtil.isEmpty(staffInfoHeadImg.get(s).getNickName())){
                                    questionlsit.get(i).setCreator(staffInfoHeadImg.get(s).getNickName());
                                }else {
                                    questionlsit.get(i).setCreator(staffInfoHeadImg.get(s).getStaffName());
                                }
                            }else {
                                questionlsit.get(i).setQuestionHeadimg(staffInfoHeadImg.get(s).getHeadImg());
                            }
                        }
                    }
                }


            String questionId = questionlsit.get(i).getQuestionId();
            List<Answer> answer = answer(questionId);
            for (int s = 0; s < answer.size(); s++) {
                String answerId = answer.get(s).getAnswerId();
                Integer likeNum = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", answerId).eq("validity", 10000001));
                answer.get(s).setLikeNum(likeNum);

                answer.get(s).setCheckLike(false);
                if (!StringUtil.isEmpty(memberId)){
                    Like like = new Like();
                    like.setMemberId(memberId);
                    like.setBeLikedId(answerId);
                    like.setLikeType("10000683");
                    Like like1 = likeMapper.selectOne(like);
                    if (like1 != null && !"".equals(like1)){
                        answer.get(s).setCheckLike(true);
                    }
                }
            }
            Integer count = count(questionId);
            questionlsit.get(i).setTotal(count);
            Map<String, Object> map = new HashMap<>();
            map.put("question", questionlsit.get(i));
            map.put("answer", answer);
            maps.add( map);
        }

        return new Result<List<Map<String, Object>>>(Code.SUCCESS, "查询成功!", maps, Code.IS_ALERT_NO);
    }
}