package com.yxbkj.yxb.entity.product;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 产品分类
 * </p>
 *
 * @author 李明
 * @since 2018-07-10
 */
@TableName("yxb_product_catalogue")
public class ProductCatalogue extends Model<ProductCatalogue> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String productCatalcode;
	private String productCatalname;
	private String productType;
	private String parentCatalcode;
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


    /**
     * 当前页码
     */
    @TableField(exist = false)
    private Integer page;
    /**
     * 每页大小
     */
    @TableField(exist = false)
    private Integer limit;

    /**
     * 上级目录名称
     */
    @TableField(exist = false)
    private String parentCatalogueName;


    /**
     * 生成查询wrapper对象
     *
     * @return wrapper对象
     */
    public Wrapper<ProductCatalogue> generateSearchWrapper() {
        Wrapper<ProductCatalogue> wrapper = new EntityWrapper<>();
        if (!StringUtils.isEmpty(validity)) {
            wrapper.eq("validity", validity);
        }
        if (!StringUtils.isEmpty(productCatalname)) {
            wrapper.like("product_catalname", productCatalname);
        }
        if (!StringUtils.isEmpty(productCatalcode)) {
            wrapper.like("product_catalcode", productCatalcode);
        }
        if (!StringUtils.isEmpty(productType)) {
            wrapper.like("product_type", productType);
        }

        return wrapper;
    }

    public String getParentCatalogueName() {
        return parentCatalogueName;
    }

    public void setParentCatalogueName(String parentCatalogueName) {
        this.parentCatalogueName = parentCatalogueName;
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


	public String getProductCatalcode() {
		return productCatalcode;
	}

	public void setProductCatalcode(String productCatalcode) {
		this.productCatalcode = productCatalcode;
	}

	public String getProductCatalname() {
		return productCatalname;
	}

	public void setProductCatalname(String productCatalname) {
		this.productCatalname = productCatalname;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getParentCatalcode() {
		return parentCatalcode;
	}

	public void setParentCatalcode(String parentCatalcode) {
		this.parentCatalcode = parentCatalcode;
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
