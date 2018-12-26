package com.yxbkj.yxb.member.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.CashInfo;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CashParam;
import com.yxbkj.yxb.entity.vo.ZrpxParam;
import com.yxbkj.yxb.member.service.CashInfoService;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RestController
@RequestMapping("/cashInfo")
public class CashInfoController {

    @Autowired
    private CashInfoService cashInfoService;
    /**
     * 作者: 李明
     * 描述: 申请提现
     * 备注:
     * @param param
     * @return
     */
    @ApiOperation(value = "申请提现",notes = "申请提现")
    @PostMapping("/applyCashInfo")
    public Result<CashInfo> applyCashInfo(
            @RequestBody CashParam param
    ) {
        return  cashInfoService.applyCashInfo(param);
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
    @GetMapping("/getCashInfo")
    public  Result<Page<CashInfo>> getCashInfo(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型",required = false)@RequestParam(value="type",required = false)String type
    ) {
        if(offset==null){
            return new Result<>(Code.SUCCESS,"页码不能为空",null,Code.IS_ALERT_NO);
        }
        if(limit==null){
            return new Result<>(Code.SUCCESS,"条数不能为空",null,Code.IS_ALERT_NO);
        }
        return  cashInfoService.getCashInfo(offset,limit,token,type);
    }

    /**
     * 作者: 李明
     * 描述: 获取提现记录新
     * 备注:
     * @param offset
     * @param limit
     * @param token
     * @param type
     * @return
     */
    @ApiOperation(value = "获取提现记录新",notes = "获取提现记录新")
    @GetMapping("/getCashInfoNew")
    public  Result<List<Map<String, Object>>> getCashInfoNew(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型",required = false)@RequestParam(value="type",required = false)String type
    ) {
        if(offset==null){
            return new Result<>(Code.SUCCESS,"页码不能为空",null,Code.IS_ALERT_NO);
        }
        if(limit==null){
            return new Result<>(Code.SUCCESS,"条数不能为空",null,Code.IS_ALERT_NO);
        }
        return  cashInfoService.getCashInfoNew(offset,limit,token,type);
    }


}
