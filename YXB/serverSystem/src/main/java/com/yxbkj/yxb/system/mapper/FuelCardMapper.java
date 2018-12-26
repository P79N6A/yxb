package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.product.FuelCard;

import java.util.Map;

/**
 * <p>
 * 加油卡表 Mapper 接口
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
public interface FuelCardMapper extends BaseMapper<FuelCard> {
    Map<String,Object> elifeActivity(Map<String,Object> map);
}
