package com.yxbkj.yxb.system.webservice.insured;

import java.io.Serializable;
import java.util.List;

public class ItemKindEhm implements Serializable {
    private String serialNo;
    private String KindCode;
    private String KindName;
    private String ItemCode;
    private String ItemName;
    private String Amount;
    private String Premium;
    private String Rate;
    private String Deductible;
    private String DeductibleRate;
    private String AddressNo;
    private List<LimitEhm> LimitEhmArray;
    private String othField1;
    private String othField2;
    private String othField3;
    private String othField4;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getKindCode() {
        return KindCode;
    }

    public void setKindCode(String kindCode) {
        KindCode = kindCode;
    }

    public String getKindName() {
        return KindName;
    }

    public void setKindName(String kindName) {
        KindName = kindName;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPremium() {
        return Premium;
    }

    public void setPremium(String premium) {
        Premium = premium;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getDeductible() {
        return Deductible;
    }

    public void setDeductible(String deductible) {
        Deductible = deductible;
    }

    public String getDeductibleRate() {
        return DeductibleRate;
    }

    public void setDeductibleRate(String deductibleRate) {
        DeductibleRate = deductibleRate;
    }

    public String getAddressNo() {
        return AddressNo;
    }

    public void setAddressNo(String addressNo) {
        AddressNo = addressNo;
    }


    public List<LimitEhm> getLimitEhmArray() {
        return LimitEhmArray;
    }

    public void setLimitEhmArray(List<LimitEhm> limitEhmArray) {
        LimitEhmArray = limitEhmArray;
    }

    public String getOthField1() {
        return othField1;
    }

    public void setOthField1(String othField1) {
        this.othField1 = othField1;
    }

    public String getOthField2() {
        return othField2;
    }

    public void setOthField2(String othField2) {
        this.othField2 = othField2;
    }

    public String getOthField3() {
        return othField3;
    }

    public void setOthField3(String othField3) {
        this.othField3 = othField3;
    }

    public String getOthField4() {
        return othField4;
    }

    public void setOthField4(String othField4) {
        this.othField4 = othField4;
    }
}
