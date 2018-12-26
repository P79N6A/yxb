package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * <p>
 * Token 基类 vo
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
public class BaseTokenVo implements Serializable {
	/**
	 * 令牌
	 */
	@ApiModelProperty(value = "令牌")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
