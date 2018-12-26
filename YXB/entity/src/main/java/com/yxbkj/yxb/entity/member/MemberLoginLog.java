package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@TableName("yxb_member_login_log")
public class MemberLoginLog extends Model<MemberLoginLog> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 会员id
     */
	private String memberId;
    /**
     * 登录来源
     */
	private String loginSource;
    /**
     * 登录时间
     */
	private String loginTime;
    /**
     * 登录ip
     */
	private String loginIp;
    /**
     * 登录经度
     */
	private String loginLongitude;
    /**
     * 登录纬度
     */
	private String loginLatitude;
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

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginLongitude() {
		return loginLongitude;
	}

	public void setLoginLongitude(String loginLongitude) {
		this.loginLongitude = loginLongitude;
	}

	public String getLoginLatitude() {
		return loginLatitude;
	}

	public void setLoginLatitude(String loginLatitude) {
		this.loginLatitude = loginLatitude;
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
