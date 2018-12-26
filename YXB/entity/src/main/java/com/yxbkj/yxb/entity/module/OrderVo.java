package com.yxbkj.yxb.entity.module;

import java.math.BigDecimal;

public class OrderVo {
    private String orderSource;
    private String orderId;
    private String orderMemberName;
    private String plateNumber;
    private String insuranceTimeStart;
    private String insuranceTimeEnd;
    private BigDecimal amount;
    private String orderStatus;
    private String productName;
    private String companyLogo;
    private String payStatus;

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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getInsuranceTimeStart() {
        return insuranceTimeStart;
    }

    public void setInsuranceTimeStart(String insuranceTimeStart) {
        this.insuranceTimeStart = insuranceTimeStart;
    }

    public String getInsuranceTimeEnd() {
        return insuranceTimeEnd;
    }

    public void setInsuranceTimeEnd(String insuranceTimeEnd) {
        this.insuranceTimeEnd = insuranceTimeEnd;
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
}
