package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 提现信息表
 * </p>
 *
 * @author 李明
 * @since 2018-08-27
 */
@TableName("yxb_cash_info")
public class CashInfo extends Model<CashInfo> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 会员ID
     */
	private String memberId;
    /**
     * 会员姓名
     */
	private String memberName;
    /**
     * 状态0待审核1提现成功2提现失败3审核中4撤销提现
     */
	private String cashStatus;
    /**
     * 银行卡号
     */
	private String bankCard;
    /**
     * 所属银行
     */
	private String bankName;
    /**
     * 所属省份
     */
	private String province;
    /**
     * 所属城市
     */
	private String city;
    /**
     * 银行支行
     */
	private String bankBranch;
    /**
     * 提现金额
     */
	private BigDecimal cashAmount;
    /**
     * 到账金额
     */
	private BigDecimal toAccountAmount;
    /**
     * 手续费
     */
	private BigDecimal cashFee;
    /**
     * 订单来源
     */
	private String orderSource;
    /**
     * 提现方式1人工打款
     */
	private String payType;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getToAccountAmount() {
		return toAccountAmount;
	}

	public void setToAccountAmount(BigDecimal toAccountAmount) {
		this.toAccountAmount = toAccountAmount;
	}

	public BigDecimal getCashFee() {
		return cashFee;
	}

	public void setCashFee(BigDecimal cashFee) {
		this.cashFee = cashFee;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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
