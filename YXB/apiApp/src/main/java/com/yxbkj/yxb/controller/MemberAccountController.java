package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.member.MemberAccount;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerMemberFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "MemberAccountController",description = "会员账户信息接口(银行卡信息)")
@RestController
@RequestMapping("/memberAccount")
public class MemberAccountController {
    @Autowired
    private ServerMemberFeignClient serverMemberFeignClient;
    /**
     * 作者: 李明
     * 描述: 添加会员账户信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "添加会员账户信息",notes = "添加会员账户信息")
    @AccessToken
    @PostMapping("/saveMemberAccount")
    public Result<MemberAccount> saveMemberAccount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "银行编码",required = true)@RequestParam(value = "bankCode") String  bankCode
            ,@ApiParam(value = "开户行名称",required = true)@RequestParam(value = "depositBankName") String  depositBankName
            ,@ApiParam(value = "银行卡号",required = true)@RequestParam(value = "bankCardNo") String  bankCardNo
            ,@ApiParam(value = "首选状态",required = true)@RequestParam(value = "cardPreferred") String  cardPreferred
    ){
        if(StringUtil.isEmpty(bankCode)) bankCode="";
        if(StringUtil.isEmpty(depositBankName)) depositBankName="";
        if(bankCardNo==null) bankCardNo="";
        if(StringUtil.isEmpty(cardPreferred)) cardPreferred="";
        return   serverMemberFeignClient.saveMemberAccount(token, bankCode, depositBankName, bankCardNo, cardPreferred);
     }

    /**
     * 作者: 李明
     * 描述: 获取某会员所有账户信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取某会员所有账户信息",notes = "获取某会员所有账户信息")
    @AccessToken
    @GetMapping("/getMemberAccount")
    public Result<List<MemberAccount>> getMemberAccount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    ){
        return serverMemberFeignClient.getMemberAccount(token);
    }

}
