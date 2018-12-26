package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
@TableName("yxb_prize")
public class Prize extends Model<Prize> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 奖品id
     */
	private String prizeId;
	private String prizeName;
    /**
     * 活动编号
     */
	private String activityNo;
    /**
     * 奖品总数
     */
	private Integer prizeTotal;
	private Integer realTotal;
    /**
     * 奖品描述
     */
	private String prizeDesc;
    /**
     * 奖品排序
     */
	private Integer prizeSort;

	/**
	 * 奖品值
	 */
	private BigDecimal prizeVal;
	/**
	 * 奖品类型
	 */
	private String prizeType;
	/**
	 * 奖品图片
	 */
	private String prizeImg;

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
	 * 生成查询wrapper对象
	 *
	 * @return wrapper对象
	 */
	public Wrapper<Prize> generateSearchWrapper() {
		Wrapper<Prize> wrapper = new EntityWrapper<>();
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

	public BigDecimal getPrizeVal() {
		return prizeVal;
	}

	public void setPrizeVal(BigDecimal prizeVal) {
		this.prizeVal = prizeVal;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeImg() {
		return prizeImg;
	}

	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	public Integer getPrizeTotal() {
		return prizeTotal;
	}

	public void setPrizeTotal(Integer prizeTotal) {
		this.prizeTotal = prizeTotal;
	}

	public Integer getRealTotal() {
		return realTotal;
	}

	public void setRealTotal(Integer realTotal) {
		this.realTotal = realTotal;
	}

	public String getPrizeDesc() {
		return prizeDesc;
	}

	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}

	public Integer getPrizeSort() {
		return prizeSort;
	}

	public void setPrizeSort(Integer prizeSort) {
		this.prizeSort = prizeSort;
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
