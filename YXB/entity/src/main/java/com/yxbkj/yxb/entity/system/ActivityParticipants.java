package com.yxbkj.yxb.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 活动参与人员信息
 * </p>
 *
 * @author zy
 * @since 2018-12-06
 */
@TableName("yxb_activity_participants")
public class ActivityParticipants extends Model<ActivityParticipants> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityParticipants{" +
        ", id=" + id +
        ", activityNo=" + activityNo +
        ", memberId=" + memberId +
        ", pid=" + pid +
        ", activityMoney=" + activityMoney +
        ", memberTime=" + memberTime +
        "}";
    }
}
