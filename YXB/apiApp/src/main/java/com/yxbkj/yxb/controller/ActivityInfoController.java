package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.system.ActivityInfo;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 活动信息 控制器
 * @author zy
 * @desc
 * @since
 */
@Api(value = "ActivityInfoController",description = "活动信息相关接口")
@RestController
@RequestMapping("/activityInfo")
public class ActivityInfoController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * 获取所有活动
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "获取活动信息列表",notes = "获取活动信息列表")
    @GetMapping("/getActivityInfoList")
    public Result<List<ActivityInfo>> getActivityInfoList() {
        return serverSystemFeignClient.getActivityInfoList();
    }
    /**
     * 添加活动
     * @author zy
     * @desc
     * @since
     */
    /*@ApiOperation(value = "添加活动列表",notes = "添加活动列表")
    @PostMapping(value = "/addActivityInfo")
    public Result<Map<String, Object>> addActivityInfo(
            @ApiParam(value = "活动名称",required = true)@RequestParam(value="activityName",required = true) String activityName
            ,@ApiParam(value = "活动简介",required = true)@RequestParam(value="activityDesc",required = true) String activityDesc
            ,@ApiParam(value = "活动金额",required = true) @RequestParam(value = "activityMoney",required = true) String activityMoney
            ,@ApiParam(value = "创建人",required = true) @RequestParam(value = "creator",required = true) String creator
            ,@ApiParam(value = "活动开始时间",required = true) @RequestParam(value = "activityStartTime",required = true) Date activityStartTime
            ,@ApiParam(value = "活动结束时间",required = true) @RequestParam(value = "activityEndTime",required = true) Date activityEndTime
            ,HttpServletRequest request
    ) {
        Result<Map<String, Object>> result = null;
        result = serverSystemFeignClient.addActivityInfo(activityName,activityDesc,activityMoney,creator,activityStartTime,activityEndTime,request);
        return result;
    }*/
    /**
     * 删除活动
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "删除活动信息",notes = "删除活动信息")
    @PostMapping(value = "/deleteByActivityNo")
    public Result<Map<String, Object>> deleteByActivityNo(
            @ApiParam(value = "活动Id", required = true)@RequestParam(value = "activityNo",required = true) String activityNo
            ) {
                return serverSystemFeignClient.deleteByActivityNo(activityNo);
    }
    /**
     * 活动是否在进行中
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "活动是否正在进行",notes = "活动是否正在进行")
    @PostMapping(value = "/isStart")
    public Result<Boolean> isStart(
            @ApiParam(value = "活动Id", required = true)@RequestParam(value = "activityNo",required = true) String activityNo
    ) {
        return serverSystemFeignClient.isStart(activityNo);
    }
}
