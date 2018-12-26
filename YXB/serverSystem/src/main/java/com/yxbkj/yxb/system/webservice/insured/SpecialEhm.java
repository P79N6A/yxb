package com.yxbkj.yxb.system.webservice.insured;

import java.io.Serializable;

public class SpecialEhm  implements Serializable {
    private String BusinessDetail;
    private String InsureArea;
    private String BusinessSite;
    private String DeviceNo;
    private String Model;
    private String FuelName;
    private String Capacity;
    private String EngineSerialNo;
    private String StrictType;


    public String getBusinessDetail() {
        return BusinessDetail;
    }

    public void setBusinessDetail(String businessDetail) {
        BusinessDetail = businessDetail;
    }

    public String getInsureArea() {
        return InsureArea;
    }

    public void setInsureArea(String insureArea) {
        InsureArea = insureArea;
    }

    public String getBusinessSite() {
        return BusinessSite;
    }

    public void setBusinessSite(String businessSite) {
        BusinessSite = businessSite;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getFuelName() {
        return FuelName;
    }

    public void setFuelName(String fuelName) {
        FuelName = fuelName;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getEngineSerialNo() {
        return EngineSerialNo;
    }

    public void setEngineSerialNo(String engineSerialNo) {
        EngineSerialNo = engineSerialNo;
    }
}
