package com.yxbkj.yxb.product.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.product.ProtectCard;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CardMapper extends BaseMapper<ProtectCard> {
    Map<String, Object> insertCard( @Param("productId") String productId,
                                    @Param("amount") BigDecimal amount,
                                    @Param("token") String token,
                                    @Param("policyHolder") String policyHolder,
                                    @Param("policyCard") String policyCard,
                                    @Param("plateNumber") String plateNumber,
                                    @Param("chassisNumber") String chassisNumber,
                                    @Param("policyPhone") String policyPhone,
                                    @Param("source")String source,
                                    @Param("number") String number);

}
