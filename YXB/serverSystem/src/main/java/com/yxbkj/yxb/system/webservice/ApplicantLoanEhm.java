package com.yxbkj.yxb.system.webservice;

import java.io.Serializable;

public class ApplicantLoanEhm  implements Serializable {

    private String appliCode     ;
    private String appliName     ;
    private String insuredNature ;
    private String identifyType  ;
    private String identifyNumber;
    private String addressName   ;
    private String linkerName    ;
    private String postAddress   ;
    private String postCode      ;
    private String AppliProvinceCode;
    private String AppliCityCode;
    private String phone         ;
    private String mobile        ;
    private String email         ;
    private String sex           ;
    private String age           ;
    private String birthday      ;


    public String getAppliCode() {
        return appliCode;
    }

    public void setAppliCode(String appliCode) {
        this.appliCode = appliCode;
    }

    public String getAppliName() {
        return appliName;
    }

    public void setAppliName(String appliName) {
        this.appliName = appliName;
    }

    public String getInsuredNature() {
        return insuredNature;
    }

    public void setInsuredNature(String insuredNature) {
        this.insuredNature = insuredNature;
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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLinkerName() {
        return linkerName;
    }

    public void setLinkerName(String linkerName) {
        this.linkerName = linkerName;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAppliProvinceCode() {
        return AppliProvinceCode;
    }

    public void setAppliProvinceCode(String appliProvinceCode) {
        AppliProvinceCode = appliProvinceCode;
    }

    public String getAppliCityCode() {
        return AppliCityCode;
    }

    public void setAppliCityCode(String appliCityCode) {
        AppliCityCode = appliCityCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
