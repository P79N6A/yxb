package com.yxbkj.yxb.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
@RestController
@RequestMapping("/memberInfo")
public class MemberInfoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    /**
     * 作者: 李明
     * 描述: 注册用户
     * 备注:
     * @param phone
     * @param code
     * @return
     */
    @ApiOperation(value = "注册用户",notes = "注册用户")
    @PostMapping("/registMember")
    public Result<Map<String, Object>> registMember(@ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone,
                                                    @ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code,
                                                    @ApiParam(value = "邀请码",required = false)@RequestParam(value="inviteCode")String inviteCode,
                                                    @ApiParam(value = "上级ID",required = false)@RequestParam(value="parentId",defaultValue = "")String parentId,
                                                    @ApiParam(value = "ip",required = false)@RequestParam(value="ip",defaultValue = "")String ip,
                                                    HttpServletRequest request){
        Result<Map<String, Object>> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "注册失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(code)){
            result = new Result<>(Code.FAIL, "注册失败,验证码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        return memberInfoService.registMember(phone, code, inviteCode,parentId,ip,request);
    }
    /**
     * 作者: 李明
     * 描述: 发送验证码
     * 备注:
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送验证码",notes = "发送验证码")
    @PostMapping("/sendSmsCode")
    public Result<String> sendSmsCode(
            @ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "ip",required = true)@RequestParam(value="ip")String ip
    ) {
        return memberInfoService.sendSmsCode(phone,ip);
    }

    /**
     * 作者: 李明
     * 描述: 手机号码登录
     * 备注:
     * @param logVo
     * @return
     */
    @ApiOperation(value = "手机号码登录",notes = "手机号码登录")
    @PostMapping("/loginByPhone")
    public Result<Map<String, Object>> loginByPhone(@RequestBody LoginLogVo logVo
    ) {
        Result<Map<String, Object>> result = null;
        if(logVo.getPhone()==null || !ValidateUtils.isPhone(logVo.getPhone())){
            result = new Result<>(Code.FAIL, "登录失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(logVo.getCode())){
            result = new Result<>(Code.FAIL, "登录失败,验证码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        return memberInfoService.loginByPhone(logVo);
    }

    /**
     * 作者: 李明
     * 描述: 密码登录
     * 备注:
     * @param logVo
     * @return
     */
    @ApiOperation(value = "密码登录",notes = "密码登录")
    @PostMapping("/loginByPwd")
    public Result<Map<String, Object>> loginByPwd(@RequestBody LoginLogVo logVo
    ) {
            return  memberInfoService.loginByPwd(logVo);
    }

    /**
     * 作者: 李明
     * 描述: 获取用户信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result<MemberInfo> getUserInfo(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ) {
        return memberInfoService.getUserInfo(token);
    }

    /**
     * 作者: 李明
     * 描述: 获取其他用户信息
     * 备注:
     * @param memberId
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @GetMapping("/getOtherUserInfo")
    public Result<MemberInfo> getOtherUserInfo(@ApiParam(value = "用户ID",required = true)@RequestParam(value="memberId")String memberId
    ) {
        return memberInfoService.getOtherUserInfo(memberId);
    }

    /**
     * 作者: 李明
     * 描述: 获取用户团队信息
     * 备注:
     * @param memberId
     * @return
     */
    @ApiOperation(value = "获取用户团队信息",notes = "获取用户团队信息")
    @GetMapping("/getMemberTeam")
    public Result<Map<String, Object>> getMemberTeam(
            @ApiParam(value = "会员ID",required = true)@RequestParam(value="memberId",defaultValue = "")String memberId
            ,@ApiParam(value = "会员等级(相关码表值)",required = true)@RequestParam(value="memberLevel",defaultValue = "")String memberLevel
    ) {
        return memberInfoService.getMemberTeam(memberId,memberLevel);
    }

    /**
     * 作者: 李明
     * 描述: openId换Token
     * 备注:
     * @param openId
     * @return
     */
    @ApiOperation(value = "openId换Token",notes = "openId换Token")
    @GetMapping("/getTokenByOpenId")
    public Result<Map<String,Object>> getTokenByOpenId(@ApiParam(value = "openId",required = true)@RequestParam(value="openId",defaultValue = "")String openId
    ) {
        return memberInfoService.getTokenByOpenId(openId);
    }

    /**
     * 作者: 李明
     * 描述: 退出登录
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "退出登录",notes = "退出登录")
    @PostMapping("/logOut")
    public Result<Boolean> logOut(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ) {
        if(token==null){
            return  new Result<>(Code.FAIL, "token不能为空!", null, Code.IS_ALERT_YES);
        }
        boolean detele = redisTemplateUtils.detele(token);
        return  new Result<Boolean>(Code.SUCCESS, "操作成功!", detele, Code.IS_ALERT_NO);
    }


    /**
     * 作者: 李明
     * 描述: 修改昵称
     * 备注:
     * @param token
     * @param nickName
     * @return
     */
    @ApiOperation(value = "修改昵称",notes = "修改昵称")
    @PostMapping("/updateNickName")
    public Result<MemberInfo> updateNickName(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "昵称",required = true)@RequestParam(value="nickName")String nickName
    ) {
        if(StringUtil.isEmpty(nickName)){
            return  new Result<>(Code.FAIL, "昵称不能为空!", null, Code.IS_ALERT_YES);
        }
        return  memberInfoService.updateNickName(token,nickName);
    }

    /**
     * 作者: 李明
     * 描述: 修改头像
     * 备注:
     * @param token
     * @param headImage
     * @return
     */
    @ApiOperation(value = "修改头像",notes = "修改头像")
    @PostMapping("/updateHeadImage")
    public Result<MemberInfo> updateHeadImage(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "昵称",required = true)@RequestParam(value="headImage")String headImage
    ) {
        if(StringUtil.isEmpty(headImage)){
            return  new Result<>(Code.FAIL, "头像不能为空!", null, Code.IS_ALERT_YES);
        }
        return  memberInfoService.updateHeadImage(token,headImage);
    }


    /**
     * 作者: 李明
     * 描述: 获取微信公众号授权
     * 备注:
     * @param code
     * @return
     */
    @ApiOperation(value = "获取微信公众号授权",notes = "获取微信公众号授权")
    @PostMapping("/actionAuth")
    public Result<Map<String, Object>> actionAuth(@ApiParam(value = "CODE",required = true)@RequestParam(value="code")String code
    ) {
        if(StringUtil.isEmpty(code)){
            return  new Result<>(Code.FAIL, "code不能为空!", null, Code.IS_ALERT_NO);
        }
        return memberInfoService.wxAuth(code);
    }

    /**
     * 作者: 李明
     * 描述: openid 绑定手机号
     * 备注:
     * @param phone
     * @param openId
     * @param code
     * @return
     */
    @ApiOperation(value = "openId 绑定手机号",notes = "openId 绑定手机号")
    @PostMapping("/bindPhoneByOpenId")
    public Result<Map<String, Object>> bindPhoneByOpenId(
             @ApiParam(value = "电话号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "openId",required = true)@RequestParam(value="openId")String openId
            ,@ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code
            ,@ApiParam(value = "ip",required = true)@RequestParam(value="ip")String ip
             ,HttpServletRequest request
    ) {
        if(StringUtil.isEmpty(phone)){
            return  new Result<>(Code.FAIL, "电话不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(openId)){
            return  new Result<>(Code.FAIL, "openId不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(code)){
            return  new Result<>(Code.FAIL, "code不能为空!", null, Code.IS_ALERT_YES);
        }
        return memberInfoService.bindPhoneByOpenId(phone,openId,code,ip,request);
    }

    /**
     * 作者: 李明
     * 描述: 短信找回密码
     * 备注:
     * @param phone
     * @param code
     * @param newPassWord
     * @param confirmPassWord
     * @return
     */
    @ApiOperation(value = "短信找回密码",notes = "短信找回密码")
    @PostMapping("/findPwdByPhone")
    public Result<Map<String, Object>> findPwdByPhone(
            @ApiParam(value = "电话号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code
            ,@ApiParam(value = "新密码",required = true)@RequestParam(value="newPassWord")String newPassWord
            ,@ApiParam(value = "确认密码",required = true)@RequestParam(value="confirmPassWord")String confirmPassWord
    ) {
        if(StringUtil.isEmpty(phone)){
            return  new Result<>(Code.FAIL, "电话不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(code)){
            return  new Result<>(Code.FAIL, "验证码不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(newPassWord)){
            return  new Result<>(Code.FAIL, "新密码不能为空!", null, Code.IS_ALERT_YES);
        }
        if(!newPassWord.equals(confirmPassWord)){
            return  new Result<>(Code.FAIL, "新旧密码不一致!", null, Code.IS_ALERT_YES);
        }
        return memberInfoService.findPwdByPhone(phone,code,newPassWord);
    }

    /**
     * 作者: 李明
     * 描述: 会员升级
     * 备注:
     * @param token
     * @param payType
     * @param memberLevel
     * @return
     */
    @ApiOperation(value = "会员升级",notes = "会员升级")
    @PostMapping("/buyMember")
    public Result<Map<String, Object>> buyMember(
             @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "支付方式(相关码表)",required = true)@RequestParam(value="payType",defaultValue = "")String payType
            ,@ApiParam(value = "会员等级(相关码表)",required = true)@RequestParam(value="memberLevel",defaultValue = "")String memberLevel
            ,@ApiParam(value = "支付来源(同\"会员来源\"的相关码表值)",required = true)@RequestParam(value="source",defaultValue = "")String source
    ) {
        return memberInfoService.buyMember(token,payType,memberLevel,source);
    }

    /**
     * 作者: 李明
     * 描述: 获取会员升级需要的价格信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取会员升级需要的价格信息",notes = "获取会员升级需要的价格信息")
    @GetMapping("/getMemberPrice")
    public Result<JSONObject> getMemberPrice() {
        return memberInfoService.getMemberPrice();
    }

    /**
     * 作者: 李明
     * 描述: 获取会员是否有上级
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取会员是否有上级",notes = "获取会员是否有上级")
    @GetMapping("/getMemberParent")
    public Result<JSONObject> getMemberParent(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ) {
        return memberInfoService.getMemberParent(token);
    }

    /**
     * 作者: 李明
     * 描述: 绑定邀请码
     * 备注:
     * @return
     */
    @ApiOperation(value = "绑定邀请码",notes = "绑定邀请码")
    @PostMapping("/bindInviteCode")
    public Result<MemberInfo> bindInviteCode(
             @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "邀请码",required = true)@RequestParam(value="inviteCode",defaultValue = "")String inviteCode
    ) {
        return memberInfoService.bindInviteCode(token,inviteCode);
    }

    /**
     * 作者: 李明
     * 描述: 获取邀请奖励信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取邀请奖励信息",notes = "获取邀请奖励信息")
    @GetMapping("/getInviteMemberInfo")
    public Result<Map<String, Object>> getInviteMemberInfo(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token",required = true,defaultValue = "")String token
    ) {
        return memberInfoService.getInviteMemberInfo(token);
    }

    @GetMapping("/jql")
    public Result<Map<String, Object>> test(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token, String type){
        return memberInfoService.jql(token,type);
    }

}
