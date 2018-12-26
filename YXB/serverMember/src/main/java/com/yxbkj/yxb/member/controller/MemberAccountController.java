package com.yxbkj.yxb.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.entity.member.MemberAccount;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.member.mapper.BankMapper;
import com.yxbkj.yxb.member.service.ConfigService;
import com.yxbkj.yxb.member.service.MemberAccountService;
import com.yxbkj.yxb.member.service.MemberPropertyHisService;
import com.yxbkj.yxb.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员账户信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@RestController
@RequestMapping("/memberAccount")
public class MemberAccountController {
    @Autowired
    private MemberAccountService memberAccountService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private ConfigService configService;
    @Autowired
    private BankMapper bankMapper;



    /**
     * 作者: 李明
     * 描述: 添加会员账户信息
     * 备注:
     * @param token
     * @param bankCode
     * @param depositBankName
     * @param bankCardNo
     * @param cardPreferred
     * @return
     */
    @ApiOperation(value = "添加会员账户信息",notes = "添加会员账户信息")
    @PostMapping("/saveMemberAccount")
    public Result<MemberAccount> saveMemberAccount(
             @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "银行编码",required = true)@RequestParam(value = "bankCode") String  bankCode
            ,@ApiParam(value = "开户行名称",required = true)@RequestParam(value = "depositBankName") String  depositBankName
            ,@ApiParam(value = "银行卡号",required = true)@RequestParam(value = "bankCardNo") String  bankCardNo
            ,@ApiParam(value = "首选状态",required = true)@RequestParam(value = "cardPreferred") String  cardPreferred
    ){
        String memberId = redisTemplateUtils.getStringValue(token);
        EntityWrapper<MemberAccount> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("bank_card_no",bankCardNo);
        MemberAccount memberAccountDb = memberAccountService.selectOne(wrapper);
        if(memberAccountDb!=null){
            return new Result<MemberAccount>(Code.FAIL, "银行卡号已经存在!", null, Code.IS_ALERT_YES);
        }
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setId(StringUtil.getUuid());
        memberAccount.setMemberId(memberId);
        memberAccount.setBankCode(bankCode);
        memberAccount.setDepositBankName(depositBankName);
        memberAccount.setBankCardNo(bankCardNo);
        memberAccount.setCardPreferred(cardPreferred);
        memberAccount.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        memberAccount.setCreatorTime(DateUtils.getSysDate());
        memberAccount.setCreatorIp(HttpKit.getClientIP());
        boolean flag = memberAccountService.insert(memberAccount);
        if(flag){
            return new Result<MemberAccount>(Code.SUCCESS, "保存成功!", memberAccount, Code.IS_ALERT_NO);
        }else{
            return new Result<MemberAccount>(Code.FAIL, "保存失败!", memberAccount, Code.IS_ALERT_YES);
        }
    }


    /**
     * 作者: 李明
     * 描述: 获取某会员所有账户信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取某会员所有账户信息",notes = "获取某会员所有账户信息")
    @GetMapping("/getMemberAccount")
    public Result<List<MemberAccount>> getMemberAccount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    ){
        String memberId = redisTemplateUtils.getStringValue(token);
        EntityWrapper<MemberAccount> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<MemberAccount> memberAccounts = memberAccountService.selectList(wrapper);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for(MemberAccount accout : memberAccounts){
            Bank bank = new Bank();
            bank.setBankCode(accout.getBankCode());
            Bank bankDb = bankMapper.selectOne(bank);
            if(bankDb!=null){
                accout.setBankLogo(systemImageUrl+bankDb.getBankLogo());
                accout.setBankName(bankDb.getBankName());
            }
        }
        return new Result<List<MemberAccount>>(Code.SUCCESS, "获取数据成功!", memberAccounts, Code.IS_ALERT_NO);
    }
}
