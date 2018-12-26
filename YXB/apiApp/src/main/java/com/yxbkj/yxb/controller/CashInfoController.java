package com.yxbkj.yxb.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.CashInfo;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CashParam;
import com.yxbkj.yxb.feign.ServerMemberFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-27
 */
@Api(value = "CashInfoController",description = "提现相关接口")
@RestController
@RequestMapping("/cashInfo")
public class CashInfoController {

    @Autowired
    private ServerMemberFeignClient serverMemberFeignClient;
    /**
     * 作者: 李明
     * 描述: 申请提现
     * 备注:
     * @param param
     * @return
     */
    @ApiOperation(value = "申请提现",notes = "申请提现")
    @AccessToken
    @PostMapping("/applyCashInfo")
    public Result<CashInfo> applyCashInfo(CashParam param) {
        return  serverMemberFeignClient.applyCashInfo(param);
    }

    /**
     * 作者: 李明
     * 描述: 获取提现记录
     * 备注:
     * @param offset
     * @param limit
     * @param token
     * @param type
     * @return
     */
    @ApiOperation(value = "获取提现记录",notes = "获取提现记录")
    @AccessToken
    @GetMapping("/getCashInfo")
    public  Result<Page<CashInfo>> getCashInfo(
            @ApiParam(value = "页码")@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数")@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌")@RequestParam(value="token")String token
            ,@ApiParam(value = "类型",required = false)@RequestParam(value="type",required = false,defaultValue = "")String type
    ) {
        if(offset==null){
            return new Result<>(Code.SUCCESS,"页码不能为空",null,Code.IS_ALERT_NO);
        }
        if(limit==null){
            return new Result<>(Code.SUCCESS,"条数不能为空",null,Code.IS_ALERT_NO);
        }
        return  serverMemberFeignClient.getCashInfo(offset,limit,token,type);
    }

    /**
     * 作者: 李明
     * 描述: 获取提现记录新
     * 备注:
     * @param offset
     * @param limit
     * @param token
     * @return
     */
    @ApiOperation(value = "获取提现记录新",notes = "获取提现记录新")
    @AccessToken
    @GetMapping("/getCashInfoNew")
    public  Result<List<Map<String, Object>>> getCashInfoNew(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token")String token
    ) {
        if(offset==null){
            return new Result<>(Code.SUCCESS,"页码不能为空",null,Code.IS_ALERT_NO);
        }
        if(limit==null){
            return new Result<>(Code.SUCCESS,"条数不能为空",null,Code.IS_ALERT_NO);
        }
        return  serverMemberFeignClient.getCashInfoNew(offset,limit,token,"");
    }

}
