package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 李明
 * @since 2018-10-17
 */
@TableName("yxb_third_order")
public class ThirdOrder extends Model<ThirdOrder> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 订单ID
     */
	private String proposalNo;
    /**
     * 三方订单号
     */
	private String othOrderCode;
	/**
	 * appid
	 */
	private String appId;
    /**
     * 通知地址
     */
	private String notifyUrl;
	/**
	 * 通知地址
	 */
	private String callUrl;
	/**
	 * 文件内容
	 */
	private String fileContent;


    /**
     * XML内容
     */
	private String content;
    /**
     * 附加数据
     */
	private String ext;
	private BigDecimal amount;
	private String policyNo;
	private String policyUrl;
    /**
     * 创建时间
     */
	private String createTime;
    /**
     * 更新时间
     */
	private String updateTime;
    /**
     * 状态 0 初始化 其他待定
     */
	private String status;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getOthOrderCode() {
		return othOrderCode;
	}

	public void setOthOrderCode(String othOrderCode) {
		this.othOrderCode = othOrderCode;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyUrl() {
		return policyUrl;
	}

	public void setPolicyUrl(String policyUrl) {
		this.policyUrl = policyUrl;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getCallUrl() {
		return callUrl;
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}




}
