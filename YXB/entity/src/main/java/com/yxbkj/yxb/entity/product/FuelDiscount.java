package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 折扣表
 * </p>
 *
 * @author ZY
 * @since 2018-12-14
 */
@TableName("yxb_fuel_discount")
public class FuelDiscount extends Model<FuelDiscount> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 折扣id
     */
    private String discountId;
    /**
     * 加油卡/手机
     */
    private String discountType;
    /**
     * 到账总月数
     */
    private Integer months;
    /**
     * 折扣大小
     */
    private BigDecimal discountNum;
    /**
     * 数据有效性
     */
    private String validity;
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
     * 备注
     */
    private String remark;

    private BigDecimal costRate;
    private BigDecimal commissionRate;
    private Integer hesiPeriod;

    public BigDecimal getCostRate() {
        return costRate;
    }

    public void setCostRate(BigDecimal costRate) {
        this.costRate = costRate;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Integer getHesiPeriod() {
        return hesiPeriod;
    }

    public void setHesiPeriod(Integer hesiPeriod) {
        this.hesiPeriod = hesiPeriod;
    }

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

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public BigDecimal getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(BigDecimal discountNum) {
        this.discountNum = discountNum;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
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
        return "FuelDiscount{" +
                "id='" + id + '\'' +
                ", discountId='" + discountId + '\'' +
                ", discountType='" + discountType + '\'' +
                ", months=" + months +
                ", discountNum=" + discountNum +
                ", validity='" + validity + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorTime='" + creatorTime + '\'' +
                ", creatorIp='" + creatorIp + '\'' +
                ", modifierId='" + modifierId + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifierTime='" + modifierTime + '\'' +
                ", modifierIp='" + modifierIp + '\'' +
                ", remark='" + remark + '\'' +
                ", costRate=" + costRate +
                ", commissionRate=" + commissionRate +
                ", hesiPeriod=" + hesiPeriod +
                ", ext1='" + ext1 + '\'' +
                ", ext2=" + ext2 +
                ", ext3=" + ext3 +
                ", ext4='" + ext4 + '\'' +
                '}';
    }
}
