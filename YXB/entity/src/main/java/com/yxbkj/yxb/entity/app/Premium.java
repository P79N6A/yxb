package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

@TableName("yxb_product_premium")

public class Premium {
    private String id;
    private String productId;
    private BigDecimal premium;
    private String validity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
