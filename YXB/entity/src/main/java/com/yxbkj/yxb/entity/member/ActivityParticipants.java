package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 活动参与人员信息
 * </p>
 *
 * @author 李明
 * @since 2018-12-20
 */
@TableName("yxb_activity_participants")
public class ActivityParticipants extends Model<ActivityParticipants> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 活动编号
     */
	private String activityNo;
    /**
     * 参与会员
     */
	private String memberId;
    /**
     * 获得红包的会员
     */
	private String pid;
    /**
     * 活动金额
     */
	private BigDecimal activityMoney;
    /**
     * 会员参与时间
     */
	private String memberTime;

	@TableField(exist = false)
	private String nickName;
	@TableField(exist = false)
	private String memberName;
	@TableField(exist = false)
	private String headimg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public BigDecimal getActivityMoney() {
		return activityMoney;
	}

	public void setActivityMoney(BigDecimal activityMoney) {
		this.activityMoney = activityMoney;
	}

	public String getMemberTime() {
		return memberTime;
	}

	public void setMemberTime(String memberTime) {
		this.memberTime = memberTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
