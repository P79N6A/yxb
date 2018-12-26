package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.InsertComment;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.InsertCommentMapper;
import com.yxbkj.yxb.system.service.InsertCommentService;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.sensitive.SensitiveWord;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class InsertCommentServiceImpl extends ServiceImpl<InsertCommentMapper, InsertComment> implements InsertCommentService {
    @Autowired
    private InsertCommentMapper insertCommentMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    public Result<Map<String, Object>> insertcomment(String beCommentedId,String token,
                                                     String content, String commentType) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        System.currentTimeMillis();
        SensitiveWord sw = new SensitiveWord("CensorWords.txt");
        sw.InitializationWork();
        sw.filterInfo(content);
        System.currentTimeMillis();
        HashMap<String, Object> map1 = new HashMap<>();
        if (sw.sensitiveWordList.size() > 0){
            map1.put("包含敏感字", sw.sensitiveWordList);
            return new Result<>(Code.FAIL, "包含敏感字", map1, Code.IS_ALERT_NO);
        }
        InsertComment insertComment = new InsertComment();
        insertComment.setId(StringUtil.getUuid());
        insertComment.setCommentId("COM" + StringUtil.getCurrentDateStr());
        insertComment.setBeCommentedId(beCommentedId);
        insertComment.setMemberId(memberId);
        insertComment.setContent(content);
        insertComment.setCommentTime(DateUtils.getSysDate());
        insertComment.setCommentType(commentType);
        insertComment.setValidity("10000001");
        Integer insert1 = insertCommentMapper.insert(insertComment);
        Map<String, Object> map = new HashMap<>();
        map.put("insert", insert1);
        return new Result<>(Code.SUCCESS, "评论成功", map, Code.IS_ALERT_NO);
    }
}
