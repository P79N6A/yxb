package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.member.mapper.MemberPropertyMapper;
import com.yxbkj.yxb.member.service.MemberPropertyService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 用户资产信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@Service
public class MemberPropertyServiceImpl extends ServiceImpl<MemberPropertyMapper, MemberProperty> implements MemberPropertyService {

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    public Result<MemberProperty> getMemberProperty(String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        EntityWrapper<MemberProperty> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        MemberProperty property = selectOne(wrapper);
//        if(property==null){
//             property = new MemberProperty();
//            property.setMemberId(memberId);
//            property.setId(StringUtil.getUuid());
//            property.setPropertyAmount(BigDecimal.ZERO);
//            property.setFrozenAmount(BigDecimal.ZERO);
//            property.setAvailableAmount(BigDecimal.ZERO);
//            property.setSubmittedAmount(BigDecimal.ZERO);
//            property.setEbean(0);
//            insert(property);
//        }
        return new Result<>(Code.SUCCESS, "获取数据成功!", property, Code.IS_ALERT_NO);
    }
}
