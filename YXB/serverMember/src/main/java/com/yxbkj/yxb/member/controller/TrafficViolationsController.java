package com.yxbkj.yxb.member.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.TrafficViolations;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.member.service.TrafficViolationsService;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆违章记录表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
@RestController
@RequestMapping("/trafficViolations")
public class TrafficViolationsController {
    @Autowired
    private TrafficViolationsService trafficViolationsService;

    /**
     * 作者: 李明
     * 描述: 违章查询
     * 备注:
     * @param token
     * @param vin
     * @param license
     * @param engineNo
     * @return
     */
    @ApiOperation(value = "违章查询",notes = "违章查询")
    @GetMapping("/searchCarInfo")
    public Result<Map<String, Object>> searchCarInfo(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                     @ApiParam(value = "车辆vin码",required = true)@RequestParam(value="vin")String vin,
                                                     @ApiParam(value = "车牌",required = true)@RequestParam(value="license")String license,
                                                     @ApiParam(value = "发动机",required = true)@RequestParam(value="engineNo")String engineNo
                                                    ) {
        if(StringUtil.isEmpty(vin)){
            return new Result<>(Code.FAIL, "vin不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(license)){
            return new Result<>(Code.FAIL, "license不能为空!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(engineNo)){
            return new Result<>(Code.FAIL, "engineNo不能为空!", null, Code.IS_ALERT_YES);
        }
        return trafficViolationsService.searchCarInfo(token,vin,license,engineNo);
    }

    /**
     * 作者: 李明
     * 描述: 违章历史记录查询
     * 备注:
     * @param token
     * @param type
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation(value = "违章历史记录查询",notes = "违章历史记录查询")
    @GetMapping("/searchCarInfoHis")
    public Result<Page<TrafficViolations>> searchCarInfoHis(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = false)@RequestParam(value="type",defaultValue = "")String type
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ) {
        if(offset==null){
            return new Result<Page<TrafficViolations>>(Code.FAIL,"offset不能为空!",null,Code.IS_ALERT_YES);
        }
        if(limit==null){
            return new Result<Page<TrafficViolations>>(Code.FAIL,"limit不能为空!",null,Code.IS_ALERT_YES);
        }
        return trafficViolationsService.searchCarInfoHis(token,type,offset,limit);
    }

    /**
     * 作者: 李明
     * 描述: 车辆违章统计
     * 备注:
     * @param token
     * @param type
     * @return
     */
    @ApiOperation(value = "车辆违章统计",notes = "车辆违章统计")
    @GetMapping("/carInfoStatistics")
    public Result<List<Map<String, Object>>> carInfoStatistics(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = true)@RequestParam(value="type",defaultValue = "")String type
    ) {
        return trafficViolationsService.carInfoStatistics(token,type);
    }

    /**
     * 作者: 李明
     * 描述: 根据车牌号查询违章记录
     * 备注:
     * @param token
     * @param type
     * @return
     */
    @ApiOperation(value = "根据车牌号查询违章记录",notes = "根据车牌号查询违章记录")
    @GetMapping("/searchCarInfoByCarplate")
    public Result<Map<String,Object>> searchCarInfoByCarplate(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型 10000622 未处理 10000621 已处理 不传为 全部",required = false)@RequestParam(value="type",defaultValue = "")String type
            ,@ApiParam(value = "车牌号",required = true)@RequestParam(value="carplate")String carplate
    ) {
        if(StringUtil.isEmpty(carplate)){
            return new Result<Map<String,Object>>(Code.FAIL,"车牌号不能为空",null,Code.IS_ALERT_NO);
        }
        return trafficViolationsService.searchCarInfoByCarplate(token,type,carplate);
    }

}
