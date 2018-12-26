package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
@TableName("yxb_prize_record")
public class PrizeRecord extends Model<PrizeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 中奖人id
     */
	private String memberId;
	private String prizeId;
    /**
     * 活动编号
     */
	private String activityNo;
    /**
     * 获奖数量
     */
	private Integer prizeNum;
    /**
     * 获奖时间
     */
	private String prizeTime;
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
     * 备注
     */
	private String remark;

	@TableField(exist = false)
	private String contacts;
	@TableField(exist = false)
	private Long contactsPhone;
	@TableField(exist = false)
	private String address;
	@TableField(exist = false)
	private String prizeType;

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

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	public Integer getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}

	public String getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(String prizeTime) {
		this.prizeTime = prizeTime;
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


	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Long getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(Long contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
