package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Answer;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.DeleteAnswerMapper;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.service.DeleteAnswerService;
import com.yxbkj.yxb.system.service.DeleteLikeService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class DeleteAnswerServiceImpl extends ServiceImpl<DeleteAnswerMapper, Answer> implements DeleteAnswerService {
    @Autowired
    private DeleteAnswerMapper deleteAnswerMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    public Result<Map<String, Object>> deleteAnswer(String answerId, String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        Answer answer = new Answer();
        EntityWrapper<Answer> likeEntityWrapper = new EntityWrapper<>();
        likeEntityWrapper.eq("answer_id", answerId);
        likeEntityWrapper.eq("creator_id", memberId);
        answer.setValidity("10000002");
        Integer delete = deleteAnswerMapper.update(answer,likeEntityWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("delete", delete);
        return new Result<>(Code.SUCCESS, "删除成功!", map, Code.IS_ALERT_NO);
    }
}