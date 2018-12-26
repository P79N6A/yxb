package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.app.Newss;
import com.yxbkj.yxb.entity.app.Sensitive;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.SensitiveService;
import com.yxbkj.yxb.util.sensitive.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitive> implements SensitiveService {

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public Result<Map<String, Object>> str(String content) {
        long startNumer = System.currentTimeMillis();
        SensitiveWord sw = new SensitiveWord("CensorWords.txt");
        sw.InitializationWork();
        content = sw.filterInfo(content);
        long endNumber = System.currentTimeMillis();
        HashMap<String, Object> map = new HashMap<>();
        if (sw.sensitiveWordList.size() > 0){
            map.put("包含敏感字", sw.sensitiveWordList);
            return new Result<>(Code.SUCCESS, "包含敏感字", map, Code.IS_ALERT_NO);
        }
        return new Result<>(Code.SUCCESS, "允许发布", map, Code.IS_ALERT_NO);
    }
}
