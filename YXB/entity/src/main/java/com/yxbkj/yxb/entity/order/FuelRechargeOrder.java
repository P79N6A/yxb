package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 充值订单
 * </p>
 *
 * @author ZY
 * @since 2018-12-14
 */
@TableName("yxb_fuel_recharge_order")
public class FuelRechargeOrder extends Model<FuelRechargeOrder> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 支付订单号
     */
    private String payOrderId;
    /**
     * 订单号
     */
    private String rechargeOrderId;
    /**
     * 加油卡/手机
     */
    private String orderType;
    /**
     * 到账日期yyyy-MM-dd
     */
    private String arrivalDate;
    /**
     * 19位号码 100011/9开头
     */
    private String fuelNumber;
    /**
     * 加油卡/手机
     */
    private String orderSource;
    /**
     * 下单会员ID
     */
    private String orderMemberId;
    /**
     * 下单会员姓名
     */
    private String orderMemberName;
    /**
     * 当前第几个月充值
     */
    private Long time;
    /**
     * 下单会员手机
     */
    private Long orderMemberPhone;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 未支付/已支付
     */
    private String payStatus;
    /**
     * 未出单/已出单/退单
     */
    private String orderStatus;
    /**
     * 现金/微信/支付宝/壹支付/连连支付/众安/700度
     */
    private String payType;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 创建人ID
     */
    private String creatorId;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    private String creatorTime;
    /**
     * 创建IP地址
     */
    private String creatorIp;
    /**
     * 修改人ID
     */
    private String modifierId;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private String modifierTime;
    /**
     * 修改IP地址
     */
    private String modifierIp;
    /**
     * 数据有效性
     */
    private String validity;
    /**
     * 备注
     */
    private String remark;
    /**
     * EXT1
     */
    private String ext1;
    /**
     * EXT2
     */
    private Long ext2;
    /**
     * EXT3
     */
    private BigDecimal ext3;
    /**
     * EXT4
     */
    private String ext4;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getRechargeOrderId() {
        return rechargeOrderId;
    }

    public void setRechargeOrderId(String rechargeOrderId) {
        this.rechargeOrderId = rechargeOrderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getFuelNumber() {
        return fuelNumber;
    }

    public void setFuelNumber(String fuelNumber) {
        this.fuelNumber = fuelNumber;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderMemberId() {
        return orderMemberId;
    }

    public void setOrderMemberId(String orderMemberId) {
        this.orderMemberId = orderMemberId;
    }

    public String getOrderMemberName() {
        return orderMemberName;
    }

    public void setOrderMemberName(String orderMemberName) {
        this.orderMemberName = orderMemberName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getOrderMemberPhone() {
        return orderMemberPhone;
    }

    public void setOrderMemberPhone(Long orderMemberPhone) {
        this.orderMemberPhone = orderMemberPhone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getCreatorIp() {
        return creatorIp;
    }

    public void setCreatorIp(String creatorIp) {
        this.creatorIp = creatorIp;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifierTime() {
        return modifierTime;
    }

    public void setModifierTime(String modifierTime) {
        this.modifierTime = modifierTime;
    }

    public String getModifierIp() {
        return modifierIp;
    }

    public void setModifierIp(String modifierIp) {
        this.modifierIp = modifierIp;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public Long getExt2() {
        return ext2;
    }

    public void setExt2(Long ext2) {
        this.ext2 = ext2;
    }

    public BigDecimal getExt3() {
        return ext3;
    }

    public void setExt3(BigDecimal ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FuelRechargeOrder{" +
                ", id=" + id +
                ", payOrderId=" + payOrderId +
                ", rechargeOrderId=" + rechargeOrderId +
                ", orderType=" + orderType +
                ", arrivalDate=" + arrivalDate +
                ", fuelNumber=" + fuelNumber +
                ", orderSource=" + orderSource +
                ", orderMemberId=" + orderMemberId +
                ", orderMemberName=" + orderMemberName +
                ", time=" + time +
                ", orderMemberPhone=" + orderMemberPhone +
                ", amount=" + amount +
                ", payStatus=" + payStatus +
                ", orderStatus=" + orderStatus +
                ", payType=" + payType +
                ", productId=" + productId +
                ", creatorId=" + creatorId +
                ", creator=" + creator +
                ", creatorTime=" + creatorTime +
                ", creatorIp=" + creatorIp +
                ", modifierId=" + modifierId +
                ", modifier=" + modifier +
                ", modifierTime=" + modifierTime +
                ", modifierIp=" + modifierIp +
                ", validity=" + validity +
                ", remark=" + remark +
                ", ext1=" + ext1 +
                ", ext2=" + ext2 +
                ", ext3=" + ext3 +
                ", ext4=" + ext4 +
                "}";
    }
}
