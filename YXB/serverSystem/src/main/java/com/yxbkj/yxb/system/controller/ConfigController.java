package com.yxbkj.yxb.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.app.Config;
import com.yxbkj.yxb.entity.member.MemberAccount;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.CodeInfo;
import com.yxbkj.yxb.entity.system.CodeType;
import com.yxbkj.yxb.system.mapper.CodeTypeMapper;
import com.yxbkj.yxb.system.service.CodeInfoService;
import com.yxbkj.yxb.system.service.CodeTypeService;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配置管理 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-18
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private CodeTypeService codeTypeService;
    @Autowired
    private CodeInfoService codeInfoService;
    /**
     * 作者: 李明
     * 描述: 根据key获取系统配置
     * 备注:
     * @param key
     * @return
     */
    @ApiOperation(value = "根据key获取系统配置",notes = "根据key获取系统配置")
    @GetMapping("/getConfigValue")
    public String getConfigValue(
            @ApiParam(value = "键",required = true)@RequestParam(value = "key") String  key
    ){
        return configService.getConfigValue(key);
    }

    /**
     * 作者: 李明
     * 描述: 获取所有码表类型信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有码表类型信息",notes = "获取所有码表类型信息")
    @GetMapping("/getAllCodeType")
    public Result<List<CodeType>> getAllCodeType(){
        EntityWrapper<CodeType> codeTypeEntityWrapper = new EntityWrapper<>();
        codeTypeEntityWrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        return  new Result<List<CodeType>>(Code.SUCCESS,"查询成功!",codeTypeService.selectList(codeTypeEntityWrapper),Code.IS_ALERT_NO);
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
        String[] types = typeCode.split(",");
        Map<String,Object> map = new HashMap<>();
        for(String str : types){
            EntityWrapper<CodeInfo> codeInfoEntityWrapper = new EntityWrapper<>();
            codeInfoEntityWrapper.eq("type_code",str);
            codeInfoEntityWrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            codeInfoEntityWrapper.orderBy("code_sort",true);
            List<CodeInfo> codeInfos = codeInfoService.selectList(codeInfoEntityWrapper);
            map.put(str,codeInfos);
        }
        return  new Result<Map<String,Object>>(Code.SUCCESS,"查询成功!",map,Code.IS_ALERT_NO);
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
            @ApiParam(value = "类型",required = true)@RequestParam(value = "codeValue",required = true,defaultValue = "") String  codeValue)
    {
            if(StringUtil.isEmpty(codeValue)){
                return  new Result<CodeInfo>(Code.FAIL,"码表值不能为空!",null,Code.IS_ALERT_YES);
            }
            EntityWrapper<CodeInfo> codeInfoEntityWrapper = new EntityWrapper<>();
            codeInfoEntityWrapper.eq("code_value",codeValue);
            codeInfoEntityWrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            CodeInfo codeInfos = codeInfoService.selectOne(codeInfoEntityWrapper);
            return  new Result<CodeInfo>(Code.SUCCESS,"查询成功!",codeInfos,Code.IS_ALERT_NO);
    }

}
