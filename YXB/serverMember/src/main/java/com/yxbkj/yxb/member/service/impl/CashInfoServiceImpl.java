package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.*;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.vo.CashParam;
import com.yxbkj.yxb.member.mapper.CashAuditMapper;
import com.yxbkj.yxb.member.mapper.CashInfoMapper;
import com.yxbkj.yxb.member.service.*;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-27
 */
@Service
public class CashInfoServiceImpl extends ServiceImpl<CashInfoMapper, CashInfo> implements CashInfoService {

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberPropertyService memberPropertyService;
    @Autowired
    private MemberPropertyHisService memberPropertyHisService;
    @Autowired
    private CashAuditMapper cashAuditMapper;
    @Autowired
    private CashInfoMapper cashInfoMapper;
    @Autowired
    private ConfigService configService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<CashInfo> applyCashInfo(CashParam param) {
        String memberId = redisTemplateUtils.getStringValue(param.getToken());
        if(memberId==null){
            return new Result<CashInfo>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> memberWraper = new EntityWrapper<>();
        memberWraper.eq("member_id",memberId);
        memberWraper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = memberInfoService.selectOne(memberWraper);
        if(memberInfo==null){
            return new Result<CashInfo>(Code.FAIL,"会员信息不存在!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getBankName())){
            return new Result<CashInfo>(Code.FAIL,"所属银行不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getOrderSource())){
            return new Result<CashInfo>(Code.FAIL,"来源不能为空!",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberProperty> propertyWraper = new EntityWrapper<>();
        propertyWraper.eq("member_id",memberId);
        MemberProperty memberProperty = memberPropertyService.selectOne(propertyWraper);
        if(memberProperty==null){
            return new Result<CashInfo>(Code.FAIL,"非法异常，请联系管理员!",null,Code.IS_ALERT_YES);
        }
        String cashAmount = param.getCashAmount();
        BigDecimal cash = BigDecimal.ZERO;
        try{
            cash = new BigDecimal(cashAmount);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<CashInfo>(Code.FAIL,"金额格式错误,转换异常!",null,Code.IS_ALERT_YES);
        }
        if(cash.compareTo(BigDecimal.ZERO)<=0){
            return new Result<CashInfo>(Code.FAIL,"提现金额必须大于0!",null,Code.IS_ALERT_YES);
        }
        //会员可用金额
        BigDecimal availableAmount = memberProperty.getAvailableAmount();
        if(cash.compareTo(availableAmount)>0){
            return new Result<CashInfo>(Code.FAIL,"提现金额不能大于可用金额!",null,Code.IS_ALERT_YES);
        }
        String uuid = StringUtil.getUuid();
        // 添加资产信息流水
        MemberPropertyHis propertyHis = new MemberPropertyHis();
        propertyHis.setId(uuid);
        propertyHis.setPropertyAmount(memberProperty.getPropertyAmount());//总收入
        propertyHis.setFrozenAmount(memberProperty.getFrozenAmount());//冻结金额
        propertyHis.setAvailableAmount(memberProperty.getAvailableAmount());//可用金额  为负数
        propertyHis.setSubmittedAmount(memberProperty.getSubmittedAmount());//已提金额
        propertyHis.setEbean(memberProperty.getEbean());
        propertyHis.setRemark("申请提现");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisService.insert(propertyHis);
        //更新资产信息
        memberProperty.setAvailableAmount(memberProperty.getAvailableAmount().subtract(cash));//重置可用金额   减去提现金额
        //memberProperty.setFrozenAmount(memberProperty.getFrozenAmount().add(cash));//重置冻结金额   +  提现金额
        memberPropertyService.updateById(memberProperty);
        //添加提现记录
        CashInfo cashInfo= new CashInfo();
        cashInfo.setId(uuid);
        cashInfo.setCreatorIp(HttpKit.getClientIP());
        cashInfo.setCreatorTime(YxbConstants.sysDate());
        cashInfo.setCashAmount(new BigDecimal(param.getCashAmount()));
        cashInfo.setMemberId(memberId);
        cashInfo.setToAccountAmount(BigDecimal.ZERO);
        cashInfo.setCashFee(BigDecimal.ZERO);
        cashInfo.setBankCard(param.getBankCard());
        cashInfo.setBankName(param.getBankName());
        cashInfo.setBankBranch(param.getDepositBankName());
        cashInfo.setOrderSource(param.getOrderSource());
        cashInfo.setCashStatus(YxbConstants.CASH_INITED);
        cashInfo.setMemberName(memberInfo.getMemberName());
        cashInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        insert(cashInfo);
        //添加提现审核信息
        CashAudit audit = new CashAudit();
        audit.setId(StringUtil.getUuid());
        audit.setCashInfoId(uuid);
        String defaultAuditUser = configService.getConfigValue("defaultAuditUser");
        audit.setAuditUser(defaultAuditUser);
        cashAuditMapper.insert(audit);
        return new Result<CashInfo>(Code.SUCCESS,"提现申请成功!",cashInfo,Code.IS_ALERT_NO);
    }


    @Override
    public  Result<Page<CashInfo>> getCashInfo(Integer offset, Integer limit, String token,String type) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Page<CashInfo>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        Page<CashInfo> page = new Page(offset,limit);
        EntityWrapper<CashInfo> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("member_id",memberId);
        if(!StringUtil.isEmpty(type)){
            wrapper.eq("cash_status",type);
        }
        page.setOrderByField("creator_time"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        Page<CashInfo> cashInfoPage = selectPage(page, wrapper);
        return new Result<Page<CashInfo>>(Code.SUCCESS,"获取成功!",cashInfoPage,Code.IS_ALERT_NO);

    }

    @Override
    public Result<List<Map<String, Object>>> getCashInfoNew(Integer offset, Integer limit, String token, String type) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<List<Map<String, Object>>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();

        map.put("memberId",memberId);
        map.put("limit",limit);
        map.put("offset",((offset-1)*limit));

        List<Map<String, Object>> cashInfoNew = cashInfoMapper.getCashInfoNew(map);
        return new Result<List<Map<String, Object>>>(Code.SUCCESS,"数据查询成功!",cashInfoNew,Code.IS_ALERT_NO);

    }
}
