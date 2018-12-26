package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 理财产品表
 * </p>
 *
 * @author 李明
 * @since 2018-07-17
 */
@TableName("yxb_product_investment")
public class ProductInvestment extends Model<ProductInvestment> {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 历史年化%
	 */
	private BigDecimal fundApr;
	/**
	 * 标的金额
	 */
	private BigDecimal fundAccount;
	/**
	 * 已投金额
	 */
	private BigDecimal fundAccountTotal;
	/**
	 * 标的期限
	 */
	private String fundPeriod;
	/**
	 * 还款方式：1 先息期本 2 先息后本 3 到期还本息 4 等额本息
	 */
	private String repaymentType;
	/**
	 * 标的类型 1金车贷2金房贷3保中宝4步步高
	 */
	private String fundType;
	/**
	 * 复审及起息时间
	 */
	private String valuedateTime;
	/**
	 * 发标及初审时间
	 */
	private String biddingTime;
	/**
	 * 标的完成率
	 */
	private BigDecimal fundFinishrate;
	/**
	 * 第三方产品ID
	 */
	private String otherPid;

	public String getOtherPid() {
		return otherPid;
	}

	public void setOtherPid(String otherPid) {
		this.otherPid = otherPid;
	}

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

	public BigDecimal getFundApr() {
		return fundApr;
	}

	public void setFundApr(BigDecimal fundApr) {
		this.fundApr = fundApr;
	}

	public BigDecimal getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(BigDecimal fundAccount) {
		this.fundAccount = fundAccount;
	}

	public BigDecimal getFundAccountTotal() {
		return fundAccountTotal;
	}

	public void setFundAccountTotal(BigDecimal fundAccountTotal) {
		this.fundAccountTotal = fundAccountTotal;
	}

	public String getFundPeriod() {
		return fundPeriod;
	}

	public void setFundPeriod(String fundPeriod) {
		this.fundPeriod = fundPeriod;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getValuedateTime() {
		return valuedateTime;
	}

	public void setValuedateTime(String valuedateTime) {
		this.valuedateTime = valuedateTime;
	}

	public String getBiddingTime() {
		return biddingTime;
	}

	public void setBiddingTime(String biddingTime) {
		this.biddingTime = biddingTime;
	}

	public BigDecimal getFundFinishrate() {
		return fundFinishrate;
	}

	public void setFundFinishrate(BigDecimal fundFinishrate) {
		this.fundFinishrate = fundFinishrate;
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
