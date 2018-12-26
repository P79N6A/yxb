package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.DeleteCollectionMapper;
import com.yxbkj.yxb.system.service.DeleteCollectionService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class DeleteCollectionServiceImpl extends ServiceImpl<DeleteCollectionMapper, Collection> implements DeleteCollectionService {
    @Autowired
    private DeleteCollectionMapper deleteCollectionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    public Result<Map<String, Object>> deleteCollection(String beCollectedId, String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<Collection> likeEntityWrapper = new EntityWrapper<>();
        likeEntityWrapper.eq("be_collected_id", beCollectedId);
        likeEntityWrapper.eq("member_id", memberId);

        Integer delete = deleteCollectionMapper.delete(likeEntityWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("delete", delete);
        return new Result<>(Code.SUCCESS, "删除成功!", map, Code.IS_ALERT_NO);
    }
}
