package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.app.Question;
import com.yxbkj.yxb.entity.app.StaffInfo;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.DetailsService;
import com.yxbkj.yxb.system.service.MyQuestionService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionDetailsServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements DetailsService {
    @Autowired
    private MyQuestionMapper myQuestionMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Override
    public Result<List<Map<String, Object>>> questionDetails(String questionId, String token, Integer limit, Integer offset) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        List<Map<String, Object>> maps = new ArrayList<>();
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        MemberInfo memberInfo = new MemberInfo();
        Question questionDetails = new Question();
        questionDetails.setQuestionId(questionId);
        questionDetails.setValidity("10000001");
        Question questionOne = myQuestionMapper.selectOne(questionDetails);
        Page<Answer> page = new Page<>(limit, offset);
        Page<Answer> page1 = selectPage(page, new EntityWrapper<Answer>().eq("question_id", questionId).eq("validity", 10000001).orderBy("sort desc, creator_time desc"));
        List<Answer> answerList = page1.getRecords();
        for (int i = 0; i < answerList.size(); i++) {
            String creatorId = answerList.get(i).getCreatorId();
            String answerId = answerList.get(i).getAnswerId();
            memberInfo.setMemberId(creatorId);
            MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
            if (memberInfo1 != null){
                String headimg = memberInfo1.getHeadimg();
                String nickname = memberInfo1.getNickname();
                if (!StringUtil.isEmpty(nickname)){
                    answerList.get(i).setHeadimg(systemImageUrl + headimg);
                }else {
                    answerList.get(i).setHeadimg(headimg);
                }
                answerList.get(i).setNickName(nickname);
            }else {
                List<StaffInfo> staffInfoHeadImg = staffInfoMapper.selectList(new EntityWrapper<StaffInfo>().eq("staff_id", creatorId));
                for (int s = 0; s < staffInfoHeadImg.size(); s++) {
                    if (!StringUtil.isEmpty(staffInfoHeadImg.get(s).getHeadImg())) {
                        answerList.get(i).setHeadimg(systemImageUrl + staffInfoHeadImg.get(s).getHeadImg());
                        if (!StringUtil.isEmpty(staffInfoHeadImg.get(s).getNickName())) {
                            answerList.get(i).setCreator(staffInfoHeadImg.get(s).getNickName());
                        } else {
                            answerList.get(i).setCreator(staffInfoHeadImg.get(s).getStaffName());
                        }
                    }else {
                        if (!StringUtil.isEmpty(staffInfoHeadImg.get(s).getNickName())) {
                            answerList.get(i).setCreator(staffInfoHeadImg.get(s).getNickName());
                        } else {
                            answerList.get(i).setCreator(staffInfoHeadImg.get(s).getStaffName());
                        }
                        answerList.get(i).setHeadimg(staffInfoHeadImg.get(s).getHeadImg());
                    }
                }
            }
            Integer likeNum = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", answerId).eq("validity", 10000001));
            answerList.get(i).setLikeNum(likeNum);
            answerList.get(i).setCheckLike(false);
            if (!StringUtil.isEmpty(memberId)){
                Like like = new Like();
                like.setMemberId(memberId);
                like.setBeLikedId(answerId);
                like.setLikeType("10000683");
                Like like1 = likeMapper.selectOne(like);
                if (like1 != null && !"".equals(like1)){
                    answerList.get(i).setCheckLike(true);
                }
            }
        }
        if(questionOne != null) {
            if (memberId.equals(questionOne.getCreatorId())) {
                Answer answer = new Answer();
                answer.setIsRead("10000561");
                answerMapper.update(answer, new EntityWrapper<Answer>().eq("question_id", questionId).eq("validity", 10000001));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("question", questionOne);
        map.put("answer", answerList);
        maps.add(map);

        return new Result<List<Map<String, Object>>>(Code.SUCCESS, "查询成功!", maps, Code.IS_ALERT_NO);
    }

}
