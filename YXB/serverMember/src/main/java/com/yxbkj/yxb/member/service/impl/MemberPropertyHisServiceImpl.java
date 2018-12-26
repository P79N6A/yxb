package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.member.mapper.MemberPropertyHisMapper;
import com.yxbkj.yxb.member.service.MemberPropertyHisService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 会员资产信息历史表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@Service
public class MemberPropertyHisServiceImpl extends ServiceImpl<MemberPropertyHisMapper, MemberPropertyHis> implements MemberPropertyHisService {
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    public Result<Page<MemberPropertyHis>> getMemberPropertyHis(String token, String type, String inOrOut, Integer offset, Integer limit) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return  new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_NO);
        }
        Page<MemberPropertyHis> page = new Page(offset,limit);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("ebean",0);
        wrapper.where("available_amount!=0");
        wrapper.eq("member_id",memberId);
        if(!StringUtil.isEmpty(inOrOut)){
            if("0".equals(inOrOut)){
                wrapper.gt("availableAmount",0);
            }
            if("1".equals(inOrOut)){
                wrapper.lt("availableAmount",0);
            }
        }
        page.setOrderByField("modifier_time"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        page.setRecords(selectPage(page,wrapper).getRecords());
        return new Result<Page<MemberPropertyHis>>(Code.SUCCESS, "获取数据成功!", page, Code.IS_ALERT_NO);
    }
}
