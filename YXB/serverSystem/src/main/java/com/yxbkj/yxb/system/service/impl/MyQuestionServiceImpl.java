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
import com.yxbkj.yxb.system.service.MyQuestionService;
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
public class MyQuestionServiceImpl extends ServiceImpl<MyQuestionMapper, Question> implements MyQuestionService {
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
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Override
    public Result<List<Map<String, Object>>> myQuestion(String token, Integer limit, Integer offset) {
//        limit = (limit - 1 ) * offset;
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if(StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        List<Map<String, Object>> maps = new ArrayList<>();
        MemberInfo memberInfo = new MemberInfo();
        StaffInfo staffInfo = new StaffInfo();
        if(!StringUtil.isEmpty(memberId)){
            Page<Question> page = new Page<>(limit, offset);
            Page<Question> question = selectPage(page, new EntityWrapper<Question>().eq("creator_id",memberId).eq("validity", 10000001).orderBy("creator_time desc"));
            List<Question> records = question.getRecords();
            for (int i = 0; i < records.size(); i++) {
                String questionId1 = records.get(i).getQuestionId();
                Integer total = answerMapper.selectCount(new EntityWrapper<Answer>().eq("question_id", questionId1).eq("validity", 10000001));
                Integer isRead = answerMapper.selectCount(new EntityWrapper<Answer>().eq("question_id", questionId1).eq("validity", 10000001).eq("is_read", 10000562));

                records.get(i).setTotal(total);
                records.get(i).setIsRead(isRead);
                List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("question_id", questionId1).eq("validity", 10000001).orderBy("sort desc"));
                for (int s = 0; s < answers.size(); s++) {
                    String answerId = answers.get(s).getAnswerId();
                    String creatorId = answers.get(s).getCreatorId();
                    memberInfo.setMemberId(creatorId);
                    MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
                    Integer likeNum = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", answerId).eq("validity", 10000001));
                    answers.get(s).setLikeNum(likeNum);

                    answers.get(s).setCheckLike(false);
                    if (!StringUtil.isEmpty(memberId)){
                        Like like = new Like();
                        like.setMemberId(memberId);
                        like.setBeLikedId(answerId);
                        like.setLikeType("10000683");
                        Like like1 = likeMapper.selectOne(like);
                        if (like1 != null && !"".equals(like1)){
                            answers.get(s).setCheckLike(true);
                        }
                    }
                    if(memberInfo1 != null ){
                        if(memberInfo1.getHeadimg() != null && !memberInfo1.getHeadimg().equals("")){
                            answers.get(s).setHeadimg(systemImageUrl + memberInfo1.getHeadimg());
                        }else {
                            answers.get(s).setHeadimg(memberInfo1.getHeadimg());
                        }
                    }else {
                        staffInfo.setStaffId(creatorId);
                        StaffInfo staffInfo1 = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfo1.getHeadImg())){
                            answers.get(s).setHeadimg(staffInfo1.getHeadImg());
                        }
                        if (!StringUtil.isEmpty(staffInfo1.getNickName())){
                            answers.get(s).setNickName(staffInfo1.getNickName());
                        }else {
                            answers.get(s).setNickName(staffInfo1.getStaffName());
                        }
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("question", records.get(i));
                map.put("answer", answers);
                maps.add( map);
            }
            return new Result<List<Map<String, Object>>>(Code.SUCCESS, "查询成功!", maps, Code.IS_ALERT_NO);
        }
        return new Result<>(Code.SUCCESS, "该用户ID暂无数据!", null, Code.IS_ALERT_NO);
    }
}
