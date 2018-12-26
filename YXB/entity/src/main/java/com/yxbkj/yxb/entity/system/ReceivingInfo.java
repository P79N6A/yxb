package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 收货信息表
 * </p>
 *
 * @author 李明
 * @since 2018-10-30
 */
@TableName("yxb_receiving_info")
public class ReceivingInfo extends Model<ReceivingInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 会员id
     */
	private String memberId;
	private String prizeRecordId;

    /**
     * 联系人
     */
	private String contacts;
    /**
     * 联系电话
     */
	private Long contactsPhone;
    /**
     * 收获地址
     */
	private String address;
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

	public String getPrizeRecordId() {
		return prizeRecordId;
	}

	public void setPrizeRecordId(String prizeRecordId) {
		this.prizeRecordId = prizeRecordId;
	}

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
