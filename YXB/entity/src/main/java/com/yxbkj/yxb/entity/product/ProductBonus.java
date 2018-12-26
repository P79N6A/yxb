package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

@TableName("yxb_product_bonus")
public class ProductBonus extends Model<ProductBonus> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 会员等级
     */
    private String memberLevel;
    /**
     * 推广加佣
     */
    private BigDecimal spreadRate;
    /**
     * 推广固定金额
     */
    private BigDecimal spreadFixedAmount;
    /**
     * 一级分享加佣
     */
    private BigDecimal oneRate;
    /**
     * 一级固定金额
     */
    private BigDecimal oneFixedAmount;
    /**
     * 二级分享加佣
     */
    private BigDecimal twoRate;
    /**
     * 二级固定金额
     */
    private BigDecimal twoFixedAmount;
    /**
     * 加佣起时间
     */
    private String startTime;
    /**
     * 加佣止时间
     */
    private String endTime;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public BigDecimal getSpreadRate() {
        return spreadRate;
    }

    public void setSpreadRate(BigDecimal spreadRate) {
        this.spreadRate = spreadRate;
    }

    public BigDecimal getSpreadFixedAmount() {
        return spreadFixedAmount;
    }

    public void setSpreadFixedAmount(BigDecimal spreadFixedAmount) {
        this.spreadFixedAmount = spreadFixedAmount;
    }

    public BigDecimal getOneRate() {
        return oneRate;
    }

    public void setOneRate(BigDecimal oneRate) {
        this.oneRate = oneRate;
    }

    public BigDecimal getOneFixedAmount() {
        return oneFixedAmount;
    }

    public void setOneFixedAmount(BigDecimal oneFixedAmount) {
        this.oneFixedAmount = oneFixedAmount;
    }

    public BigDecimal getTwoRate() {
        return twoRate;
    }

    public void setTwoRate(BigDecimal twoRate) {
        this.twoRate = twoRate;
    }

    public BigDecimal getTwoFixedAmount() {
        return twoFixedAmount;
    }

    public void setTwoFixedAmount(BigDecimal twoFixedAmount) {
        this.twoFixedAmount = twoFixedAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

}
