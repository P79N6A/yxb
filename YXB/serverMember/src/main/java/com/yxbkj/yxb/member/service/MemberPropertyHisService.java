package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Result;

/**
 * <p>
 * 会员资产信息历史表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
public interface MemberPropertyHisService extends IService<MemberPropertyHis> {

    Result<Page<MemberPropertyHis>> getMemberPropertyHis(String token, String type, String inOrOut, Integer offset, Integer limit);
}
