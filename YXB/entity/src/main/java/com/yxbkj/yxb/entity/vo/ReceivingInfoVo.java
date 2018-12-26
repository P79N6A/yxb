package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

public class ReceivingInfoVo extends BaseTokenVo {

    @ApiModelProperty(value = "联系人",required = true)
    private String contacts;
    @ApiModelProperty(value = "联系电话",required = true)
    private String contactsPhone;
    @ApiModelProperty(value = "收获地址",required = true)
    private String address;
    @ApiModelProperty(value = "备注",required = false)
    private String remark;
    @ApiModelProperty(value = "中奖记录ID",required = false)
    private String prizeRecordId;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrizeRecordId() {
        return prizeRecordId;
    }

    public void setPrizeRecordId(String prizeRecordId) {
        this.prizeRecordId = prizeRecordId;
    }
}
