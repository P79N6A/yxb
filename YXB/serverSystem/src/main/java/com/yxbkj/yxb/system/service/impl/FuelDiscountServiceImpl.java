package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.FuelDiscount;
import com.yxbkj.yxb.system.mapper.FuelDiscountMapper;
import com.yxbkj.yxb.system.service.FuelDiscountService;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 折扣表 服务实现类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
@Service
public class FuelDiscountServiceImpl extends ServiceImpl<FuelDiscountMapper, FuelDiscount> implements FuelDiscountService {
    @Autowired
    private FuelDiscountMapper fuelDiscountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> incrDiscount(String discountType, Integer months, Double discountNum,Double costRate,Double commissionRate,Integer hesiPeriod) {
        Result<Map<String, Object>> result = null;
        if (discountType == null) {
            return result = new Result<>(Code.FAIL, "充值类型为空", Code.IS_ALERT_YES);
        }
        if (months == null) {
            return result = new Result<>(Code.FAIL, "到账总月数为空", Code.IS_ALERT_YES);
        }
        if (discountNum == null) {
            return result = new Result<>(Code.FAIL, "折扣大小为空", Code.IS_ALERT_YES);
        }
        String id = StringUtil.getUuid();
        String discountId = "DISC" + StringUtil.getCurrentDateStr();
        FuelDiscount fuelDiscount = new FuelDiscount();
        fuelDiscount.setId(id);
        fuelDiscount.setDiscountId(discountId);
        fuelDiscount.setDiscountType(discountType);
        fuelDiscount.setMonths(months);
        fuelDiscount.setCommissionRate(new BigDecimal(commissionRate + ""));
        fuelDiscount.setCostRate(new BigDecimal(costRate + ""));
        fuelDiscount.setHesiPeriod(hesiPeriod);
		
        fuelDiscount.setDiscountNum(new BigDecimal(discountNum.toString()));
        fuelDiscount.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelDiscount.setCreatorTime(StringUtil.getCurrentDateStr());
        Integer insert = fuelDiscountMapper.insert(fuelDiscount);
        Map<String, Object> map = new HashMap<>();
        if (insert <= 0) {
            return result = new Result<>(Code.FAIL, "添加失败", null, Code.IS_ALERT_YES);
        } else {
            map.put("insert", insert);
            return result = new Result<>(Code.SUCCESS, "添加成功", map, Code.IS_ALERT_YES);
        }
    }

    @Override
    public Result<List<FuelDiscount>> getDiscountList(String disCountType) {
        Result<List<FuelDiscount>> result = null;
        if (disCountType == null) {
            return result = new Result<>(Code.FAIL, "充值类型为空", new ArrayList<FuelDiscount>(), Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelDiscount> el = new EntityWrapper<>();
        el.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE).eq("discount_type", YxbConstants.FUELCARD).orderBy("months ASC");
        if (YxbConstants.FUELCARD.equals(disCountType)) {
            List<FuelDiscount> fuelDiscountList = fuelDiscountMapper.selectList(el);
            Map<String, Object> map1 = new HashMap<>();
            if (!fuelDiscountList.isEmpty()) {
                return result = new Result<>(Code.SUCCESS, "查询成功", fuelDiscountList, Code.IS_ALERT_NO);
            } else {
                return result = new Result<>(Code.SUCCESS, "查询失败", fuelDiscountList, Code.IS_ALERT_YES);
            }
        } else {
            return result = new Result<>(Code.FAIL, "类型不存在", new ArrayList<FuelDiscount>(), Code.IS_ALERT_YES);
        }
    }
}
