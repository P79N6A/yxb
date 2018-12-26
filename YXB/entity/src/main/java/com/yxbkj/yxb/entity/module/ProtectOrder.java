package com.yxbkj.yxb.entity.module;

import java.math.BigDecimal;

public class ProtectOrder {
    private String orderSource;
    private String orderId;
    private String orderMemberName;
    private String orderMemberPhone;
    private String protectCard;
    private String policyNum;
    private BigDecimal amount;
    private String orderStatus;
    private String policyHolder;
    private String protectHolder;
    private String productName;
    private String companyLogo;
    private String payStatus;
    private String creatorTime;
    private String policyUrl;

    public String getPolicyUrl() {
        return policyUrl;
    }

    public void setPolicyUrl(String policyUrl) {
        this.policyUrl = policyUrl;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderMemberName() {
        return orderMemberName;
    }

    public void setOrderMemberName(String orderMemberName) {
        this.orderMemberName = orderMemberName;
    }

    public String getOrderMemberPhone() {
        return orderMemberPhone;
    }

    public void setOrderMemberPhone(String orderMemberPhone) {
        this.orderMemberPhone = orderMemberPhone;
    }

    public String getProtectCard() {
        return protectCard;
    }

    public void setProtectCard(String protectCard) {
        this.protectCard = protectCard;
    }

    public String getPolicyNum() {
        return policyNum;
    }

    public void setPolicyNum(String policyNum) {
        this.policyNum = policyNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    public String getProtectHolder() {
        return protectHolder;
    }

    public void setProtectHolder(String protectHolder) {
        this.protectHolder = protectHolder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }
}
