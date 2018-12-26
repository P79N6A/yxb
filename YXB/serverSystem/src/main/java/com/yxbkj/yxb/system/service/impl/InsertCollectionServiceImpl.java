package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.CollectionMapper;
import com.yxbkj.yxb.system.mapper.InsertCollectionMapper;
import com.yxbkj.yxb.system.mapper.LikeMapper;
import com.yxbkj.yxb.system.service.CollectionService;
import com.yxbkj.yxb.system.service.InsertCollectionService;
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
public class InsertCollectionServiceImpl extends ServiceImpl<InsertCollectionMapper, Collection> implements InsertCollectionService {
    @Autowired
    private InsertCollectionMapper collectionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public List<Collection> selectCollection (String beCollectedId, String memberId, String collectionType){
        List<Collection> selectCollection = collectionMapper.selectList(
                new EntityWrapper<Collection>()
                        .eq("be_collected_id", beCollectedId)
                        .eq("collection_type", collectionType)
                        .eq("member_id", memberId));
        return selectCollection;
    }

    @Override
    public Result<Map<String, Object>> insertCollection(String beCollectedId,String token, String collectionType) {

        String memberId = redisTemplateUtils.getStringValue(token);
        if (StringUtil.isEmpty(memberId)){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        List<Collection> collections = selectCollection(beCollectedId, memberId, collectionType);
        if (collections.size() > 0){
            return new Result<>(Code.FAIL, "已被收藏，无法收藏!", null, Code.IS_ALERT_YES);
        }
        Collection collection = new Collection();
        collection.setId(StringUtil.getUuid());
        collection.setCollectionId("COL" + StringUtil.getCurrentDateStr());
        collection.setBeCollectedId(beCollectedId);
        collection.setMemberId(memberId);
        collection.setCollectionType(collectionType);
        collection.setCollectionTime(DateUtils.getSysDate());
        collection.setValidity("10000001");
        Integer insert1 = collectionMapper.insert(collection);
        Map<String, Object> map = new HashMap<>();
        map.put("insert", insert1);
        return new Result<>(Code.SUCCESS, "收藏成功", map, Code.IS_ALERT_NO);
    }
}
