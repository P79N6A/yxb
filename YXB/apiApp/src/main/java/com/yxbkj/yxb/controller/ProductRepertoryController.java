package com.yxbkj.yxb.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductAnalysis;
import com.yxbkj.yxb.entity.product.ProductCompany;
import com.yxbkj.yxb.entity.product.ProductRepertory;
import com.yxbkj.yxb.entity.product.ReterporyCategory;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 产品仓库表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-11-12
 */
@Api(value = "ProductRepertoryController",description = "产品库接口")
@RestController
@RequestMapping("/productRepertory")
public class ProductRepertoryController {
    @Autowired
    private ServerProductFeignClient serverProductFeignClient;

    /**
     * 作者: 李明
     * 描述: 分页获取产品库列表
     * 备注:
     * @param offset
     * @return
     */
    @ApiOperation(value = "分页获取产品库列表",notes = "分页获取产品库列表")
    @GetMapping("/getProductRepertoryList")
    public Result<Page<ReterporyCategory>> getProductRepertoryList(
            @ApiParam(value = "页码",required = true)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = true)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode",defaultValue = "")String companyCode
    ) {
        return serverProductFeignClient.getProductRepertoryList(offset,limit,companyCode);
    }

    /**
     * 作者: 李明
     * 描述: 根据分类获取产品库列表
     * 备注:
     * @param offset
     * @return
     */
    @ApiOperation(value = "根据分类获取产品库列表",notes = "分页获取产品库列表")
    @GetMapping("/getProductRepertoryByCategory")
    public Result<Page<ProductRepertory>> getProductRepertoryByCategory(
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "分类ID(分类信息中的id)",required = false)@RequestParam(value="categoryId")String categoryId
    ) {
        return serverProductFeignClient.getProductRepertoryByCategory(offset,limit,categoryId);
    }

    /**
     * 作者: 李明
     * 描述: 关键字搜索产品库
     * 备注:
     * @return
     */
    @ApiOperation(value = "关键字搜索产品库",notes = "关键字搜索产品库")
    @GetMapping("/searchProductRepertory")
    public Result<List<ReterporyCategory>> searchProductRepertory(
            @ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord",defaultValue = "")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode",defaultValue = "")String companyCode
            ,@ApiParam(value = "分类ID(分类信息中的id)",required = false)@RequestParam(value="category_id",defaultValue = "")String category_id
    ) {
        return serverProductFeignClient.searchProductRepertory(keyWord,companyCode,category_id);
     }

    /**
     * 作者: 李明
     * 描述: 关键字搜索产品库—新
     * 备注:
     * @return
     */
    @ApiOperation(value = "关键字搜索产品库—新",notes = "关键字搜索产品库—新")
    @GetMapping("/searchProductRepertoryNew")
    public Result<List<ProductRepertory>> searchProductRepertoryNew(
            @ApiParam(value = "页码",required = true)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = true)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord",defaultValue = "")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode",defaultValue = "")String companyCode
            ,@ApiParam(value = "分类ID(分类信息中的id)",required = false)@RequestParam(value="category_id",defaultValue = "")String category_id
    ) {
        return serverProductFeignClient.searchProductRepertoryNew(offset,limit,keyWord,companyCode,category_id);
        }


    /**
     * 作者: 李明
     * 描述: 获取所有分类
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有分类",notes = "获取所有分类")
    @GetMapping("/getAllProductCategory")
    public Result<List<ReterporyCategory>> getAllProductCategory(
    ) {
        return serverProductFeignClient.getAllProductCategory();
    }

    /**
     * 作者: 李明
     * 描述: 获取产品库详情
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取产品库详情",notes = "获取产品库详情")
    @GetMapping("/getProductRepertory")
    public Result<Map<String,Object>> getProductRepertory(
            @ApiParam(value = "产品库ID",required = false)@RequestParam(value="productId",defaultValue = "")String productId
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token",defaultValue = "")String token
    ) {
        return serverProductFeignClient.getProductRepertory(productId,token);
    }

    /**
     * 作者: 李明
     * 描述: 获取产品库详情
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取产品解读详情",notes = "获取产品解读详情")
    @GetMapping("/getProductAnalysis")
    public Result<ProductAnalysis> getProductAnalysis(
            @ApiParam(value = "产品库ID",required = false)@RequestParam(value="productId")String productId
    ) {
        return serverProductFeignClient.getProductAnalysis(productId);
    }


    /**
     * 作者: 李明
     * 描述: 获取产品公司列表
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取产品公司列表",notes = "获取产品公司列表")
    @GetMapping("/getProductCompany")
    public Result<List<ProductCompany>> getProductCompany(
    ) {
        return serverProductFeignClient.getProductCompany();
    }

}
