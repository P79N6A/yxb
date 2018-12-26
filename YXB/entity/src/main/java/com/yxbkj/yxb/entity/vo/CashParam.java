package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * <p>
 * 提现参数vo
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
public class CashParam extends BaseTokenVo implements Serializable {
	@ApiModelProperty(value = "提现金额")
	private String cashAmount;
	@ApiModelProperty(value = "银行卡号")
	private String bankCard;
	@ApiModelProperty(value = "所属银行")
	private String bankName;
	@ApiModelProperty(value = "支行名称",required = false)
	private String depositBankName;
	@ApiModelProperty(value = "订单来源(值为会员来源的码表值)",required = false)
	private String orderSource;
	public String getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(String cashAmount) {
		this.cashAmount = cashAmount;
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

	public String getDepositBankName() {
		return depositBankName;
	}

	public void setDepositBankName(String depositBankName) {
		this.depositBankName = depositBankName;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
}
