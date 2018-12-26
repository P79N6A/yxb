package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.Prize;
import com.yxbkj.yxb.entity.system.PrizeRecord;
import com.yxbkj.yxb.entity.system.ReceivingInfo;
import com.yxbkj.yxb.entity.vo.ReceivingInfoVo;
import com.yxbkj.yxb.system.mapper.ReceivingInfoMapper;
import com.yxbkj.yxb.system.service.PrizeRecordService;
import com.yxbkj.yxb.system.service.PrizeService;
import com.yxbkj.yxb.system.service.ReceivingInfoService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收货信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-10-30
 */
@Service
public class ReceivingInfoServiceImpl extends ServiceImpl<ReceivingInfoMapper, ReceivingInfo> implements ReceivingInfoService {

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Autowired
    private PrizeRecordService prizeRecordService;
    @Autowired
    private PrizeService prizeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> addReceivingInfo(ReceivingInfoVo entity) {
        String memberId = redisTemplateUtils.getStringValue(entity.getToken());
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        if(entity.getContactsPhone()==null || !ValidateUtils.isPhone(entity.getContactsPhone())){
            return new Result<>(Code.FAIL, "添加失败,手机号码错误!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(entity.getContacts())){
            return new Result<>(Code.FAIL, "添加失败,联系人不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(entity.getAddress())){
            return new Result<>(Code.FAIL, "添加失败,地址不能为空!", null, Code.IS_ALERT_YES);
        }
        ReceivingInfo receivingInfo = new ReceivingInfo();
        receivingInfo.setId(StringUtil.getUuid());
        receivingInfo.setMemberId(memberId);
        receivingInfo.setAddress(entity.getAddress());
        receivingInfo.setContacts(entity.getContacts());
        receivingInfo.setContactsPhone(Long.parseLong(entity.getContactsPhone()));
        receivingInfo.setRemark(entity.getRemark());
        receivingInfo.setPrizeRecordId(entity.getPrizeRecordId());
        boolean insert = insert(receivingInfo);
        if(insert){
            Map<String, Object> map = new HashMap<>();
            map.put("receivingInfo",receivingInfo);
            return new Result<>(Code.SUCCESS, "添加成功!", null, Code.IS_ALERT_NO);
        }else{
            return new Result<>(Code.FAIL, "添加失败，未知异常", null, Code.IS_ALERT_YES);
        }
    }

    @Override
    public Result<Page<PrizeRecord>> getPrizeInfoNews(String token, String activityNo,Integer offset, Integer limit) {
        if (offset == null) offset = 1;
        if (limit == null) limit = 10;
        if(StringUtil.isEmpty(activityNo)){
            return new Result<>(Code.FAIL, "查询失败!活动编号不能为空!", null, Code.IS_ALERT_YES);
        }
        String memberId = redisTemplateUtils.getStringValue(token);
        Page<PrizeRecord> page = new Page(offset, limit);
        EntityWrapper<PrizeRecord> wrapper = new EntityWrapper();
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("activity_no", activityNo);
        if(!StringUtil.isEmpty(memberId)){
            wrapper.eq("member_id", memberId);
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("index",(offset-1)*limit);
            map.put("size",limit);
            map.put("activityNo",activityNo);
            List<PrizeRecord> prizeRecordByPage = prizeRecordService.getPrizeRecordByPage(map);
            rebuildPrizeRecord(prizeRecordByPage);
            page.setRecords(prizeRecordByPage);
            return new Result<>(Code.SUCCESS, "获取数据成功!", page, Code.IS_ALERT_NO);
        }
        page.setOrderByField("creator_time"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        page = prizeRecordService.selectPage(page, wrapper);
        List<PrizeRecord> records = page.getRecords();
        rebuildPrizeRecord(records);
        page.setRecords(records);
        return new Result<>(Code.SUCCESS, "获取数据成功!", page, Code.IS_ALERT_NO);
    }


    private void rebuildPrizeRecord(List<PrizeRecord> prizeRecordByPage){
        for(PrizeRecord bean : prizeRecordByPage){
            String id = bean.getId();
            EntityWrapper<ReceivingInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("prize_record_id",id);
            ReceivingInfo receivingInfo = selectOne(wrapper);
            EntityWrapper<Prize> wrapper_prize = new EntityWrapper<>();
            wrapper_prize.eq("prize_id",bean.getPrizeId());
            Prize prize = prizeService.selectOne(wrapper_prize);
            if(prize!=null){
                bean.setPrizeType(prize.getPrizeType());
            }
            if(receivingInfo!=null){
                bean.setContacts(receivingInfo.getContacts());
                bean.setContactsPhone(receivingInfo.getContactsPhone());
                bean.setAddress(receivingInfo.getAddress());
            }
        }
    }
}
