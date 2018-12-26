package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 非车险订单表
 * </p>
 *
 * @author 李明
 * @since 2018-08-22
 */
@TableName("yxb_order_protect")
public class OrderProtect extends Model<OrderProtect> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 订单号
     */
	private String orderId;
	private String policyHolder;
	private String policyCard;
	private String protectHolder;
	private String protectCard;
	private String policyNum;
    /**
     * 备注
     */
	private String remark;
	private String policyUrl;
	private String bankNo;
	private String bankAccountNo;
	private String bankAccountName;
	private String policyPhone;
	private String policyEmail;
	private String protectPhone;
	private String protectEmail;
	private String policyNo;
	private String plateNumber;
	private String chassisNumber;
	private String number;
	private String cardNo;
	private String cardPwd;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
	}

	public String getPolicyCard() {
		return policyCard;
	}

	public void setPolicyCard(String policyCard) {
		this.policyCard = policyCard;
	}

	public String getProtectHolder() {
		return protectHolder;
	}

	public void setProtectHolder(String protectHolder) {
		this.protectHolder = protectHolder;
	}

	public String getProtectCard() {
		return protectCard;
	}

	public void setProtectCard(String protectCard) {
		this.protectCard = protectCard;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPolicyUrl() {
		return policyUrl;
	}

	public void setPolicyUrl(String policyUrl) {
		this.policyUrl = policyUrl;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getPolicyPhone() {
		return policyPhone;
	}

	public void setPolicyPhone(String policyPhone) {
		this.policyPhone = policyPhone;
	}

	public String getPolicyEmail() {
		return policyEmail;
	}

	public void setPolicyEmail(String policyEmail) {
		this.policyEmail = policyEmail;
	}

	public String getProtectPhone() {
		return protectPhone;
	}

	public void setProtectPhone(String protectPhone) {
		this.protectPhone = protectPhone;
	}

	public String getProtectEmail() {
		return protectEmail;
	}

	public void setProtectEmail(String protectEmail) {
		this.protectEmail = protectEmail;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
