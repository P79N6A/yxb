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
 * @since 2018-10-17
 */
@TableName("yxb_third_payres")
public class ThirdPayres extends Model<ThirdPayres> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String req;
	private String createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
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
