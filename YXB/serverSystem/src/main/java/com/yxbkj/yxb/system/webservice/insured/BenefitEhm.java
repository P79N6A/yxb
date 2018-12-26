package com.yxbkj.yxb.system.webservice.insured;

import java.io.Serializable;

public class BenefitEhm implements Serializable {
    private String serialNo;
    private String BenefitName;
    private String identifyType  ;
    private String identifyNumber;
    private String Relation;
    private String sex           ;
    private String birthday      ;
    private String mobile        ;
    private String BenefitRate;
    private String BenefitAmount;
    private String benefitSequence;
    private String BenefitType;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBenefitName() {
        return BenefitName;
    }

    public void setBenefitName(String benefitName) {
        BenefitName = benefitName;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBenefitRate() {
        return BenefitRate;
    }

    public void setBenefitRate(String benefitRate) {
        BenefitRate = benefitRate;
    }

    public String getBenefitAmount() {
        return BenefitAmount;
    }

    public void setBenefitAmount(String benefitAmount) {
        BenefitAmount = benefitAmount;
    }

    public String getBenefitSequence() {
        return benefitSequence;
    }

    public void setBenefitSequence(String benefitSequence) {
        this.benefitSequence = benefitSequence;
    }

    public String getBenefitType() {
        return BenefitType;
    }

    public void setBenefitType(String benefitType) {
        BenefitType = benefitType;
    }
}
