package com.yxbkj.yxb.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.ActivityInfo;
import com.yxbkj.yxb.system.service.ActivityInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 活动信息 控制器
 * @author zy
 * @desc
 * @since
 */
@RestController
@RequestMapping("/activityInfo")
public class ActivityInfoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ActivityInfoService activityInfoService;
    /**
     * 获取活动列表
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "获取所有活动信息",notes = "获取所有活动信息")
    @GetMapping("/getActivityInfoList")
    public Result<List<ActivityInfo>> getActivityInfoList() {
        EntityWrapper<ActivityInfo> activityInfoWrapper = new EntityWrapper<>();
        activityInfoWrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<ActivityInfo> activityInfos = activityInfoService.selectList(activityInfoWrapper);
        return new Result<List<ActivityInfo>>(Code.SUCCESS,"查询成功!",activityInfos,Code.IS_ALERT_NO);
    }
    /**
     * 添加活动
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "添加活动信息",notes = "添加活动信息")
    @PostMapping(value = "/addActivityInfo")
    public Result<Map<String, Object>> addActivityInfo(@ApiParam(value = "活动名称",required = true)@RequestParam(value="activityName",required = true) String activityName
            ,@ApiParam(value = "活动简介",required = true)@RequestParam(value="activityDesc",required = true) String activityDesc
            ,@ApiParam(value = "活动金额",required = true) @RequestParam(value = "activityMoney",required = true) String activityMoney
            ,@ApiParam(value = "创建人",required = true) @RequestParam(value = "creator",required = true) String creator
            ,@ApiParam(value = "活动开始时间",required = true) @RequestParam(value = "activityStartTime",required = true) Date activityStartTime
            ,@ApiParam(value = "活动结束时间",required = true) @RequestParam(value = "activityEndTime",required = true) Date activityEndTime
            , HttpServletRequest request
    ) {
        return activityInfoService.addActivityInfo(activityName,activityDesc,activityMoney,creator,activityStartTime,activityEndTime,request);
    }
    /**
     * 删除活动
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "删除活动信息",notes = "删除活动信息")
    @PostMapping(value = "/deleteByActivityNo")
    public Result<Map<String, Object>> deleteByActivityNo(@ApiParam(value = "删除活动",required = true)@RequestParam(value = "activityNo",required = true) String activityNo) {
        return activityInfoService.deleteByActivityNo(activityNo);
    }
    /**
     * 活动是否在进行中
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "活动是否正在进行",notes = "活动是否正在进行")
    @PostMapping(value = "/isStart")
    public Result<Boolean> isStart(@ApiParam(value = "活动是否开始",required = true)@RequestParam(value = "activityNo",required = true) String activityNo) {
        return activityInfoService.isStart(activityNo);
    }
}
