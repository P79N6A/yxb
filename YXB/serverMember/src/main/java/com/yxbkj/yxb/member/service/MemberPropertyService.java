package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.module.Result;

/**
 * <p>
 * 用户资产信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
public interface MemberPropertyService extends IService<MemberProperty> {

    Result<MemberProperty> getMemberProperty(String token);
}
