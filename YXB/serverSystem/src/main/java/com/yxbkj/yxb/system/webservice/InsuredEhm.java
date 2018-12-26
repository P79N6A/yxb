package com.yxbkj.yxb.system.webservice;

import com.yxbkj.yxb.system.webservice.insured.*;

import java.io.Serializable;
import java.util.List;

public class InsuredEhm  implements Serializable {
    private String serialNo;
    private String insuredCode;
    private String insuredName   ;
    private String insuredNature ;
    private String identifyType  ;
    private String identifyNumber;
    private String ApplyNum;
    private String addressName   ;
    private String linkerName    ;
    private String postAddress   ;
    private String postCode      ;
    private String phone         ;
    private String mobile        ;
    private String email         ;
    private String sex           ;
    private String age           ;
    private String birthday;
    private String Relation;
    private String OccupationCode1;
    private String OccupationCode2;
    private String OccupationCode3           ;
    private String BenefitFlag           ;
    private List<BenefitEhm> BenefitEhmArray           ;
    private List<ItemKindEhm> ItemKindEhmArray           ;
    private LoanInfoEhm LoanInfoEhm           ;
    private SpecialEhm SpecialEhm           ;
    private String Othflag1           ;
    private String Othflag2           ;
    private String Othflag3           ;
    private String Othflag4           ;
    private String Othflag5           ;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getInsuredCode() {
        return insuredCode;
    }

    public void setInsuredCode(String insuredCode) {
        this.insuredCode = insuredCode;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
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

    public String getApplyNum() {
        return ApplyNum;
    }

    public void setApplyNum(String applyNum) {
        ApplyNum = applyNum;
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

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getOccupationCode1() {
        return OccupationCode1;
    }

    public void setOccupationCode1(String occupationCode1) {
        OccupationCode1 = occupationCode1;
    }

    public String getOccupationCode2() {
        return OccupationCode2;
    }

    public void setOccupationCode2(String occupationCode2) {
        OccupationCode2 = occupationCode2;
    }

    public String getOccupationCode3() {
        return OccupationCode3;
    }

    public void setOccupationCode3(String occupationCode3) {
        OccupationCode3 = occupationCode3;
    }

    public String getBenefitFlag() {
        return BenefitFlag;
    }

    public void setBenefitFlag(String benefitFlag) {
        BenefitFlag = benefitFlag;
    }

    public List<BenefitEhm> getBenefitEhmArray() {
        return BenefitEhmArray;
    }

    public void setBenefitEhmArray(List<BenefitEhm> benefitEhmArray) {
        BenefitEhmArray = benefitEhmArray;
    }

    public List<ItemKindEhm> getItemKindEhmArray() {
        return ItemKindEhmArray;
    }

    public void setItemKindEhmArray(List<ItemKindEhm> itemKindEhmArray) {
        ItemKindEhmArray = itemKindEhmArray;
    }

    public com.yxbkj.yxb.system.webservice.insured.LoanInfoEhm getLoanInfoEhm() {
        return LoanInfoEhm;
    }

    public void setLoanInfoEhm(com.yxbkj.yxb.system.webservice.insured.LoanInfoEhm loanInfoEhm) {
        LoanInfoEhm = loanInfoEhm;
    }

    public com.yxbkj.yxb.system.webservice.insured.SpecialEhm getSpecialEhm() {
        return SpecialEhm;
    }

    public void setSpecialEhm(com.yxbkj.yxb.system.webservice.insured.SpecialEhm specialEhm) {
        SpecialEhm = specialEhm;
    }

    public String getOthflag1() {
        return Othflag1;
    }

    public void setOthflag1(String othflag1) {
        Othflag1 = othflag1;
    }

    public String getOthflag2() {
        return Othflag2;
    }

    public void setOthflag2(String othflag2) {
        Othflag2 = othflag2;
    }

    public String getOthflag3() {
        return Othflag3;
    }

    public void setOthflag3(String othflag3) {
        Othflag3 = othflag3;
    }

    public String getOthflag4() {
        return Othflag4;
    }

    public void setOthflag4(String othflag4) {
        Othflag4 = othflag4;
    }

    public String getOthflag5() {
        return Othflag5;
    }

    public void setOthflag5(String othflag5) {
        Othflag5 = othflag5;
    }
}
