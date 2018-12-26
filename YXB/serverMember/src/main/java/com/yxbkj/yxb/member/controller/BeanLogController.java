package com.yxbkj.yxb.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.BeanLog;
import com.yxbkj.yxb.entity.member.MemberAccount;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.member.service.BeanLogService;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 易豆日志表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-13
 */
@RestController
@RequestMapping("/beanLog")
public class BeanLogController {

    @Autowired
    private BeanLogService beanLogService;
    /**
     * 作者: 李明
     * 描述: 会员签到
     * 备注:
     * @param token
     * @param activeType
     * @return
     */
    @ApiOperation(value = "会员签到",notes = "会员签到")
    @PostMapping("/memberSignIn")
    public Result<Map<String,Object>> memberSignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "活动类型",required = true)@RequestParam(value = "activeType") String  activeType
    ){
        return beanLogService.memberSignIn(token,activeType);
    }

    /**
     * 作者: 李明
     * 描述: 今日是否签到
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "今日是否签到",notes = "今日是否签到")
    @AccessToken
    @GetMapping("/todaySignIn")
    public Result<Boolean> todaySignIn(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
    ){
        return beanLogService.todaySignIn(token);
    }

    /**
     * 作者: 李明
     * 描述: 获取易豆记录
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取易豆记录",notes = "获取易豆记录")
    @AccessToken
    @GetMapping("/getBeanLog")
    public Result<Page<BeanLog>> getBeanLog(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token
            ,@ApiParam(value = "类型 0 收入 1  支出 不传 所有",required = false)@RequestParam(value = "type") String  type
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ){
        return beanLogService.getBeanLog(token,type,offset,limit);
    }


}
