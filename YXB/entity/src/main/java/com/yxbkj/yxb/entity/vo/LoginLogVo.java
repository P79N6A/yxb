package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * <p>
 * 登录日志vo
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
public class LoginLogVo implements Serializable {

    private static final long serialVersionUID = 1L;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phone;
	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码")
	private String code;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 登录来源
	 */
	@ApiModelProperty(value = "登录来源")
	private String loginSource;
	/**
	 * 登录经度
	 */
	@ApiModelProperty(value = "登录经度")
	private String loginLongitude;
	/**
	 * 登录纬度
	 */
	@ApiModelProperty(value = "登录纬度")
	private String loginLatitude;
	/**
	 * 登录纬度
	 */
	@ApiModelProperty(value = "登录IP")
	private String ip;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
