package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.CodeInfo;
import com.yxbkj.yxb.entity.system.CodeType;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.RequestParams;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.pingan.EPay;
import com.yxbkj.yxb.util.wxshare.WxUtil;
import com.yxbkj.yxb.util.zrbx.ZRBXProductUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  系统相关接口 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-10
 */
@Api(value = "SystemController",description = "系统相关接口")
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    /**
     * 作者: 李明
     * 描述: 获取所有码表类型信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有码表类型信息",notes = "获取所有码表类型信息")
    @GetMapping("/getAllCodeType")
    public Result<List<CodeType>> getAllCodeType(){
        return  serverSystemFeignClient.getAllCodeType();
    }

    /**
     * 作者: 李明
     * 描述: 根据码表类型 获取码表值
     * 备注:
     * @param typeCode
     * @return
     */
    @ApiOperation(value = "根据码表类型 获取码表值",notes = "根据码表类型 获取码表值")
    @GetMapping("/getCodeByType")
    public Result<Map<String,Object>> getCodeByType(
            @ApiParam(value = "类型  多个类型用逗号分割",required = true)@RequestParam(value = "typeCode",required = true,defaultValue = "") String  typeCode
    ){
        if(StringUtil.isEmpty(typeCode)){
            return  new Result<Map<String,Object>>(Code.FAIL,"类型不能为空!",null,Code.IS_ALERT_YES);
        }
        return  serverSystemFeignClient.getCodeByType(typeCode);
    }

    /**
     * 作者: 李明
     * 描述: 根据码表值获取码表对象
     * 备注:
     * @param codeValue
     * @return
     */
    @ApiOperation(value = "根据码表值获取码表对象",notes = "根据码表值获取码表对象")
    @GetMapping("/getNameByValue")
    public Result<CodeInfo> getNameByValue(
            @ApiParam(value = "类型",required = true)@RequestParam(value = "codeValue",required = true,defaultValue = "") String  codeValue
    ){
        if(StringUtil.isEmpty(codeValue)){
            return  new Result<CodeInfo>(Code.FAIL,"码表值不能为空!",null,Code.IS_ALERT_YES);
        }
        return  serverSystemFeignClient.getNameByValue(codeValue);
    }

    /**
     * 作者: 李明
     * 描述: 获取所有银行信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有银行信息",notes = "获取所有银行信息")
    @GetMapping("/getAllBank")
    public Result<List<Bank>> getAllBank(){
        return  serverSystemFeignClient.getAllBank();
    }


    /**
     * 作者: 李明
     * 描述: 获取分享信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取分享信息",notes = "获取分享信息")
    @GetMapping("/getWxShareinfo")
    public Result<Map<String, String>> getWxShareinfo(
            @ApiParam(value = "地址(暂时不传)",required = true)@RequestParam(value = "url",required = false,defaultValue = "") String  url
    ){
        return serverSystemFeignClient.getWxShareinfo(url);
    }
    /**
     * 作者: 李明
     * 描述: 获取APP版本信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取APP版本信息",notes = "获取APP版本信息")
    @GetMapping("/getAppVersion")
    public Result<JSONObject> getAppVersion(){
        return serverSystemFeignClient.getAppVersion();
    }

    /**
     * 作者: 李明
     * 描述: 获取服务器时间戳
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取服务器时间戳",notes = "获取服务器时间戳")
    @GetMapping("/getCurrentTimeMillis")
    public Result<String> getCurrentTimeMillis(){
        return new Result<>(Code.SUCCESS,"获取成功",""+System.currentTimeMillis(), Code.IS_ALERT_NO);
    }

    /**
     * 作者: 李明
     * 描述: 查看环境
     * 备注:
     * @return
     */
    @ApiOperation(value = "查看环境",notes = "查看环境")
    @GetMapping("/getEnvironment")
    public Object getEnvironment(){
        Map<String,Object> map = new HashMap();
        map.put("EPay.return_url", EPay.getReturnUrl());
        map.put("EPay.notify_url",EPay.getNotifyUrl());
        map.put("ZRBXProductUtils.backUrl",ZRBXProductUtils.getBackUrl());
        map.put("ZRBXProductUtils.shareUrl",ZRBXProductUtils.getShareUrl());
        return map;
    }

    public static void main(String[] args) {
        System.out.println(EPay.getNotifyUrl());
    }

}
