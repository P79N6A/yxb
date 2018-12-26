package com.yxbkj.yxb.domain.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum StatusEnum implements IEnum {
	/**
	 * @yes 1
	 */
	YES("1","是"),
	/**
	 * @no 0
	 */
	NO("0","否"),
	/**
	 * @LOGIN_ERROR 4
	 */
	LOGIN_ERROR("4","登录异常");
	
	private String value;
	private String desc;
	
	StatusEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Serializable getValue() {
		return this.value;
	}
	@JsonValue
	public String getDesc(){
        return this.desc;
    }
}
