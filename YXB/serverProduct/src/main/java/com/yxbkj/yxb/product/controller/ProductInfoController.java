package com.yxbkj.yxb.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.*;
import com.yxbkj.yxb.product.mapper.MemberMapper;
import com.yxbkj.yxb.product.mapper.ProductBonusMapper;
import com.yxbkj.yxb.product.service.*;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
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
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductSortService productSortService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ProductCompanyService productCompanyService;
    @Autowired
    private ProductCatalogueService productCatalogueService;
    @Autowired
    private ProductProtectService productProtectService;
    @Autowired
    private ProductInvestmentService productInvestmentService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ProductBonusMapper productBonusMapper;

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
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "产品目录编码",required = false)@RequestParam(value="product_catalcode",required = false)String product_catalcode
    ){
        if(offset==null)offset =1;
        if(limit==null)limit =10;
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        Page<ProductInfo> page = new Page(offset,limit);
        EntityWrapper<ProductInfo> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        if(!StringUtil.isEmpty(product_catalcode)){
            wrapper.eq("product_catalcode",product_catalcode);
        }
        page.setOrderByField("creator_time"); // 排序参数

        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        page = productInfoService.selectPage(page, wrapper);

        List<ProductInfo> records = page.getRecords();

        //构造返回值
        Page<Map<String,Object>> returnPage = new Page(offset,limit);
        List<Map<String,Object>> maps = new ArrayList<>();

        for(ProductInfo productInfo : records){
            Map<String,Object> map = new HashMap<>();
            productInfo.setProductImg(systemImageUrl+productInfo.getProductImg());
            map.put("productInfo",productInfo);
            if(!StringUtil.isEmpty(product_catalcode)){
                map.put("10000441",new JSONObject());//车险
                map.put("10000442",new JSONObject());//非车险
                map.put("10000443",new JSONObject());//理财产品
               //如果传了目录
                EntityWrapper<ProductCatalogue> wraper_catalogue = new EntityWrapper<>();
                wraper_catalogue.eq("product_catalcode",product_catalcode);
                ProductCatalogue productCatalogue = productCatalogueService.selectOne(wraper_catalogue);
                if("10000442".equals(productCatalogue.getProductType())){
                    EntityWrapper<ProductProtect> wraper_protect = new EntityWrapper<>();
                    ProductProtect productProtect = productProtectService.selectOne(wraper_protect);
                    if(productProtect!=null)map.put("10000442",productProtect);//非车险
                }
                if("10000443".equals(productCatalogue.getProductType())){
                    EntityWrapper<ProductInvestment> wraper_investment = new EntityWrapper<>();
                    ProductInvestment investmentProtect = productInvestmentService.selectOne(wraper_investment);
                    if(investmentProtect!=null)map.put("10000443",investmentProtect);//理财产品
                }
            }
            //ProductCompany p = new ProductCompany();
            //p.setCompanyCode(productInfo.getCompanyCode());
            if(productInfo.getCompanyCode()!=null){
                EntityWrapper<ProductCompany> wraper_company = new EntityWrapper<>();
                wraper_company.eq("company_code",productInfo.getCompanyCode());
                ProductCompany productCompany = productCompanyService.selectOne(wraper_company);
                productCompany.setCompanyLogo(systemImageUrl+productCompany.getCompanyLogo());
                map.put("productCompany",productCompany);
            }else{
                map.put("productCompany",new JSONObject());
            }
            maps.add(map);
        }
        returnPage.setRecords(maps);
        return new Result<Page<Map<String,Object>>>(Code.SUCCESS, "获取数据成功!", returnPage, Code.IS_ALERT_NO);
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
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        Page<ProductSort> page = new Page(offset,limit);
        EntityWrapper<ProductSort> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        if(!StringUtil.isEmpty(sort)){
            wrapper.eq("column_type",sort);
        }
        if(!StringUtil.isEmpty(clientType)){
            wrapper.like("client_type",clientType);
        }
        page.setOrderByField("sort"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        page = productSortService.selectPage(page, wrapper);
        List<ProductSort> sorts = page.getRecords();
        List<ProductInfo> records = new ArrayList<>();
        for(ProductSort bean : sorts){
            EntityWrapper<ProductInfo> wrapper_product = new EntityWrapper();
            wrapper_product.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            wrapper_product.eq("product_id",bean.getProductId());
            ProductInfo productInfo = productInfoService.selectOne(wrapper_product);
            records.add(productInfo);
        }
        //构造返回值
        Page<Map<String,Object>> returnPage = new Page(offset,limit);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(ProductInfo productInfo : records){
            Map<String,Object> map = new HashMap<>();
            productInfo.setProductImg(systemImageUrl+productInfo.getProductImg());
            map.put("productInfo",productInfo);
            //ProductCompany p = new ProductCompany();
            //p.setCompanyCode(productInfo.getCompanyCode());
            if(productInfo.getCompanyCode()!=null){
                EntityWrapper<ProductCompany> wraper_company = new EntityWrapper<>();
                wraper_company.eq("company_code",productInfo.getCompanyCode());
                ProductCompany productCompany = productCompanyService.selectOne(wraper_company);
                productCompany.setCompanyLogo(systemImageUrl+productCompany.getCompanyLogo());
                map.put("productCompany",productCompany);
            }else{
                map.put("productCompany",new JSONObject());
            }
            if(productInfo.getProductCatalcode()!=null && "10000456".equals(sort)){
                map.put("investmentProduct",new JSONObject());//理财产品
                ProductInvestment investmentProduct =  productInfoService.getInvestmentProduct(productInfo.getProductId(),"10000443");
                 if(investmentProduct!=null){
                     map.put("investmentProduct",investmentProduct);//理财产品
                 }
            }
            //用户登录则拉取加佣数据
            if (token != null && !token.equals("")) {
                String memberId = redisTemplateUtils.getStringValue(token);
                if (memberId != null && !memberId.equals("") ) {
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setMemberId(memberId);
                    MemberInfo member = memberMapper.selectOne(memberInfo);
                    ProductBonus productBonus = new ProductBonus();
                    productBonus.setProductId(productInfo.getProductId());
                    productBonus.setMemberLevel(member.getMemberlevel());
                    ProductBonus productBonusInfo = productBonusMapper.selectOne(productBonus);
                    map.put("productBonus",productBonusInfo);
                } else {
                    ProductBonus productBonus = new ProductBonus();
                    productBonus.setProductId(productInfo.getProductId());
                    productBonus.setMemberLevel("10000143");
                    ProductBonus productBonusInfo = productBonusMapper.selectOne(productBonus);
                    map.put("productBonus",productBonusInfo);
                }
            } else {
                ProductBonus productBonus = new ProductBonus();
                productBonus.setProductId(productInfo.getProductId());
                productBonus.setMemberLevel("10000143");
                ProductBonus productBonusInfo = productBonusMapper.selectOne(productBonus);
                map.put("productBonus",productBonusInfo);
            }
            maps.add(map);
        }
        returnPage.setRecords(maps);
        return new Result<Page<Map<String,Object>>>(Code.SUCCESS, "获取数据成功!", returnPage, Code.IS_ALERT_NO);
    }


}
