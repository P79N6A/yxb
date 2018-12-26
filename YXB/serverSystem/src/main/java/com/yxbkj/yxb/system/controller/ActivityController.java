package com.yxbkj.yxb.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.system.Prize;
import com.yxbkj.yxb.entity.system.PrizeRecord;
import com.yxbkj.yxb.entity.vo.ReceivingInfoVo;
import com.yxbkj.yxb.system.service.PrizeRecordService;
import com.yxbkj.yxb.system.service.PrizeService;
import com.yxbkj.yxb.system.service.ReceivingInfoService;
import com.yxbkj.yxb.util.HttpUtil;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.zhongan.YxbRsaUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抽奖活动管理 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-18
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PrizeService prizeService;
    @Autowired
    private PrizeRecordService prizeRecordService;
    @Autowired
    private ReceivingInfoService receivingInfoService;
    /**
     * 作者: 李明
     * 描述: 获取奖品列表
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取奖品列表",notes = "获取奖品列表")
    @GetMapping("/getPrizeList")
    public Result<List<Prize>> getPrizeList(
            @ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
    ){
      return  prizeService.getPrizeList(activityNo);
    }
    /**
     * 作者: 李明
     * 描述: 执行抽奖
     * 备注:
     * @return
     */
    @ApiOperation(value = "执行抽奖",notes = "执行抽奖")
    @PostMapping("/executeDraw")
    public Result<Map<String, Object>> executeDraw(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token
            ,@ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
            ,@ApiParam(value = "Ip",required = true)@RequestParam(value = "Ip",required = true,defaultValue = "") String  Ip
    ){
        return prizeService.executeDraw(token,activityNo,Ip);
    }

    /**
     * 作者: 李明
     * 描述: 获取今日抽奖次数
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取今日抽奖次数",notes = "获取今日抽奖次数")
    @GetMapping("/getTodayDrawCount")
    public Result<Integer> getTodayDrawCount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token
            ,@ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
    ){
        return prizeService.getTodayDrawCount(token,activityNo);
    }


    /**
     * 作者: 李明
     * 描述: 添加收货地址
     * 备注:
     * @return
     */
    @ApiOperation(value = "添加收货地址",notes = "添加收货地址")
    @PostMapping("/addReceivingInfo")
    public Result<Map<String, Object>> addReceivingInfo(
            @RequestBody ReceivingInfoVo entity
            ){
        return receivingInfoService.addReceivingInfo(entity);
    }


    /**
     * 作者: 李明
     * 描述: 分页获取最新中奖信息
     * 备注:
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页获取最新中奖信息",notes = "分页获取最新中奖信息")
    @GetMapping("/getPrizeInfoNews")
    public Result<Page<PrizeRecord>> getPrizeInfoNews(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = false,defaultValue = "") String  token
            ,@ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ) {
        return receivingInfoService.getPrizeInfoNews(token,activityNo,offset,limit);


    }




}
