package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * <p>
 * 产品仓库表
 * </p>
 *
 * @author 李明
 * @since 2018-11-12
 */
@TableName("yxb_product_repertory")
public class ProductRepertory extends Model<ProductRepertory> {

    private static final long serialVersionUID = 1L;




	private String id;
    /**
     * 产品ID
     */
	private String productId;
    /**
     * 产品图片
     */
	private String productImg;
	private String categoryId;
    /**
     * 产品名称
     */
	private String productName;
    /**
     * 投保年龄
     */
	private String policyAge;
    /**
     * 保障期间
     */
	private String policyPeriod;
	/**
	 * 交费期间
	 */
	private String paymentPeriod;
	/**
	 * 产品简介
	 */
	private String productDesc;
	private String productText;
	private String productPic;
    /**
     * 产品佣金比例
     */
	private String workType;
    /**
     * 公司编码
     */
	private String canbuyNum;
    /**
     * 公司编码
     */
	private String companyCode;
    /**
     * 保障概览,多张图片id，之间用逗号隔开
     */
	private String protectTag;

	private String readNum;

	@TableField(exist = false)
	private String companyName;
	@TableField(exist = false)
	private String companyLogo;
	@TableField(exist = false)
	private Boolean hasAna;

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


	@TableField(exist = false)
	List<ProductPlan> productPlans;
	@TableField(exist = false)
	List<ProductProtocol> productProtocols;


	/**
	 * 当前页码
	 */
	@TableField(exist = false)
	private Integer page;
	/**
	 * 会员ID
	 */
	@TableField(exist = false)
	private String memberId;
	/**
	 * 每页大小
	 */
	@TableField(exist = false)
	private Integer limit;

	/**
	 * 生成查询wrapper对象
	 *
	 * @return wrapper对象
	 */
	public Wrapper<ProductRepertory> generateSearchWrapper() {
		Wrapper<ProductRepertory> wrapper = new EntityWrapper<>();
		if (!StringUtils.isEmpty(validity)) {
			wrapper.eq("validity", validity);
		}

		return wrapper;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}



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

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPolicyAge() {
		return policyAge;
	}

	public void setPolicyAge(String policyAge) {
		this.policyAge = policyAge;
	}

	public String getPolicyPeriod() {
		return policyPeriod;
	}

	public void setPolicyPeriod(String policyPeriod) {
		this.policyPeriod = policyPeriod;
	}

	public String getProductText() {
		return productText;
	}

	public void setProductText(String productText) {
		this.productText = productText;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getCanbuyNum() {
		return canbuyNum;
	}

	public void setCanbuyNum(String canbuyNum) {
		this.canbuyNum = canbuyNum;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getProtectTag() {
		return protectTag;
	}

	public void setProtectTag(String protectTag) {
		this.protectTag = protectTag;
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

	public List<ProductPlan> getProductPlans() {
		return productPlans;
	}

	public void setProductPlans(List<ProductPlan> productPlans) {
		this.productPlans = productPlans;
	}

	public List<ProductProtocol> getProductProtocols() {
		return productProtocols;
	}

	public void setProductProtocols(List<ProductProtocol> productProtocols) {
		this.productProtocols = productProtocols;
	}

	public String getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}


	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Boolean getHasAna() {
		return hasAna;
	}

	public void setHasAna(Boolean hasAna) {
		this.hasAna = hasAna;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
