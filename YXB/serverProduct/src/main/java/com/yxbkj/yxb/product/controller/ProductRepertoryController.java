package com.yxbkj.yxb.product.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.app.Collection;
import com.yxbkj.yxb.entity.app.ImgInfo;
import com.yxbkj.yxb.entity.app.Like;
import com.yxbkj.yxb.entity.app.StaffInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.*;
import com.yxbkj.yxb.product.mapper.*;
import com.yxbkj.yxb.product.service.ConfigService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 产品仓库表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-11-12
 */
@RestController
@RequestMapping("/productRepertory")
public class ProductRepertoryController{
    @Autowired
    private ReterporyCategoryMapper reterporyCategoryMapper;
    @Autowired
    private ProductRepertoryMapper productRepertoryMapper;
    @Autowired
    private ProductProtocolMapper productProtocolMapper;
    @Autowired
    private ProductAnalysisMapper productAnalysisMapper;
    @Autowired
    private ProductPlanMapper  productPlanMapper;
    @Autowired
    private ProductCompanyMapper  productCompanyMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private ImgInfoMapper imgInfoMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

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
            @ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode",defaultValue = "")String companyCode
    ) {
        if (offset == null) offset = 1;
        if (limit == null) limit = 10;
        //构造返回值
        Page<ReterporyCategory> page = new Page(offset,limit);
        EntityWrapper<ReterporyCategory> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        page.setOrderByField("creator_time"); // 排序参数
        page.setAsc(true); // 为true表示顺序排列，false为倒序排列
        List<ReterporyCategory> reterporyCategories = null;
        if(StringUtil.isEmpty(companyCode)){
            reterporyCategories = reterporyCategoryMapper.selectPage(page, wrapper);
        }else{
              //公司编码特殊处理
              reterporyCategories = getCategoryByCom(page,wrapper,companyCode);
        }
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for(ReterporyCategory category : reterporyCategories){
            Page<ProductRepertory> productRepertoryPage = new Page(1,2);
            EntityWrapper<ProductRepertory> repertory_wrapper = new EntityWrapper();
            repertory_wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            repertory_wrapper.like(true,"category_id",category.getId());
            if(!StringUtil.isEmpty(companyCode)){
                repertory_wrapper.eq("company_code",companyCode);
            }
            //repertory_wrapper.setSqlSelect("category_id like '%"+category.getId()+"%'");
            productRepertoryPage.setOrderByField("creator_time"); // 排序参数
            productRepertoryPage.setAsc(false); // 为true表示顺序排列，false为倒序排列
            //List<ProductRepertory> productRepertories = productRepertoryMapper.getListByCategory(category.getId());
           List<ProductRepertory> productRepertories = productRepertoryMapper.selectList(repertory_wrapper);
            for(ProductRepertory pr : productRepertories){
                if(!StringUtil.isEmpty(pr.getProductImg())){
                    pr.setProductImg(systemImageUrl+pr.getProductImg());
                }
                if(!StringUtil.isEmpty(pr.getProductPic())){
                    pr.setProductPic(systemImageUrl+pr.getProductPic());
                }
                //公司名称
                ProductCompany company = new ProductCompany();
                company.setCompanyCode(pr.getCompanyCode());
                ProductCompany companyDb = productCompanyMapper.selectOne(company);
                if(companyDb!=null){
                    if(companyDb.getCompanyLogo()!=null){
                        pr.setCompanyLogo(systemImageUrl+companyDb.getCompanyLogo());
                    }
                    pr.setCompanyName(companyDb.getCompanyName());
                }
                //阅读
                ProductAnalysis ana = new ProductAnalysis();
                ana.setProductId(pr.getProductId());
                ProductAnalysis productAnalysis = productAnalysisMapper.selectOne(ana);
                if(productAnalysis!=null){
                    pr.setReadNum(productAnalysis.getReadNum()+"");
                }else{
                    pr.setReadNum("0");
                }
            }
            category.setProductRepertories(productRepertories);
        }
        page.setRecords(reterporyCategories);
        return new Result<Page<ReterporyCategory>>(Code.SUCCESS, "获取数据成功!", page, Code.IS_ALERT_NO);
    }

    private List<ReterporyCategory> getCategoryByCom(Page<ReterporyCategory> page,EntityWrapper<ReterporyCategory> wrapper,String companyCode) {
        EntityWrapper<ProductRepertory> productRepertoryEntityWrapper = new EntityWrapper<>();
        productRepertoryEntityWrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        productRepertoryEntityWrapper.eq("company_code",companyCode);
        List<ProductRepertory> productRepertories = productRepertoryMapper.selectList(productRepertoryEntityWrapper);
        Set<String> ids = new HashSet<>();
        for(ProductRepertory bean : productRepertories){
            if(!StringUtil.isEmpty(bean.getCategoryId())){
                if(bean.getCategoryId().contains(",")){
                    String[] categoryIds = bean.getCategoryId().split(",");
                    for(String id : categoryIds){
                        if(!StringUtil.isEmpty(id)){
                            ids.add(id);
                        }
                    }
                }else{
                    ids.add(bean.getCategoryId().replace(",",""));
                }
            }
        }
        wrapper.in("id",ids);
        if(ids.size()==0)return  new ArrayList<>();
        List<ReterporyCategory> reterporyCategories = reterporyCategoryMapper.selectPage(page, wrapper);
        return reterporyCategories;
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
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="categoryId")String categoryId
    ) {
        if (offset == null) offset = 1;
        if (limit == null) limit = 10;
        if(StringUtil.isEmpty(categoryId)){
            return new Result<Page<ProductRepertory>>(Code.FAIL, "分类ID不能为空!", null, Code.IS_ALERT_YES);
        }
        //构造返回值
        Page<ProductRepertory> productRepertoryPage = new Page(1,2);
        EntityWrapper<ProductRepertory> repertory_wrapper = new EntityWrapper();
        repertory_wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        repertory_wrapper.like(true,"category_id",categoryId);
        productRepertoryPage.setOrderByField("creator_time"); // 排序参数
        productRepertoryPage.setAsc(false); // 为true表示顺序排列，false为倒序排列
        List<ProductRepertory> productRepertories = productRepertoryMapper.selectList(repertory_wrapper);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for(ProductRepertory pr : productRepertories){
            if(!StringUtil.isEmpty(pr.getProductImg())){
                pr.setProductImg(systemImageUrl+pr.getProductImg());
            }
            if(!StringUtil.isEmpty(pr.getProductPic())){
                pr.setProductPic(systemImageUrl+pr.getProductPic());
            }

            //公司名称
            ProductCompany company = new ProductCompany();
            company.setCompanyCode(pr.getCompanyCode());
            ProductCompany companyDb = productCompanyMapper.selectOne(company);
            if(companyDb!=null){
                if(companyDb.getCompanyLogo()!=null){
                    pr.setCompanyLogo(systemImageUrl+companyDb.getCompanyLogo());
                }
                pr.setCompanyName(companyDb.getCompanyName());
            }
            //阅读
            ProductAnalysis ana = new ProductAnalysis();
            ana.setProductId(pr.getProductId());
            ProductAnalysis productAnalysis = productAnalysisMapper.selectOne(ana);
            if(productAnalysis!=null){
                pr.setReadNum(productAnalysis.getReadNum()+"");
            }else{
                pr.setReadNum("0");
            }

        }
        productRepertoryPage.setRecords(productRepertories);
        return new Result<Page<ProductRepertory>>(Code.SUCCESS, "获取数据成功!", productRepertoryPage, Code.IS_ALERT_NO);
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
            @ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode")String companyCode
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="category_id")String category_id
    ) {
        int offset = 1;
        int limit = 10;
        //构造返回值
        Page<ProductRepertory> productRepertoryPage = new Page(1,10);
        EntityWrapper<ProductRepertory> repertory_wrapper = new EntityWrapper();
        repertory_wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
//        if(!StringUtil.isEmpty(companyCode)){
//            repertory_wrapper.eq("company_code",companyCode);
//        }else{
//            if(!StringUtil.isEmpty(keyWord)){
//                repertory_wrapper.like("product_name",keyWord);
//            }else{
//                return new Result<List<ReterporyCategory>>(Code.FAIL, "关键字不能为空!", null, Code.IS_ALERT_YES);
//            }
//        }
        if(StringUtil.isEmpty(keyWord) && StringUtil.isEmpty(companyCode)  && StringUtil.isEmpty(category_id)){
            return new Result<List<ReterporyCategory>>(Code.FAIL, "关键字不能为空!", null, Code.IS_ALERT_YES);
        }
        if(!StringUtil.isEmpty(keyWord)){
            repertory_wrapper.like("product_name",keyWord);
        }
        if(!StringUtil.isEmpty(companyCode)){
            repertory_wrapper.eq("company_code",companyCode);
        }
        if(!StringUtil.isEmpty(category_id)){
            repertory_wrapper.like("category_id",category_id);
        }
        productRepertoryPage.setOrderByField("creator_time"); // 排序参数
        productRepertoryPage.setAsc(false); // 为true表示顺序排列，false为倒序排列
        List<ProductRepertory> productRepertories = productRepertoryMapper.selectList(repertory_wrapper);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        Set<String> set = new HashSet<>();
        for(ProductRepertory pr : productRepertories){
            if(!StringUtil.isEmpty(pr.getProductImg())){
                pr.setProductImg(systemImageUrl+pr.getProductImg());
            }
            if(!StringUtil.isEmpty(pr.getProductPic())){
                pr.setProductPic(systemImageUrl+pr.getProductPic());
            }

            //公司名称
            ProductCompany company = new ProductCompany();
            company.setCompanyCode(pr.getCompanyCode());
            ProductCompany companyDb = productCompanyMapper.selectOne(company);
            if(companyDb!=null){
                if(companyDb.getCompanyLogo()!=null){
                    pr.setCompanyLogo(systemImageUrl+companyDb.getCompanyLogo());
                }
                pr.setCompanyName(companyDb.getCompanyName());
            }
            //阅读
            ProductAnalysis ana = new ProductAnalysis();
            ana.setProductId(pr.getProductId());
            ProductAnalysis productAnalysis = productAnalysisMapper.selectOne(ana);
            if(productAnalysis!=null){
                pr.setReadNum(productAnalysis.getReadNum()+"");
            }else{
                pr.setReadNum("0");
            }

            if(!StringUtil.isEmpty(pr.getCategoryId())){
                String[] split = pr.getCategoryId().split(",");
                for(String s : split){
                    if(!StringUtil.isEmpty(s)){
                        set.add(s);
                    }
                }
            }
        }
        productRepertoryPage.setRecords(productRepertories);
        //重新组装数据
        List<ReterporyCategory> rcs = new ArrayList<>();
        for(String s : set){
            ReterporyCategory category = new ReterporyCategory();
            category.setId(s);
            category.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            ReterporyCategory categoryDb = reterporyCategoryMapper.selectOne(category);
            if(categoryDb!=null){
                categoryDb.setProductRepertories(getPrsByCategoryId(productRepertories,s));
                rcs.add(categoryDb);
            }
        }
        return new Result<List<ReterporyCategory>>(Code.SUCCESS, "获取数据成功!", rcs, Code.IS_ALERT_NO);
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
            ,@ApiParam(value = "关键字",required = false)@RequestParam(value="keyWord")String keyWord
            ,@ApiParam(value = "公司编码",required = false)@RequestParam(value="companyCode")String companyCode
            ,@ApiParam(value = "分类ID",required = false)@RequestParam(value="category_id")String category_id
    ) {
        //构造返回值
        Page<ProductRepertory> productRepertoryPage = new Page(offset,limit);
        EntityWrapper<ProductRepertory> repertory_wrapper = new EntityWrapper();
        repertory_wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        if(!StringUtil.isEmpty(keyWord)){
            repertory_wrapper.like("product_name",keyWord);
        }
        if(!StringUtil.isEmpty(companyCode)){
            repertory_wrapper.eq("company_code",companyCode);
        }
        if(!StringUtil.isEmpty(category_id)){
            repertory_wrapper.like("category_id",category_id);
        }
        //repertory_wrapper.setSqlSelect("order by read_num , creator_time desc");
        repertory_wrapper.orderBy("read_num",false);
        repertory_wrapper.orderBy("creator_time",false);
        List<ProductRepertory> productRepertories = productRepertoryMapper.selectPage(productRepertoryPage,repertory_wrapper);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        Set<String> set = new HashSet<>();
        for(ProductRepertory pr : productRepertories){
            if(!StringUtil.isEmpty(pr.getProductImg())){
                pr.setProductImg(systemImageUrl+pr.getProductImg());
            }
            if(!StringUtil.isEmpty(pr.getProductPic())){
                pr.setProductPic(systemImageUrl+pr.getProductPic());
            }

            //公司名称
            ProductCompany company = new ProductCompany();
            company.setCompanyCode(pr.getCompanyCode());
            ProductCompany companyDb = productCompanyMapper.selectOne(company);
            if(companyDb!=null){
                if(companyDb.getCompanyLogo()!=null){
                    pr.setCompanyLogo(systemImageUrl+companyDb.getCompanyLogo());
                }
                pr.setCompanyName(companyDb.getCompanyName());
            }
            //阅读
            ProductAnalysis ana = new ProductAnalysis();
            ana.setProductId(pr.getProductId());
            ProductAnalysis productAnalysis = productAnalysisMapper.selectOne(ana);
            if(productAnalysis!=null){
                //pr.setReadNum(productAnalysis.getReadNum()+"");
                pr.setHasAna(true);
            }else{
               // pr.setReadNum("0");
                pr.setHasAna(false);
            }


            if(!StringUtil.isEmpty(pr.getCategoryId())){
                String[] split = pr.getCategoryId().split(",");
                for(String s : split){
                    if(!StringUtil.isEmpty(s)){
                        set.add(s);
                    }
                }
            }
        }
        productRepertoryPage.setRecords(productRepertories);
        return new Result<List<ProductRepertory>>(Code.SUCCESS, "获取数据成功!", productRepertories, Code.IS_ALERT_NO);
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
        EntityWrapper<ReterporyCategory> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<ReterporyCategory> reterporyCategories = reterporyCategoryMapper.selectList(wrapper);
        return new Result<List<ReterporyCategory>>(Code.SUCCESS, "获取数据成功!", reterporyCategories, Code.IS_ALERT_NO);
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
            @ApiParam(value = "产品库ID",required = false)@RequestParam(value="productId")String productId
            ,@ApiParam(value = "令牌",required = false)@RequestParam(value="token",defaultValue = "")String token
    ) {
        if(StringUtil.isEmpty(productId)){
            return new  Result<Map<String,Object>>(Code.FAIL, "产品库ID不能为空!", null, Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        //构造返回值
        ProductRepertory productRepertory = new ProductRepertory();
        productRepertory.setProductId(productId);
        ProductRepertory productRepertoryDb = productRepertoryMapper.selectOne(productRepertory);

        if(productRepertoryDb==null){
            return new  Result<Map<String,Object>>(Code.FAIL, "无此产品信息!", null, Code.IS_ALERT_YES);
        }

        int readNum = Integer.parseInt(productRepertoryDb.getReadNum())+1;
        productRepertoryDb.setReadNum(readNum+"");
        Integer integer = productRepertoryMapper.updateById(productRepertoryDb);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        if(!StringUtil.isEmpty(productRepertoryDb.getProductImg())){
            productRepertoryDb.setProductImg(systemImageUrl+productRepertoryDb.getProductImg());
        }
        if(!StringUtil.isEmpty(productRepertoryDb.getProductPic())){
            productRepertoryDb.setProductPic(systemImageUrl+productRepertoryDb.getProductPic());
        }

        //阅读
        ProductAnalysis ana = new ProductAnalysis();
        ana.setProductId(productId);
        ProductAnalysis productAnalysis = productAnalysisMapper.selectOne(ana);
        if(productAnalysis!=null){
            ///productRepertoryDb.setReadNum(productAnalysis.getReadNum()+"");
            productRepertoryDb.setHasAna(true);
        }else{
            //productRepertoryDb.setReadNum("0");
            productRepertoryDb.setHasAna(false);
        }

       if(!StringUtil.isEmpty(token)){
           String memberId = redisTemplateUtils.getStringValue(token);
           if(StringUtil.isEmpty(memberId)){
               map.put("isCollection",false);
           }else{
               Collection collection = new Collection();
               collection.setMemberId(memberId);
               collection.setBeCollectedId(productId);
               collection.setCollectionType(YxbConstants.COLLECTION_TYPE);
               Collection collectionDb = collectionMapper.selectOne(collection);
               map.put("isCollection",collectionDb!=null);
           }
       }else{
           map.put("isCollection",false);
       }

        //产品库
        map.put("productRepertory",productRepertoryDb);
        //产品分类
        map.put("category",getProductRepertoryCategory(productRepertoryDb.getCategoryId()));
        //产品概览
        map.put("productProtect",getProductProtect(systemImageUrl,productRepertoryDb.getProtectTag()));
        //产品条款
        map.put("productProtocol",getProductProtocol(systemImageUrl,productId));
        //产品计划
        map.put("productPlan",getProductPlan(systemImageUrl,productId));
        return new Result<Map<String,Object>>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
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
        if(StringUtil.isEmpty(productId)){
            return new  Result<ProductAnalysis>(Code.FAIL, "产品库ID不能为空!", null, Code.IS_ALERT_YES);
        }
        ProductAnalysis productAnalysis = new ProductAnalysis();
        productAnalysis.setProductId(productId);
        productAnalysis.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        ProductAnalysis productAnalysisDb = productAnalysisMapper.selectOne(productAnalysis);
        if(productAnalysisDb==null){
            return new Result<ProductAnalysis>(Code.FAIL, "获取数据失败!暂无相关解读信息!", null, Code.IS_ALERT_YES);
        }
       try{
           productAnalysisDb.setReadNum(productAnalysisDb.getReadNum()+1);
           productAnalysisMapper.updateAllColumnById(productAnalysisDb);
       }catch (Exception e){
            e.printStackTrace();
       }
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        if(!StringUtil.isEmpty(productAnalysisDb.getImg())){
            productAnalysisDb.setImg(systemImageUrl+productAnalysisDb.getImg());
        }
        if(!StringUtil.isEmpty(productAnalysisDb.getFilePath())){
            productAnalysisDb.setFilePath(systemImageUrl+productAnalysisDb.getFilePath());
        }

        EntityWrapper<Like> wrapper = new EntityWrapper<>();
        wrapper.eq("be_liked_id",productAnalysisDb.getProductId());
        wrapper.eq("like_type",YxbConstants.COLLECTION_TYPE);
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);

        StaffInfo staffInfo = new StaffInfo();
        staffInfo.setStaffId(productAnalysisDb.getCreatorId());
        StaffInfo staffInfoDb = staffInfoMapper.selectOne(staffInfo);
        if(staffInfoDb!=null){
            productAnalysisDb.setNickName(staffInfoDb.getNickName());
            productAnalysisDb.setHeadImg(systemImageUrl+staffInfoDb.getHeadImg());
        }else{
            productAnalysisDb.setNickName(null);
            productAnalysisDb.setHeadImg(null);
        }

        Integer count = likeMapper.selectCount(wrapper);
        productAnalysisDb.setPraiseNum(count+"");

        return new Result<ProductAnalysis>(Code.SUCCESS, "获取数据成功!", productAnalysisDb, Code.IS_ALERT_NO);
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
        EntityWrapper<ProductCompany> wrapper = new EntityWrapper<>();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<ProductCompany> productCompanies = productCompanyMapper.selectList(wrapper);

        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for(ProductCompany c : productCompanies){
            if(!StringUtil.isEmpty(c.getCompanyLogo())){
                c.setCompanyLogo(systemImageUrl+c.getCompanyLogo());
            }
        }



        return new Result<List<ProductCompany>>(Code.SUCCESS, "获取数据成功!", productCompanies, Code.IS_ALERT_NO);
    }


    private List<ProductRepertory> getPrsByCategoryId(List<ProductRepertory> productRepertories, String s) {
        List<ProductRepertory> list = new ArrayList<>();
        for(ProductRepertory pr:productRepertories){
            if(!StringUtil.isEmpty(pr.getCategoryId())){
                if(pr.getCategoryId().contains(s)){
                    list.add(pr);
                }
            }
        }
        return list;
    }

    private  List<ProductPlan> getProductPlan(String systemImageUrl,String productId) {
        List<ProductPlan> list = new ArrayList<>();
        if(StringUtil.isEmpty(productId)){
            return list;
        }
        EntityWrapper<ProductPlan> wrapper = new EntityWrapper<>();
        wrapper.eq("product_id",productId);
        List<ProductPlan> productPlans = productPlanMapper.selectList(wrapper);
        for(ProductPlan pp : productPlans){
            if(!StringUtil.isEmpty(pp.getDescPic())){
                pp.setDescPic(systemImageUrl+pp.getDescPic());
            }
        }
        return productPlans;
    }

    private  List<ProductProtocol> getProductProtocol(String systemImageUrl,String productId) {
        List<ProductProtocol> list = new ArrayList<>();
        if(StringUtil.isEmpty(productId)){
            return list;
        }
        EntityWrapper<ProductProtocol> wrapper = new EntityWrapper<>();
        wrapper.eq("product_id",productId);
        List<ProductProtocol> productProtocols = productProtocolMapper.selectList(wrapper);
        for(ProductProtocol pp : productProtocols){
            if(!StringUtil.isEmpty(pp.getFile())){
                pp.setFile(systemImageUrl+pp.getFile());
            }
        }
        return productProtocols;
    }

    private List<ImgInfo> getProductProtect(String systemImageUrl,String protectTag) {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtil.isEmpty(protectTag) || StringUtil.isEmpty(protectTag.trim())){
            return list;
        }
        EntityWrapper<ImgInfo> wrapper = new EntityWrapper<>();
        wrapper.in("id",protectTag);
        List<ImgInfo> imgInfos = imgInfoMapper.selectList(wrapper);
        for(ImgInfo imgInfo :imgInfos){
            if(!StringUtil.isEmpty(imgInfo.getImgUrl())){
                imgInfo.setImgUrl(systemImageUrl+imgInfo.getImgUrl());
            }
        }
        return imgInfos;
    }

    private List<ReterporyCategory> getProductRepertoryCategory(String categoryId) {
        List<ReterporyCategory> list = new ArrayList<>();
        if(StringUtil.isEmpty(categoryId) || StringUtil.isEmpty(categoryId.trim())){
            return list;
        }
        EntityWrapper<ReterporyCategory> wrapper = new EntityWrapper<>();
        wrapper.in("id",categoryId);
        return reterporyCategoryMapper.selectList(wrapper);

    }


}
