package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.system.PrizeRecord;
import com.yxbkj.yxb.entity.system.ReceivingInfo;
import com.yxbkj.yxb.entity.vo.ReceivingInfoVo;

import java.util.Map;

/**
 * <p>
 * 收货信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-10-30
 */
public interface ReceivingInfoService extends IService<ReceivingInfo> {

    Result<Map<String,Object>> addReceivingInfo(ReceivingInfoVo entity);

    Result<Page<PrizeRecord>> getPrizeInfoNews(String token,String activityNo, Integer offset, Integer limit);
}
