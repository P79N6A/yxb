package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.BeanLog;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.entity.vo.ZrpxParam;
import com.yxbkj.yxb.feign.ServerMemberFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.Api;
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
@Api(value = "MemberInfoController",description = "会员信息接口")
@RestController
@RequestMapping("/memberInfo")
public class MemberInfoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ServerMemberFeignClient serverMemberFeignClient;
    /**
     * 作者: 李明
     * 描述: 发送验证码
     * 备注:
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送验证码",notes = "发送验证码")
    @PostMapping("/sendSmsCode")
    public Result<String> sendSmsCode(@ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone,HttpServletRequest request) {
        Result<String> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "发送失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        result = serverMemberFeignClient.sendSmsCode(phone,HttpKit.getClientIP(request));
        return result;
    }


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
    public Result<Map<String, Object>> registMember(@ApiParam(value = "手机号码",required = true)@RequestParam(value="phone",required = true)String phone,
                                                    @ApiParam(value = "验证码",required = true)@RequestParam(value="code",required = true)String code,
                                                    @ApiParam(value = "邀请码",required = false)@RequestParam(value="inviteCode",required = false)String inviteCode,
                                                    @ApiParam(value = "上级ID",required = false)@RequestParam(value="parentId",defaultValue = "")String parentId,
                                                    HttpServletRequest request
    ) {
        Result<Map<String, Object>> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "注册失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(code)){
            result = new Result<>(Code.FAIL, "注册失败,验证码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(inviteCode==null)
            inviteCode ="";

        synchronized(this){
            result = serverMemberFeignClient.registMember(phone, code, inviteCode, parentId,HttpKit.getClientIP(request), request);
        }

        return result;
    }

    /**
     * 作者: 李明
     * 描述: 手机号码登录
     * 备注:
     * @param phone
     * @param code
     * @param loginSource
     * @param loginLongitude
     * @param loginLatitude
     * @return
     */
    @ApiOperation(value = "手机号码登录",notes = "注册用户")
    @PostMapping("/loginByPhone")
    public Result<Map<String, Object>> loginByPhone(
            @ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code
            ,@ApiParam(value = "登录来源",required = true)@RequestParam(value="loginSource")String loginSource
            ,@ApiParam(value = "经度",required = false)@RequestParam(value="loginLongitude",required = false)String loginLongitude
            ,@ApiParam(value = "维度",required = false)@RequestParam(value="loginLatitude",required = false)String loginLatitude
            ,HttpServletRequest request
    ) {
        Result<Map<String, Object>> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "登录失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(code)){
            result = new Result<>(Code.FAIL, "登录失败,验证码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        LoginLogVo vo = new LoginLogVo();
        vo.setPhone(phone);
        vo.setCode(code);
        vo.setIp(HttpKit.getClientIP(request));
        vo.setLoginSource(loginSource);
        vo.setLoginLongitude(loginLongitude);
        vo.setLoginLatitude(loginLatitude);
        return serverMemberFeignClient.loginByPhone(vo);
    }

    /**
     * 作者: 李明
     * 描述: 密码
     * 备注:
     * @param phone
     * @param password
     * @param loginSource
     * @param loginLongitude
     * @param loginLatitude
     * @return
     */
    @ApiOperation(value = "密码登录",notes = "此接口需使application/json传输")
    @PostMapping("/loginByPwd")
    public Result<Map<String, Object>> loginByPwd(
            @ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "密码",required = true)@RequestParam(value="password")String password
            ,@ApiParam(value = "登录来源",required = true)@RequestParam(value="loginSource")String loginSource
            ,@ApiParam(value = "经度",required = false)@RequestParam(value="loginLongitude",required = false)String loginLongitude
            ,@ApiParam(value = "维度",required = false)@RequestParam(value="loginLatitude",required = false)String loginLatitude
            ,HttpServletRequest request
    ) {
        Result<Map<String, Object>> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "登录失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(phone)){
            result = new Result<>(Code.FAIL, "登录失败,密码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        LoginLogVo vo = new LoginLogVo();
        vo.setPhone(phone);
        vo.setPassword(password);
        vo.setLoginSource(loginSource);
        vo.setLoginLongitude(loginLongitude);
        vo.setLoginLatitude(loginLatitude);
        vo.setIp(HttpKit.getClientIP(request));
        return serverMemberFeignClient.loginByPwd(vo);
    }

    /**
     * 作者: 李明
     * 描述: 获取用户信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @AccessToken
    @GetMapping("/getUserInfo")
    public Result<MemberInfo> getUserInfo(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ) {
        return serverMemberFeignClient.getUserInfo(token);
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
    public Result<Map<String, Object>> getWxOppenId(@ApiParam(value = "CODE",required = true)@RequestParam(value="code")String code
    ) {
        if(StringUtil.isEmpty(code)){
            code="";
        }
        return serverMemberFeignClient.actionAuth(code);
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
            ,HttpServletRequest request
    ) {
        if(StringUtil.isEmpty(phone)){
            phone="";
        }
        if(StringUtil.isEmpty(openId)){
            openId="";
        }
        if(StringUtil.isEmpty(code)){
            code="";
        }

        Result<Map<String, Object>> result = null;
        synchronized (this){
            result = serverMemberFeignClient.bindPhoneByOpenId(phone, openId, code,HttpKit.getClientIP(request));
        }

        return result;
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
        return serverMemberFeignClient.findPwdByPhone(phone,code,newPassWord,confirmPassWord);
    }


    /**
     * 作者: 李明
     * 描述: 会员签到
     * 备注:
     * @param token
     * @param activeType
     * @return
     */
    @ApiOperation(value = "会员签到",notes = "会员签到")
    @AccessToken
    @PostMapping("/memberSignIn")
    public Result<Map<String,Object>> memberSignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "活动类型",required = true)@RequestParam(value = "activeType") String  activeType
    ){
        if(StringUtil.isEmpty(token)){
            return  new Result<>(Code.FAIL, "token不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(activeType)){
            return  new Result<>(Code.FAIL, "activeType不能为空!", null, Code.IS_ALERT_YES);
        }
        return serverMemberFeignClient.memberSignIn(token,activeType);
    }

    /**
     * 作者: 李明
     * 描述: 今日是否签到
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "今日是否签到",notes = "今日是否签到")
    @GetMapping("/todaySignIn")
    public Result<Boolean> todaySignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    ){
        return serverMemberFeignClient.todaySignIn(token);
    }

    /**
     * 作者: 李明
     * 描述: 获取易豆记录
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取易豆记录",notes = "获取易豆记录")
    @AccessToken
    @GetMapping("/getBeanLog")
    public Result<Page<BeanLog>> getBeanLog(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "类型 0 收入 1  支出 不传 所有",required = false)@RequestParam(value = "type",defaultValue = "") String  type
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset",required =  false)Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit",required = false)Integer limit
    ){
        if(offset==null)offset=1;
        if(limit==null)limit=10;
        return serverMemberFeignClient.getBeanLog(token,type,offset,limit);
    }


    /**
     * 作者: 李明
     * 描述: 退出登录
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "退出登录",notes = "退出登录")
    @AccessToken
    @PostMapping("/logOut")
    public Result<Boolean> logOut(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ){
        return serverMemberFeignClient.logOut(token);
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
        return  serverMemberFeignClient.updateNickName(token,nickName);
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
        return  serverMemberFeignClient.updateHeadImage(token,headImage);
    }

    /**
     * 作者: 李明
     * 描述: 中瑞认证接口
     * 备注:
     * @param param
     * @return
     */
    @ApiOperation(value = "中瑞认证接口",notes = "中瑞认证接口")
    @PostMapping("/baseAuth")
    public Result<Map<String, Object>> baseAuth(ZrpxParam param){
        return serverMemberFeignClient.baseAuth(param);
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
        if(openId==null)openId="";
        return serverMemberFeignClient.getTokenByOpenId(openId);
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
        return serverMemberFeignClient.getMemberTeam(memberId,memberLevel);
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
        return serverMemberFeignClient.buyMember(token,payType,memberLevel,source);
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
        return serverMemberFeignClient.getMemberPrice();
    }

    /**
     * 作者: 李明
     * 描述: 获取会员是否有上级
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取会员是否有上级",notes = "获取会员是否有上级")
    @GetMapping("/getMemberParent")
    @AccessToken
    public Result<JSONObject> getMemberParent(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    ) {
        return serverMemberFeignClient.getMemberParent(token);
    }

    /**
     * 作者: 李明
     * 描述: 绑定邀请码
     * 备注:
     * @return
     */
    @ApiOperation(value = "绑定邀请码",notes = "绑定邀请码")
    @PostMapping("/bindInviteCode")
    @AccessToken
    public Result<MemberInfo> bindInviteCode(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "邀请码",required = true)@RequestParam(value="inviteCode",defaultValue = "")String inviteCode
    ) {
        return serverMemberFeignClient.bindInviteCode(token,inviteCode);
    }

    /**
     * 作者: 李明
     * 描述: 获取邀请奖励信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取邀请奖励信息",notes = "获取邀请奖励信息")
    @GetMapping("/getInviteMemberInfo")
    @AccessToken
    public Result<Map<String, Object>> getInviteMemberInfo(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token",required = true,defaultValue = "")String token
    ) {
        return serverMemberFeignClient.getInviteMemberInfo(token);
    }


    @ApiOperation(value = "加密",notes = "加密")
    @GetMapping("/jql")
    @AccessToken
    public Result<Map<String, Object>> test(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
            @ApiParam(value = "类型",required = true)@RequestParam(value="type")String type){
        return serverMemberFeignClient.jql(token,type);
    }

}
