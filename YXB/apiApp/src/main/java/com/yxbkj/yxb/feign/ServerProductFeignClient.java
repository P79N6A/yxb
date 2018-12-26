package com.yxbkj.yxb.feign;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductAnalysis;
import com.yxbkj.yxb.entity.product.ProductCompany;
import com.yxbkj.yxb.entity.product.ProductRepertory;
import com.yxbkj.yxb.entity.product.ReterporyCategory;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述： 产品信息服务
 * 作者： 李明
 * 备注： 2017/08/08 13:22
 */
@FeignClient(value = "productServer", configuration = FeignClientsConfiguration.class)
public interface ServerProductFeignClient {

    /**
     * 作者: 李明
     * 描述: 分页获取产品信息
     * 备注:
     * @param offset
     * @return
     */
     @GetMapping("productInfo/getProductInfoList")
     Result<Page<Map<String,Object>>> getProductInfoList(
             @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
             ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
             ,@ApiParam(value = "产品目录编码",required = false)@RequestParam(value="product_catalcode",required = false)String product_catalcode
     );

    /**
     * 作者:    唐漆
     * 描述:    获取产品信息
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "premium/premium", method = RequestMethod.GET)
    Result<Map<String, Object>> premium(@RequestParam(value="productId")String productId,
                                        @RequestParam(value="minAge")String minAge,
                                        @RequestParam(value="maxAge")String maxAge,
                                        @RequestParam(value="socialSecurityType")String socialSecurityType,
                                        @RequestParam(value="insuranceScale")String insuranceScale,
                                        @RequestParam(value="timeLimit",required = false)String timeLimit);


    /**
     * 作者: 李明
     * 描述: 根据栏目分页获取产品信息
     * 备注:
     * @param offset
     * @return
     */
    @GetMapping("productInfo/getProductInfoBySort")
    Result<Page<Map<String,Object>>> getProductInfoBySort(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset",required = false)Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit",required = false)Integer limit
            ,@ApiParam(value = "栏目码表值",required = false)@RequestParam(value="sort",required = false)String sort
            ,@ApiParam(value = "客户端类型",required = false)@RequestParam(value="clientType",required = false,defaultValue = "")String clientType
            ,@ApiParam(value = "用户标识",required = false)@RequestParam(value="token",required = false,defaultValue = "")String token
    );



    /**
     * 作者: 唐漆
     * 描述: 添加卡单1
     * 备注:
     * @return
     */
    @GetMapping("card/card")
    Result<Map<String, Object>> insertCard(@ApiParam(value = "产品ID")@RequestParam(value = "productId",required = true)String productId,
                                           @ApiParam(value = "交易金额")@RequestParam(value = "amount",required = true)BigDecimal amount,
                                           @ApiParam(value = "车牌号")@RequestParam(value = "plateNumber",required = true)String plateNumber,
                                           @ApiParam(value = "令牌",required = true)@RequestParam(value="token", required = true)String token,
                                           @ApiParam(value = "投保人名字")@RequestParam(value = "policyHolder",required = true)String policyHolder,
                                           @ApiParam(value = "投保人身份证")@RequestParam(value = "policyCard",required = true)String policyCard,
                                           @ApiParam(value = "VIN码")@RequestParam(value = "chassisNumber",required = true)String chassisNumber,
                                           @ApiParam(value = "投保人手机号")@RequestParam(value = "policyPhone",required = true)String policyPhone,
                                           @ApiParam(value = "核定载客人数")@RequestParam(value = "number",required = true)String number,
                                           @ApiParam(value = "来源")@RequestParam(value = "source",required = true)String source);


    /**
     * 作者: 李明
     * 描述: 分页获取产品库列表
     * 备注:
     * @param offset
     * @return
     */
    @GetMapping("productRepertory/getProductRepertoryList")
    Result<Page<ReterporyCategory>> getProductRepertoryList(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode",defaultValue = "")String companyCode
    );

    /**
     * 作者: 李明
     * 描述: 根据分类获取产品库列表
     * 备注:
     * @param offset
     * @return
     */
    @GetMapping("productRepertory/getProductRepertoryByCategory")
    Result<Page<ProductRepertory>> getProductRepertoryByCategory(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="categoryId")String categoryId
    );

    /**
     * 作者: 李明
     * 描述: 关键字搜索产品库
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/searchProductRepertory")
    Result<List<ReterporyCategory>> searchProductRepertory(
            @ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode")String companyCode
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="category_id")String category_id
    );

    /**
     * 作者: 李明
     * 描述: 关键字搜索产品库—新
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/searchProductRepertoryNew")
    Result<List<ProductRepertory>> searchProductRepertoryNew(
            @ApiParam(value = "页码",required = true)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = true)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode")String companyCode
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="category_id")String category_id
    );
    /**
     * 作者: 李明
     * 描述: 获取所有分类
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/getAllProductCategory")
    Result<List<ReterporyCategory>> getAllProductCategory(
    );


    /**
     * 作者: 李明
     * 描述: 获取产品库详情
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/getProductRepertory")
    Result<Map<String,Object>> getProductRepertory(
            @ApiParam(value = "产品库ID",required = false)@RequestParam(value="productId")String productId
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token",defaultValue = "")String token
    );

    /**
     * 作者: 李明
     * 描述: 获取产品库详情
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/getProductAnalysis")
    Result<ProductAnalysis> getProductAnalysis(
            @ApiParam(value = "产品库ID",required = false)@RequestParam(value="productId")String productId
    );

    /**
     * 作者: 李明
     * 描述: 获取产品公司列表
     * 备注:
     * @return
     */
    @GetMapping("productRepertory/getProductCompany")
    Result<List<ProductCompany>> getProductCompany(
    );


}
