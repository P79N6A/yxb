package com.yxbkj.yxb.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.common.entity.YxbConstants;
import com.yxbkj.yxb.common.utils.Constants;
import com.yxbkj.yxb.common.utils.IpUtil;
import com.yxbkj.yxb.domain.mapper.ProductInfoMapper;
import com.yxbkj.yxb.domain.model.ProductInfo;
import com.yxbkj.yxb.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-07-10
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public Map<String, Object> selectProductInfoList(String productName, Integer current, Integer size){
        Wrapper<ProductInfo> wrapper=new EntityWrapper<ProductInfo>();
        if(productName!=null && !"".equals(productName)){
            wrapper.like("product_name",productName);
        }
        wrapper.eq("validity","10000001");
        Page<ProductInfo> page=new Page<ProductInfo>(current,size);
        List<ProductInfo> list = productInfoMapper.selectPage(page,wrapper);
        int total=productInfoMapper.selectCount(wrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("data",list);
        result.put("code",0);
        result.put("count",total);
        return result;
    }

    @Override
    public Map<String, Object> selectProductInfoList(ProductInfo entity) {
        Page<ProductInfo> page = new Page<ProductInfo>(entity.getPage(), entity.getLimit());
        Wrapper<ProductInfo> wrapper = entity.generateSearchWrapper();
        List<ProductInfo> list = productInfoMapper.selectPage(page, wrapper);
        int total = productInfoMapper.selectCount(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("code", 0);
        result.put("count", total);
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateProductInfoByIds(String ids, HttpServletRequest request) {
        String [] idlist=ids.split(",");
        int totol=0;
        for(int i=0;i<idlist.length;i++){
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            ProductInfo productInfo=new ProductInfo();
            productInfo.setId(idlist[i]);
            productInfo.setValidity(YxbConstants.DATA_DELETE_STATUS_CODE);
            productInfo.setCreatorTime(dateString);
            productInfo.setModifierIp(IpUtil.getAddrIP(request));
            Boolean t=this.updateById(productInfo);
            if(t){
                totol++;
            }
        }
        return totol;
    }
}
