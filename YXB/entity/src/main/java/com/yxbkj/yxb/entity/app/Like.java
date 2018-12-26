package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_like")
public class Like {
    private String Id;
    private String beLikedId;
    private String memberId;
    private String likeType;
    private String likeTime;
    private String likeId;
    private String validity;

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBeLikedId() {
        return beLikedId;
    }

    public void setBeLikedId(String beLikedId) {
        this.beLikedId = beLikedId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }
}
