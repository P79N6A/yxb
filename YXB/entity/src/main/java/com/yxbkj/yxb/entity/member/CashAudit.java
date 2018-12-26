package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 提现审核信息表
 * </p>
 *
 * @author x_sherl
 * @since 2018-07-18
 */
@TableName("yxb_cash_audit")
public class CashAudit extends Model<CashAudit> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 提现信息ID
     */
    @TableField("cash_info_id")
    private String cashInfoId;
    /**
     * 审核人
     */
    @TableField("audit_user")
    private String auditUser;
    /**
     * 审核时间
     */
    @TableField("audit_time")
    private String auditTime;
    /**
     * 审核意见
     */
    @TableField("audit_memo")
    private String auditMemo;
    /**
     * 审核备注
     */
    @TableField("audit_remark")
    private String auditRemark;

    @TableField(exist = false)
    private String nextAuditUser;

    @TableField(exist = false)
    private String cashStatus;

    @TableField(exist = false)
    private String memberName;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String auditUsername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCashInfoId() {
        return cashInfoId;
    }

    public void setCashInfoId(String cashInfoId) {
        this.cashInfoId = cashInfoId;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditMemo() {
        return auditMemo;
    }

    public void setAuditMemo(String auditMemo) {
        this.auditMemo = auditMemo;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getNextAuditUser() {
        return nextAuditUser;
    }

    public void setNextAuditUser(String nextAuditUser) {
        this.nextAuditUser = nextAuditUser;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAuditUsername() {
        return auditUsername;
    }

    public void setAuditUsername(String auditUsername) {
        this.auditUsername = auditUsername;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CashAudit{" +
                "id='" + id + '\'' +
                ", cashInfoId='" + cashInfoId + '\'' +
                ", auditUser='" + auditUser + '\'' +
                ", auditTime='" + auditTime + '\'' +
                ", auditMemo='" + auditMemo + '\'' +
                ", auditRemark='" + auditRemark + '\'' +
                ", nextAuditUser='" + nextAuditUser + '\'' +
                '}';
    }
}
