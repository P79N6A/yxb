package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.system.Prize;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 奖品表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
public interface PrizeService extends IService<Prize> {

    Result<Map<String,Object>> executeDraw(String token,String activityNo,String ip);

    Result<List<Prize>> getPrizeList(String activityNo);

    Result<Integer> getTodayDrawCount(String token,String activityNo);
}
