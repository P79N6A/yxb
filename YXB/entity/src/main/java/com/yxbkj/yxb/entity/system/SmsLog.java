package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 李明
 * @since 2018-10-19
 */
@TableName("sys_sms_log")
public class SmsLog extends Model<SmsLog> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String phone;
	private String code;
	private String ip;
	private String createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
