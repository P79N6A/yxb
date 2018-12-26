package com.yxbkj.yxb.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerMemberFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户资产信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@Api(value = "MemberPropertyController",description = "会员资产信息接口")
@RestController
@RequestMapping("/memberProperty")
public class MemberPropertyController {
    @Autowired
    private ServerMemberFeignClient serverMemberFeignClient;
    /**
     * 作者: 李明
     * 描述: 获取会员资产信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取会员资产信息",notes = "获取会员资产信息")
    @AccessToken
    @GetMapping("/getMemberProperty")
    public Result<MemberProperty> getMemberProperty(@ApiParam(value = "令牌",required = true)@RequestParam(value="token",defaultValue = "")String token
      ){
        return serverMemberFeignClient.getMemberProperty(token);
    }

    /**
     * 作者: 李明
     * 描述: 获取资产历史记录信息
     * 备注:
     * @param token
     * @param type
     * @param inOrOut
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation(value = "获取资产历史记录信息",notes = "获取资产历史记录信息")
    @AccessToken
    @GetMapping("/getMemberPropertyHis")
    public Result<Page<MemberPropertyHis>> getMemberPropertyHis(
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "收支(0是收入 1是支出 不传为所有)",required = false)@RequestParam(value="inOrOut",defaultValue = "",required = false)String inOrOut
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset",required = false)Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit",required = false)Integer limit
    ){
        String type="";
        if(StringUtil.isEmpty(inOrOut)) inOrOut="";
        if(offset==null) offset=1;
        if(limit==null) limit=10;
        return serverMemberFeignClient.getMemberPropertyHis(token,type,inOrOut,offset,limit);
    }
}
