package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 理财订单表
 * </p>
 *
 * @author lideyang
 * @since 2018-08-07
 */
@TableName("yxb_order_fund")
public class OrderFund extends Model<OrderFund> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 投资平台用户ID
     */
    @TableField("platform_uid")
    private String platformUid;
    /**
     * 投资金额
     */
    @TableField("account_tender")
    private BigDecimal accountTender;
    /**
     * 回款利息总和
     */
    @TableField("recover_account_interes")
    private BigDecimal recoverAccountInteres;
    /**
     * 回款本金总和
     */
    @TableField("recover_account_capital")
    private BigDecimal recoverAccountCapital;
    /**
     * 投资时间
     */
    @TableField("tender_time")
    private String tenderTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlatformUid() {
        return platformUid;
    }

    public void setPlatformUid(String platformUid) {
        this.platformUid = platformUid;
    }

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public BigDecimal getRecoverAccountInteres() {
        return recoverAccountInteres;
    }

    public void setRecoverAccountInteres(BigDecimal recoverAccountInteres) {
        this.recoverAccountInteres = recoverAccountInteres;
    }

    public BigDecimal getRecoverAccountCapital() {
        return recoverAccountCapital;
    }

    public void setRecoverAccountCapital(BigDecimal recoverAccountCapital) {
        this.recoverAccountCapital = recoverAccountCapital;
    }

    public String getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(String tenderTime) {
        this.tenderTime = tenderTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderFund{" +
                ", id=" + id +
                ", orderId=" + orderId +
                ", platformUid=" + platformUid +
                ", accountTender=" + accountTender +
                ", recoverAccountInteres=" + recoverAccountInteres +
                ", recoverAccountCapital=" + recoverAccountCapital +
                ", tenderTime=" + tenderTime +
                "}";
    }
}
