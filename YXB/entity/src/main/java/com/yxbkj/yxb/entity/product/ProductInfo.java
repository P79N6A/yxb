package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 产品信息表
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
@TableName("yxb_product_info")
public class ProductInfo extends Model<ProductInfo> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 产品ID
     */
	private String productId;

    /**
     * 产品名称
     */
	private String productName;
	/**
	 * 产品目录编码
	 */
	private String productCatalcode;
	/**
	 * 产品成本比例
	 */
	private String productCostRate;
	/**
	 * 推广链接
	 */
	private String extensionLink;
    /**
     * 产品佣金比例
     */
	private BigDecimal commissionRate;
    /**
     * 公司编码
     */
	private String companyCode;
    /**
     * 产品图片
     */
	private String productImg;
    /**
     * 产品URL
     */
	private String productUrl;
    /**
     * 产品描述
     */
	private String describle;
    /**
     * 上架状态 上架/下架
     */
	private String shelveStatus;
    /**
     * 上架时间
     */
	private String shelveTime;
    /**
     * 下架时间
     */
	private String offshelveTime;
    /**
     * 标签
     */
	private String productTags;
    /**
     * 数据有效性
     */
	private String validity;
	/**
	 * 犹豫期
	 */
	private Integer hesiPeriod;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCatalcode() {
		return productCatalcode;
	}

	public void setProductCatalcode(String productCatalcode) {
		this.productCatalcode = productCatalcode;
	}

	public String getProductCostRate() {
		return productCostRate;
	}

	public void setProductCostRate(String productCostRate) {
		this.productCostRate = productCostRate;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public String getShelveStatus() {
		return shelveStatus;
	}

	public void setShelveStatus(String shelveStatus) {
		this.shelveStatus = shelveStatus;
	}

	public String getShelveTime() {
		return shelveTime;
	}

	public void setShelveTime(String shelveTime) {
		this.shelveTime = shelveTime;
	}

	public String getOffshelveTime() {
		return offshelveTime;
	}

	public void setOffshelveTime(String offshelveTime) {
		this.offshelveTime = offshelveTime;
	}

	public String getProductTags() {
		return productTags;
	}

	public void setProductTags(String productTags) {
		this.productTags = productTags;
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

	public Integer getHesiPeriod() {
		return hesiPeriod;
	}

	public void setHesiPeriod(Integer hesiPeriod) {
		this.hesiPeriod = hesiPeriod;
	}



	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getExtensionLink() {
		return extensionLink;
	}

	public void setExtensionLink(String extensionLink) {
		this.extensionLink = extensionLink;
	}
}
