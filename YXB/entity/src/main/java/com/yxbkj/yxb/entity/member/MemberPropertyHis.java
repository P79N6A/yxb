package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 会员资产信息历史表
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@TableName("yxb_member_property_his")
public class MemberPropertyHis extends Model<MemberPropertyHis> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 会员ID
     */
	private String memberId;
    /**
     * 总收入
     */
	private BigDecimal propertyAmount;
    /**
     * 冻结金额
     */
	private BigDecimal frozenAmount;
    /**
     * 可用金额
     */
	private BigDecimal availableAmount;
    /**
     * 已提金额
     */
	private BigDecimal submittedAmount;
    /**
     * 易豆
     */
	private Integer ebean;
    /**
     * 修改时间
     */
	private String modifierTime;
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

	public BigDecimal getPropertyAmount() {
		return propertyAmount;
	}

	public void setPropertyAmount(BigDecimal propertyAmount) {
		this.propertyAmount = propertyAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getSubmittedAmount() {
		return submittedAmount;
	}

	public void setSubmittedAmount(BigDecimal submittedAmount) {
		this.submittedAmount = submittedAmount;
	}

	public Integer getEbean() {
		return ebean;
	}

	public void setEbean(Integer ebean) {
		this.ebean = ebean;
	}

	public String getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(String modifierTime) {
		this.modifierTime = modifierTime;
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
