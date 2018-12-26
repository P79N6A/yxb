package com.yxbkj.yxb.service;

import com.yxbkj.yxb.common.utils.Result;
import com.yxbkj.yxb.domain.model.MemberInfo;
import com.baomidou.mybatisplus.service.IService;

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
    Result<Map<String, Object>> registMember(String phone,String code, HttpServletRequest request);
}