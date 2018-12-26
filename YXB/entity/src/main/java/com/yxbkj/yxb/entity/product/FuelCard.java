package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 加油卡表
 * </p>
 *
 * @author ZY
 * @since 2018-12-14
 */
@TableName("yxb_fuel_card")
public class FuelCard extends Model<FuelCard> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 会员手机号
     */
    private String memberPhone;
    /**
     * 会员姓名
     */
    private String memberName;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 加油卡卡号
     */
    private String cardNumber;
    /**
     * 加油卡分类
     */
    private String cardType;
    /**
     * 数据有效性
     */
    private String validity;
    /**
     * 创建时间
     */
    private String creatorTime;

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 是否默认

     */
    private String isDefault;
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

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
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
        return "FuelCard{" +
                "id='" + id + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", validity='" + validity + '\'' +
                ", creatorTime='" + creatorTime + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", remark='" + remark + '\'' +
                ", ext1='" + ext1 + '\'' +
                ", ext2=" + ext2 +
                ", ext3=" + ext3 +
                ", ext4='" + ext4 + '\'' +
                '}';
    }
}
