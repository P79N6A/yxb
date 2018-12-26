package com.yxbkj.yxb.feign;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.*;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CashParam;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.entity.vo.ZrpxParam;
import com.yxbkj.yxb.util.AccessToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述： 会员信息服务
 * 作者： 李明
 * 备注： 2017/07/31 10:22
 */
@FeignClient(value = "memberServer", configuration = FeignClientsConfiguration.class)
public interface ServerMemberFeignClient {
    /**
     * 作者:    李明
     * 描述:    发送验证码
     * 备注:
     * @param phone
     * @return
     */
    @RequestMapping(value = "memberInfo/sendSmsCode", method = RequestMethod.POST)
    Result<String> sendSmsCode(
            @RequestParam(value="phone")String phone,
            @RequestParam(value="ip")String ip
    );

    /**
     * 作者:    李明
     * 描述:    注册用户
     * 备注:
     * @param phone
     * @return
     */
    @RequestMapping(value = "memberInfo/registMember", method = RequestMethod.POST)
    Result<Map<String, Object>> registMember(@RequestParam(value="phone")String phone,
                                             @RequestParam(value="code")String code,
                                             @RequestParam(value="inviteCode")String inviteCode,
                                             @RequestParam(value="parentId",defaultValue = "")String parentId,
                                             @RequestParam(value="ip",defaultValue = "")String ip,
                                             @RequestParam(value="request") HttpServletRequest request
    );

    /**
     * 作者:    李明
     * 描述:    手机号码登录
     * 备注:
     * @param logVo
     * @return
     */
    @RequestMapping(value = "memberInfo/loginByPhone", method = RequestMethod.POST)
    Result<Map<String, Object>> loginByPhone(@RequestBody LoginLogVo logVo);

    /**
     * 作者:    李明
     * 描述:    密码登录
     * 备注:
     * @param logVo
     * @return
     */
    @RequestMapping(value = "memberInfo/loginByPwd", method = RequestMethod.POST)
    Result<Map<String, Object>> loginByPwd(@RequestBody LoginLogVo logVo
    );

    /**
     * 作者:    李明
     * 描述:    获取用户信息
     * 备注:
     * @param token
     * @return
     */
    @RequestMapping(value = "memberInfo/getUserInfo", method = RequestMethod.GET)
    Result<MemberInfo> getUserInfo(@RequestParam(value="token")String token
    );


    /**
     * 作者: 李明
     * 描述: 获取其他用户信息
     * 备注:
     * @param memberId
     * @return
     */
    @GetMapping("memberInfo/getOtherUserInfo")
    Result<MemberInfo> getOtherUserInfo(@ApiParam(value = "用户ID",required = true)@RequestParam(value="memberId")String memberId
    );

    /**
     * 作者: 李明
     * 描述: 修改昵称
     * 备注:
     * @param token
     * @param nickName
     * @return
     */
    @ApiOperation(value = "修改昵称",notes = "修改昵称")
    @PostMapping("memberInfo/updateNickName")
    Result<MemberInfo> updateNickName(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "昵称",required = true)@RequestParam(value="nickName")String nickName
    ) ;

    /**
     * 作者: 李明
     * 描述: 修改头像
     * 备注:
     * @param token
     * @param headImage
     * @return
     */
    @PostMapping("memberInfo/updateHeadImage")
    Result<MemberInfo> updateHeadImage(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "昵称",required = true)@RequestParam(value="headImage")String headImage
    ) ;


    /**
     * 作者:    李明
     * 描述:    获取会员资产信息
     * 备注:
     * @param token
     * @return
     */
    @RequestMapping(value = "memberProperty/getMemberProperty", method = RequestMethod.GET)
     Result<MemberProperty> getMemberProperty(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token);


    /**
     * 作者: 李明
     * 描述: 获取资产历史记录信息
     * 备注:
     * @param token
     * @param type
     * @param inOrOut
     * @param index
     * @param size
     * @return
     */
    @GetMapping("memberProperty/getMemberPropertyHis")
    Result<Page<MemberPropertyHis>> getMemberPropertyHis(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型(0是资产 1是易豆 不传为所有)",required = false)@RequestParam(value="type")String type
            ,@ApiParam(value = "收支(0是收入 1是支出 不传为所有)",required = false)@RequestParam(value="inOrOut")String inOrOut
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer index
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer size
    );

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
    @PostMapping("memberAccount/saveMemberAccount")
    Result<MemberAccount> saveMemberAccount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "银行编码",required = true)@RequestParam(value = "bankCode") String  bankCode
            ,@ApiParam(value = "开户行名称",required = true)@RequestParam(value = "depositBankName") String  depositBankName
            ,@ApiParam(value = "银行卡号",required = true)@RequestParam(value = "bankCardNo") String  bankCardNo
            ,@ApiParam(value = "首选状态",required = true)@RequestParam(value = "cardPreferred") String  cardPreferred
    );

    /**
     * 作者: 李明
     * 描述: 获取某会员所有账户信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取某会员所有账户信息",notes = "获取某会员所有账户信息")
    @GetMapping("memberAccount/getMemberAccount")
    Result<List<MemberAccount>> getMemberAccount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    );


    /**
     * 作者: 李明
     * 描述: 获取微信公众号授权
     * 备注:
     * @param code
     * @return
     */
    @PostMapping("memberInfo/actionAuth")
    Result<Map<String, Object>> actionAuth(@ApiParam(value = "CODE",required = true)@RequestParam(value="code")String code
    );

    /**
     * 作者: 李明
     * 描述: openid 绑定手机号
     * 备注:
     * @param phone
     * @param openId
     * @param code
     * @return
     */
    @PostMapping("memberInfo/bindPhoneByOpenId")
    Result<Map<String, Object>> bindPhoneByOpenId(
            @ApiParam(value = "电话号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "openId",required = true)@RequestParam(value="openId")String openId
            ,@ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code
            ,@ApiParam(value = "ip",required = true)@RequestParam(value="ip")String ip
    );

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
    @PostMapping("memberInfo/findPwdByPhone")
    Result<Map<String, Object>> findPwdByPhone(
            @ApiParam(value = "电话号码",required = true)@RequestParam(value="phone")String phone
            ,@ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code
            ,@ApiParam(value = "新密码",required = true)@RequestParam(value="newPassWord")String newPassWord
            ,@ApiParam(value = "确认密码",required = true)@RequestParam(value="confirmPassWord")String confirmPassWord
    );

    /**
     * 作者: 李明
     * 描述: 会员签到
     * 备注:
     * @param token
     * @param activeType
     * @return
     */
    @PostMapping("beanLog/memberSignIn")
    Result<Map<String,Object>> memberSignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "活动类型",required = true)@RequestParam(value = "activeType") String  activeType
    );

    /**
     * 作者: 李明
     * 描述: 今日是否签到
     * 备注:
     * @param token
     * @return
     */
    @GetMapping("beanLog/todaySignIn")
    Result<Boolean> todaySignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    );

    /**
     * 作者: 李明
     * 描述: 获取易豆记录
     * 备注:
     * @param token
     * @return
     */
    @GetMapping("beanLog/getBeanLog")
    Result<Page<BeanLog>> getBeanLog(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "类型 0 收入 1  支出 不传 所有",required = false)@RequestParam(value = "type") String  type
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    );


    /**
     * 作者: 李明
     * 描述: 退出登录
     * 备注:
     * @param token
     * @return
     */
    @PostMapping("memberInfo/logOut")
    Result<Boolean> logOut(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    );

    /**
     * 作者: 李明
     * 描述: 违章查询
     * 备注:
     * @param token
     * @param vin
     * @param license
     * @param engineNo
     * @return
     */
    @ApiOperation(value = "违章查询",notes = "违章查询")
    @GetMapping("trafficViolations/searchCarInfo")
    Result<Map<String, Object>> searchCarInfo(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                     @ApiParam(value = "车辆vin码",required = true)@RequestParam(value="vin")String vin,
                                                     @ApiParam(value = "车牌",required = true)@RequestParam(value="license")String license,
                                                     @ApiParam(value = "发动机",required = true)@RequestParam(value="engineNo")String engineNo
    );




    /**
     * 作者: 李明
     * 描述: 违章历史记录查询
     * 备注:
     * @param token
     * @param type
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("trafficViolations/searchCarInfoHis")
    Result<Page<TrafficViolations>> searchCarInfoHis(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = true)@RequestParam(value="type")String type
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ) ;

    /**
     * 作者: 李明
     * 描述: 车辆违章统计
     * 备注:
     * @param token
     * @param type
     * @return
     */
    @GetMapping("trafficViolations/carInfoStatistics")
    Result<List<Map<String, Object>>> carInfoStatistics(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = true)@RequestParam(value="type")String type
    );

    /**
     * 作者: 李明
     * 描述: 根据车牌号查询违章记录
     * 备注:
     * @param token
     * @param type
     * @return
     */
    @GetMapping("trafficViolations/searchCarInfoByCarplate")
    Result<Map<String,Object>> searchCarInfoByCarplate(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = true)@RequestParam(value="type",defaultValue = "")String type
            ,@ApiParam(value = "车牌号",required = true)@RequestParam(value="carplate")String carplate
    );

    /**
     * 作者: 李明
     * 描述: 中瑞认证接口
     * 备注:
     * @param param
     * @return
     */
    @PostMapping("zhongrui/baseAuth")
    Result<Map<String, Object>> baseAuth(@RequestBody ZrpxParam param);


    /**
     * 作者: 李明
     * 描述: 申请提现
     * 备注:
     * @param param
     * @return
     */
    @PostMapping("cashInfo/applyCashInfo")
    Result<CashInfo> applyCashInfo(
            @RequestBody CashParam param
    ) ;

    /**
     * 作者: 李明
     * 描述: 获取提现记录
     * 备注:
     * @param offset
     * @param limit
     * @param token
     * @param type
     * @return
     */
    @GetMapping("cashInfo/getCashInfo")
    Result<Page<CashInfo>> getCashInfo(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型",required = false)@RequestParam(value="type",required = false)String type
    ) ;

    /**
     * 作者: 李明
     * 描述: 获取提现记录新
     * 备注:
     * @param offset
     * @param limit
     * @param token
     * @param type
     * @return
     */
    @GetMapping("cashInfo/getCashInfoNew")
    Result<List<Map<String, Object>>> getCashInfoNew(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型",required = false)@RequestParam(value="type",required = false)String type
    ) ;


    /**
     * 作者: 李明
     * 描述: openId换Token
     * 备注:
     * @param openId
     * @return
     */
    @GetMapping("memberInfo/getTokenByOpenId")
    Result<Map<String,Object>> getTokenByOpenId(@ApiParam(value = "openId",required = true)@RequestParam(value="openId",defaultValue = "")String openId
    );

    /**
     * 作者: 李明
     * 描述: 获取用户团队信息
     * 备注:
     * @param memberId
     * @return
     */
    @GetMapping("memberInfo/getMemberTeam")
    Result<Map<String, Object>> getMemberTeam(
            @ApiParam(value = "会员ID",required = true)@RequestParam(value="memberId",defaultValue = "")String memberId
            ,@ApiParam(value = "会员等级(相关码表值)",required = true)@RequestParam(value="memberLevel",defaultValue = "")String memberLevel
    );

    /**
     * 作者: 李明
     * 描述: 会员升级
     * 备注:
     * @param token
     * @param payType
     * @param memberLevel
     * @return
     */
    @PostMapping("memberInfo/buyMember")
    Result<Map<String, Object>> buyMember(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "支付方式(相关码表)",required = true)@RequestParam(value="payType",defaultValue = "")String payType
            ,@ApiParam(value = "会员等级(相关码表)",required = true)@RequestParam(value="memberLevel",defaultValue = "")String memberLevel
            ,@ApiParam(value = "支付来源(同\"会员来源\"的相关码表值)",required = true)@RequestParam(value="source",defaultValue = "")String source
    );

    /**
     * 作者: 李明
     * 描述: 获取会员升级需要的价格信息
     * 备注:
     * @return
     */
    @GetMapping("memberInfo/getMemberPrice")
    Result<JSONObject> getMemberPrice();

    /**
     * 作者: 李明
     * 描述: 获取会员是否有上级
     * 备注:
     * @return
     */
    @GetMapping("memberInfo/getMemberParent")
    public Result<JSONObject> getMemberParent(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
    );

    /**
     * 作者: 李明
     * 描述: 绑定邀请码
     * 备注:
     * @return
     */
    @PostMapping("memberInfo/bindInviteCode")
    Result<MemberInfo> bindInviteCode(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "邀请码",required = true)@RequestParam(value="inviteCode",defaultValue = "")String inviteCode
    );

    /**
     * 作者: 李明
     * 描述: 获取邀请奖励信息
     * 备注:
     * @return
     */
    @GetMapping("memberInfo/getInviteMemberInfo")
    Result<Map<String, Object>> getInviteMemberInfo(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token",required = true,defaultValue = "")String token
    );



    @GetMapping("memberInfo/jql")
    Result<Map<String, Object>> jql (@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                @ApiParam(value = "类型",required = true)@RequestParam(value="type")String type);
}
