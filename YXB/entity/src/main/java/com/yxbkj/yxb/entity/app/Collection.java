package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_collection")
public class Collection {
    private String Id;
    private String collectionId;
    private String beCollectedId;
    private String memberId;
    private String collectionTime;
    private String collectionType;
    private String validity;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;
    @TableField(exist = false)
    private String newsId;
    @TableField(exist = false)
    private String headimg;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private Integer readNum;
    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String creatorTime;
    @TableField(exist = false)
    private Integer collNum;
    @TableField(exist = false)
    private Integer comNum;
    @TableField(exist = false)
    private String analysisId;
    @TableField(exist = false)
    private String productId;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private String creatorId;
    @TableField(exist = false)
    private String img;
    @TableField(exist = false)
    private Boolean checkLike;

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

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getBeCollectedId() {
        return beCollectedId;
    }

    public void setBeCollectedId(String beCollectedId) {
        this.beCollectedId = beCollectedId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getCollNum() {
        return collNum;
    }

    public void setCollNum(Integer collNum) {
        this.collNum = collNum;
    }

    public Integer getComNum() {
        return comNum;
    }

    public void setComNum(Integer comNum) {
        this.comNum = comNum;
    }

    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(Boolean checkLike) {
        this.checkLike = checkLike;
    }
}
