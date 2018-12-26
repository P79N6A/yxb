package com.yxbkj.yxb.member.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.LoginLogVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * <p>
 * 会员信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
public interface MemberInfoService extends IService<MemberInfo> {
    MemberInfo findMemberByPhone(String phone);
    Result<Map<String, Object>> registMember(String phone, String code,String inviteCode,String parentId,String ip,HttpServletRequest request);
    Result<Map<String, Object>> loginByPhone(LoginLogVo logVo);
    Result<Map<String, Object>> loginByPwd(LoginLogVo logVo);
    Result<Map<String,Object>> wxAuth(String code);
    Result<Map<String,Object>> bindPhoneByOpenId(String phone, String openId, String code,String ip,HttpServletRequest request);
    Result<Map<String,Object>> findPwdByPhone( String phone, String code, String newPassWord);

    Result<MemberInfo> updateNickName(String token, String nickName);

    Result<MemberInfo> updateHeadImage(String token, String headImage);

    Result<MemberInfo> getUserInfo(String token);

    Result<String> sendSmsCode(String phone,String ip);

    Result<Map<String,Object>> getTokenByOpenId(String openId);

    Result<Map<String, Object>> getMemberTeam(String memberId,String memberLevel);

    Result<Map<String,Object>> buyMember(String token, String payType, String memberLevel,String source);

    Result<JSONObject> getMemberPrice();

    Result<JSONObject> getMemberParent(String token);

    Result<MemberInfo> bindInviteCode(String token, String inviteCode);

    Result<Map<String, Object>> jql (String token, String type);

    Result<MemberInfo> getOtherUserInfo(String memberId);

    Result<Map<String,Object>> getInviteMemberInfo(String token);
}