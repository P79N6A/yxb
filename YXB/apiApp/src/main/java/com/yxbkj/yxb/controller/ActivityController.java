package com.yxbkj.yxb.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.Prize;
import com.yxbkj.yxb.entity.system.PrizeRecord;
import com.yxbkj.yxb.entity.vo.ReceivingInfoVo;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@Api(value = "ActivityController",description = "抽奖活动相关接口")
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
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
        return serverSystemFeignClient.getPrizeList(activityNo);
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
            ,HttpServletRequest request
    ){
        synchronized(this){
            return serverSystemFeignClient.executeDraw(token,activityNo,HttpKit.getClientIP(request));
        }
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
             ReceivingInfoVo entity
            ){
        return serverSystemFeignClient.addReceivingInfo(entity);
    }

    /**
     * 作者: 李明
     * 描述: 分页获取最新中奖信息
     * 备注:
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页获取最新中奖信息(传Token为查自己的不传为查非易豆中奖)",notes = "分页获取最新中奖信息(传Token为查自己的不传为查非易豆中奖)")
    @GetMapping("/getPrizeInfoNews")
    public Result<Page<PrizeRecord>> getPrizeInfoNews(
            @ApiParam(value = "令牌",required = false)@RequestParam(value = "token",required = false,defaultValue = "") String  token,
            @ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ) {
        if (offset == null) offset = 1;
        if (limit == null) limit = 10;
        if(StringUtil.isEmpty(token))token="";
        return serverSystemFeignClient.getPrizeInfoNews(token,activityNo,offset,limit);
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
        return serverSystemFeignClient.getTodayDrawCount(token,activityNo);
    }

}
