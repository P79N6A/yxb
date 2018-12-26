package com.yxbkj.yxb.entity.product;

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
 * 产品解读表
 * </p>
 *
 * @author 李明
 * @since 2018-11-14
 */
@TableName("yxb_product_analysis")
public class ProductAnalysis extends Model<ProductAnalysis> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 咨讯ID
     */
	private String analysisId;
    /**
     * 产品ID
     */
	private String productId;
    /**
     * 标题
     */
	private String title;
	/**
	 * 音频时长
	 */
	private Integer duration;
    /**
     * 图片
     */
	private String img;
    /**
     * 内容
     */
	private String content;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 文件路径
     */
	private String filePath;
    /**
     * 文件格式
     */
	private String fileType;
	private Integer readNum;
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
	/** 点赞数量
	 *
	 */
	@TableField(exist = false)
	private String praiseNum;
	/**
	 * 创建人
	 */
	@TableField(exist = false)
	private String nickName;
	/**
	 * 创建人
	 */
	@TableField(exist = false)
	private String headImg;


	 /** 富文本内容
	  *
	 */
	@TableField(exist = false)
	private String editorValue;

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
	public Wrapper<ProductAnalysis> generateSearchWrapper() {
		Wrapper<ProductAnalysis> wrapper = new EntityWrapper<>();
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

	public String getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(String analysisId) {
		this.analysisId = analysisId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
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

	public String getEditorValue() {
		return editorValue;
	}

	public void setEditorValue(String editorValue) {
		this.editorValue = editorValue;
	}

	public String getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getDuration() {
		return duration;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
