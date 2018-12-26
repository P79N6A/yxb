package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 码表类型信息表
 * </p>
 *
 * @author 李明
 * @since 2018-08-21
 */
@TableName("sys_code_type")
public class CodeType extends Model<CodeType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 类型编码
     */
	private String typeCode;
    /**
     * 类型名称
     */
	private String typeName;
    /**
     * 数据有效性 10000001有效 10000002无效
     */
	private String validity;
    /**
     * 创建人id
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
     * 创建ip地址
     */
	private String creatorIp;
    /**
     * 修改人id
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
     * 修改ip地址
     */
	private String modifierIp;
    /**
     * 备注
     */
	private String remark;
    /**
     * ext1
     */
	private String ext1;
    /**
     * ext2
     */
	private Long ext2;
    /**
     * ext3
     */
	private BigDecimal ext3;
    /**
     * ext4
     */
	private String ext4;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
