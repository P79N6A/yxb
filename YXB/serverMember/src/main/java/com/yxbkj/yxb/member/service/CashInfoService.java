package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.CashInfo;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CashParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-27
 */
public interface CashInfoService extends IService<CashInfo> {
    Result<CashInfo> applyCashInfo(CashParam param);
    Result<Page<CashInfo>> getCashInfo(Integer offset, Integer limit,String token, String type);
    Result<List<Map<String, Object>>> getCashInfoNew(Integer offset, Integer limit, String token, String type);
}
