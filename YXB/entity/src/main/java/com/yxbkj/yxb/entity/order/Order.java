package com.yxbkj.yxb.entity.order;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 李明
 * @since 2018-07-24
 */
@TableName("yxb_order")
public class Order extends Model<Order> {
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 车险/非车险/理财产品/会员充值
     */
    @TableField("order_type")
    private String orderType;
    /**
     * 订单来源
     */
    @TableField("order_source")
    private String orderSource;
    /**
     * 会员ID
     */
    @TableField("order_member_id")
    private String orderMemberId;
    /**
     * 会员姓名
     */
    @TableField("order_member_name")
    private String orderMemberName;
    /**
     * 会员手机
     */
    @TableField("order_member_phone")
    private Long orderMemberPhone;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 未分佣/已分佣/不分佣
     */
    @TableField("commission_status")
    private String commissionStatus;
    /**
     * 未支付/已支付
     */
    @TableField("pay_status")
    private String payStatus;
    /**
     * 未出单/已出单/退单
     */
    @TableField("order_status")
    private String orderStatus;
    /**
     * 现金/微信/支付宝/壹支付/连连支付/众安/700度
     */
    @TableField("pay_type")
    private String payType;
    /**
     * 产品ID
     */
    @TableField("product_id")
    private String productId;
    /**
     * 创建人ID
     */
    @TableField("creator_id")
    private String creatorId;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    @TableField("creator_time")
    private String creatorTime;
    /**
     * 创建IP地址
     */
    @TableField("creator_ip")
    private String creatorIp;
    /**
     * 修改人ID
     */
    @TableField("modifier_id")
    private String modifierId;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    @TableField("modifier_time")
    private String modifierTime;
    /**
     * 修改IP地址
     */
    @TableField("modifier_ip")
    private String modifierIp;
    /**
     * 数据有效性
     */
    private String validity;
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
    /**
     * 产品名称
     */
    @TableField(exist = false)
    private String productName;

    /**
     * 订单创建起始时间
     */
    @TableField(exist = false)
    private String startTime;
    /**
     * 订单创建结束时间
     */
    @TableField(exist = false)
    private String endTime;

    /**
     * 当前页码
     */
    @TableField(exist = false)
    private Integer page;
    /**
     * 每页大小
     */
    @TableField(exist = false)
    private Integer limit;


    /**
     * 生成查询wrapper对象
     *
     * @return wrapper对象
     */
    public Wrapper<Order> generateSearchWrapper() {
        Wrapper<Order> wrapper = new EntityWrapper<>();
        if (!StringUtils.isEmpty(validity)) {
            wrapper.eq("validity", validity);
        }
        return wrapper;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderMemberId() {
        return orderMemberId;
    }

    public void setOrderMemberId(String orderMemberId) {
        this.orderMemberId = orderMemberId;
    }

    public String getOrderMemberName() {
        return orderMemberName;
    }

    public void setOrderMemberName(String orderMemberName) {
        this.orderMemberName = orderMemberName;
    }

    public Long getOrderMemberPhone() {
        return orderMemberPhone;
    }

    public void setOrderMemberPhone(Long orderMemberPhone) {
        this.orderMemberPhone = orderMemberPhone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCommissionStatus() {
        return commissionStatus;
    }

    public void setCommissionStatus(String commissionStatus) {
        this.commissionStatus = commissionStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getCreatorIp() {
        return creatorIp;
    }

    public void setCreatorIp(String creatorIp) {
        this.creatorIp = creatorIp;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifierTime() {
        return modifierTime;
    }

    public void setModifierTime(String modifierTime) {
        this.modifierTime = modifierTime;
    }

    public String getModifierIp() {
        return modifierIp;
    }

    public void setModifierIp(String modifierIp) {
        this.modifierIp = modifierIp;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
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
