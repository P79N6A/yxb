package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.system.PrizeRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
public interface PrizeRecordMapper extends BaseMapper<PrizeRecord> {
    List<PrizeRecord> getPrizeRecordByPage(Map<String,Object> map);
}