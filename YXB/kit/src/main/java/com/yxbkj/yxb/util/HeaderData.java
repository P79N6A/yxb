package com.yxbkj.yxb.util;

public class HeaderData {
    private String app_key;
    private Integer source;
    private String channel;
    private String app_version;
    private String client_id;
    private Long user_id;
    private String encrypt;
    private Long timeStamp;

    private Double lat;//纬度
    private Double lng;//经度
    private String province;
    private String city;
    private String district;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "HeaderData{" +
                "app_key='" + app_key + '\'' +
                ", source=" + source +
                ", channel='" + channel + '\'' +
                ", app_version='" + app_version + '\'' +
                ", client_id='" + client_id + '\'' +
                ", user_id=" + user_id +
                ", encrypt='" + encrypt + '\'' +
                ", timeStamp=" + timeStamp +
                ", lat=" + lat +
                ", lng=" + lng +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
