package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.system.PrizeRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
public interface PrizeRecordService extends IService<PrizeRecord> {

    List<PrizeRecord> getPrizeRecordByPage(Map<String,Object> map);

}
