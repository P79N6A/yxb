package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.service.LikeService;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class InsertLikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public List<Like> selectLike (String beLikedId, String memberId, String likeType){
        List<Like> beLikedId1 = likeMapper.selectList(
                new EntityWrapper<Like>()
                .eq("be_liked_id", beLikedId)
                .eq("like_type", likeType)
                .eq("member_id", memberId));
        return beLikedId1;
    }

    @Override
    public Result<Map<String, Object>> insertLike(String beLikedId,String token, String likeType) {
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)) {
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        List<Like> likes = selectLike(beLikedId, memberId, likeType);
        if (likes.size() > 0){
            return new Result<>(Code.FAIL, "已点过赞，无法点赞!", null, Code.IS_ALERT_YES);
        }
        Like like = new Like();
        like.setId(StringUtil.getUuid());
        like.setLikeId("LIK" + StringUtil.getCurrentDateStr());
        like.setBeLikedId(beLikedId);
        like.setMemberId(memberId);
        like.setLikeType(likeType);
        like.setLikeTime(DateUtils.getSysDate());
        like.setValidity("10000001");
        Integer insert1 = likeMapper.insert(like);
        Map<String, Object> map = new HashMap<>();
        map.put("insert", insert1);
        return new Result<>(Code.SUCCESS, "点赞成功", map, Code.IS_ALERT_NO);
    }
}
