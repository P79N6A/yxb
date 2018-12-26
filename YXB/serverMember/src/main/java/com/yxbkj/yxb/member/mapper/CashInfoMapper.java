package com.yxbkj.yxb.member.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.member.CashInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 提现信息表 Mapper 接口
 * </p>
 *
 * @author 李明
 * @since 2018-08-27
 */
public interface CashInfoMapper extends BaseMapper<CashInfo> {
   List<Map<String,Object>> getCashInfoNew(Map<String,Object> map);
}