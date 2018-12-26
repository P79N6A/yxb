package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 非车险产品表
 * </p>
 *
 * @author 李明
 * @since 2018-07-17
 */
@TableName("yxb_product_protect")
public class ProductProtect extends Model<ProductProtect> {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 产品id
	 */
	private String productId;
	/**
	 * 适用人群
	 */
	private String crowd;
	/**
	 * 保障年龄
	 */
	private String protectAge;
	/**
	 * 保障期限
	 */
	private String protectTime;
	/**
	 * 保单形式
	 */
	private String policyForm;
	/**
	 * 保额
	 */
	private BigDecimal coverage;
	/**
	 * 保险责任
	 */
	private String duty;
	/**
	 * 产品优势
	 */
	private String superiority;
	/**
	 * 投保须知
	 */
	private String notice;
	/**
	 * 最大认购份数
	 */
	private String maxNum;
	/**
	 * 产品缩略介绍
	 */
	private String briefIntroduction;
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

	public String getCrowd() {
		return crowd;
	}

	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}

	public String getProtectAge() {
		return protectAge;
	}

	public void setProtectAge(String protectAge) {
		this.protectAge = protectAge;
	}

	public String getProtectTime() {
		return protectTime;
	}

	public void setProtectTime(String protectTime) {
		this.protectTime = protectTime;
	}

	public String getPolicyForm() {
		return policyForm;
	}

	public void setPolicyForm(String policyForm) {
		this.policyForm = policyForm;
	}

	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getSuperiority() {
		return superiority;
	}

	public void setSuperiority(String superiority) {
		this.superiority = superiority;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
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
