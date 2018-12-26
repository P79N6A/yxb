package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 李明
 * @since 2018-09-12
 */
@TableName("yxb_order_payment")
public class OrderPayment extends Model<OrderPayment> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 会员ID
     */
	private String memberId;

	/**
	 * 支付金额
	 */
	private String payAmount;
	/**
	 * 订单金额
	 */
	private String orderAmount;
	/**
	 * 支付产品
	 */
	private String paymentProduct;
	/**
	 * 支付方式
	 */
	private String payWay;
    /**
     * 订单ID
     */
	private String orderId;
    /**
     * 内容主体  业务数据
     */
	private String content;
    /**
     * 备注
     */
	private String remark;
	/**
	 * 附加信息
	 */
	private String extra;


    /**
     * 状态 相关码表
     */
	private String status;
    /**
     * 创建时间
     */
	private String createTime;
    /**
     * 更新时间
     */
	private String updateTime;


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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPaymentProduct() {
		return paymentProduct;
	}

	public void setPaymentProduct(String paymentProduct) {
		this.paymentProduct = paymentProduct;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
