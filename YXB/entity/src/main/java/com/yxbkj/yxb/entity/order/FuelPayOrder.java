package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 支付订单
 * </p>
 *
 * @author ZY
 * @since 2018-12-14
 */
@TableName("yxb_fuel_pay_order")
public class FuelPayOrder extends Model<FuelPayOrder> {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 支付订单号
     */
    private String payOrderId;
    /**
     * 加油卡/手机
     */
    private String orderType;
    /**
     * 加油卡卡号
     */
    private String fuelNumber;
    /**
     * app/微信
     */
    private String orderSource;

    public BigDecimal getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(BigDecimal monthAmount) {
        this.monthAmount = monthAmount;
    }

    /**
     * 每月到账金额

     */
    private BigDecimal monthAmount;
    /**
     * 下单会员ID
     */
    private String orderMemberId;
    /**
     * 下单会员姓名
     */
    private String orderMemberName;
    /**
     * 下单会员手机
     */
    private Long orderMemberPhone;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 已支付/未支付
     */
    private String payStatus;
    /**
     * 到账总月数
     */
    private Long months;
    /**
     * 未出单/已出单/退单
     */
    private String orderStatus;
    /**
     * 现金/微信/支付宝/壹支付/连连支付/众安/700度
     */
    private String payType;

    @Override
    public String toString() {
        return "FuelPayOrder{" +
                "id='" + id + '\'' +
                ", payOrderId='" + payOrderId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", fuelNumber='" + fuelNumber + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", monthAmount=" + monthAmount +
                ", orderMemberId='" + orderMemberId + '\'' +
                ", orderMemberName='" + orderMemberName + '\'' +
                ", orderMemberPhone=" + orderMemberPhone +
                ", amount=" + amount +
                ", payStatus='" + payStatus + '\'' +
                ", months=" + months +
                ", orderStatus='" + orderStatus + '\'' +
                ", payType='" + payType + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorTime='" + creatorTime + '\'' +
                ", creatorIp='" + creatorIp + '\'' +
                ", modifierId='" + modifierId + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifierTime='" + modifierTime + '\'' +
                ", modifierIp='" + modifierIp + '\'' +
                ", commission_status='" + commission_status + '\'' +
                ", validity='" + validity + '\'' +
                ", remark='" + remark + '\'' +
                ", ext1='" + ext1 + '\'' +
                ", ext2=" + ext2 +
                ", ext3=" + ext3 +
                ", ext4='" + ext4 + '\'' +
                '}';
    }

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

    public String getCommission_status() {
        return commission_status;
    }

    public void setCommission_status(String commission_status) {
        this.commission_status = commission_status;
    }

    /**
     * 分佣状态

     */
    private String commission_status;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public Long getMonths() {
        return months;
    }

    public void setMonths(Long months) {
        this.months = months;
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


}
