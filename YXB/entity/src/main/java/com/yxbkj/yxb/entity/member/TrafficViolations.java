package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 车辆违章记录表
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
@TableName("yxb_traffic_violations")
public class TrafficViolations extends Model<TrafficViolations> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 会员id
     */
	private String memberId;
    /**
     * 驾驶证扣分
     */
	private Integer fen;
    /**
     * 车牌号
     */
	private String carplate;
    /**
     * 罚款
     */
	private BigDecimal money;
    /**
     * 违章地址
     */
	private String area;
    /**
     * 违章详情
     */
	private String act;
    /**
     * 违章代码
     */
	private String code;
    /**
     * 违章城市
     */
	private String wzcity;
    /**
     * 处理状态
     */
	private String handled;
    /**
     * 文书编号
     */
	private String archiveno;
    /**
     * 车辆识别码
     */
	private String vin;
    /**
     * 发动机型号
     */
	private String engineNo;
    /**
     * 违章时间
     */
	private String date;
    /**
     * 数据有效性
     */
	private String validity;
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

	public Integer getFen() {
		return fen;
	}

	public void setFen(Integer fen) {
		this.fen = fen;
	}

	public String getCarplate() {
		return carplate;
	}

	public void setCarplate(String carplate) {
		this.carplate = carplate;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWzcity() {
		return wzcity;
	}

	public void setWzcity(String wzcity) {
		this.wzcity = wzcity;
	}

	public String getHandled() {
		return handled;
	}

	public void setHandled(String handled) {
		this.handled = handled;
	}

	public String getArchiveno() {
		return archiveno;
	}

	public void setArchiveno(String archiveno) {
		this.archiveno = archiveno;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
