package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.TrafficViolations;
import com.yxbkj.yxb.entity.module.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆违章记录表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
public interface TrafficViolationsService extends IService<TrafficViolations> {
    Result<Map<String,Object>> searchCarInfo(String token, String vin, String license, String engineNo);
    Result<Page<TrafficViolations>> searchCarInfoHis(String token, String type, Integer offset, Integer limit);
    Result<List<Map<String, Object>>> carInfoStatistics(String token,String type);
    Result<Map<String,Object>> searchCarInfoByCarplate(String token, String type, String carplate);
}
