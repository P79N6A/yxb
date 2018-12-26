package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductCompany;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProductSort;
import com.yxbkj.yxb.feign.ServerProductFeignClient;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
@Api(value = "ProductInfoController",description = "产品信息接口")
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ServerProductFeignClient serverProductFeignClient;
    /**
     * 作者: 李明
     * 描述: 分页获取产品信息
     * 备注:
     * @param offset
     * @return
     */
    @ApiOperation(value = "分页获取产品信息",notes = "分页获取产品信息")
    @GetMapping("/getProductInfoList")
    public Result<Page<Map<String,Object>>> getProductInfoList(
             @ApiParam(value = "页码",required = true)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = true)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "产品目录编码",required = false)@RequestParam(value="product_catalcode",required = false)String product_catalcode
    ){
        if(offset==null){
             return  new Result<>(Code.FAIL, "页码不能为空!", null, Code.IS_ALERT_YES);
        }
        if(limit==null){
            return  new Result<>(Code.FAIL, "条数不能为空!", null, Code.IS_ALERT_YES);
        }
        if(product_catalcode==null){
            product_catalcode= "";
        }
        return serverProductFeignClient.getProductInfoList(offset,limit,product_catalcode);
    }


    /**
     * 作者: 李明
     * 描述: 根据栏目分页获取产品信息
     * 备注:
     * @param offset
     * @return
     */
    @ApiOperation(value = "根据栏目分页获取产品信息",notes = "根据栏目分页获取产品信息")
    @GetMapping("/getProductInfoBySort")
    public Result<Page<Map<String,Object>>> getProductInfoBySort(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset",required = false)Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit",required = false)Integer limit
            ,@ApiParam(value = "栏目码表值",required = false)@RequestParam(value="sort",required = false)String sort
            ,@ApiParam(value = "客户端类型",required = false)@RequestParam(value="clientType",required = false,defaultValue = "")String clientType
            ,@ApiParam(value = "用户标识",required = false)@RequestParam(value="token",required = false,defaultValue = "")String token
    ){
        if(offset==null)offset =1;
        if(limit==null)limit =10;
        if(sort==null)sort ="";
        return  serverProductFeignClient.getProductInfoBySort(offset,limit,sort,clientType,token);
    }

}
