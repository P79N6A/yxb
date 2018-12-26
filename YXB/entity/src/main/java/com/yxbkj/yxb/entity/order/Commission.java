package com.yxbkj.yxb.entity.order;



/**
 * <p>
 * 是否结算
 * </p>
 *
 * @author tangqi
 * @since 2018-09-12
 */
public class Commission {
    private String id;
    private String orderId;
    private String status;
    private String fixedAmount;
    private String orderType;
    private String commissionType;
    private String waitTime;
    private String orderMemberName;
    private String creatorTime;
    private String productName;
    private String money;
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(String fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getOrderMemberName() {
        return orderMemberName;
    }

    public void setOrderMemberName(String orderMemberName) {
        this.orderMemberName = orderMemberName;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
