package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_comment")
public class InsertComment {
    private String Id;
    private String commentId;
    private String beCommentedId;
    private String content;
    private String memberId;
    private String commentType;
    private String remark;
    private String validity;
    private String commentTime;
    private String ext4;
    @TableField(exist = false)
    private Boolean checkLike;
    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String newsId;
    @TableField(exist = false)
    private String img;
    @TableField(exist = false)
    private String headImg;
    @TableField(exist = false)
    private Integer likeNum;
    @TableField(exist = false)
    private String productId;
    @TableField(exist = false)
    private String analysisId;
    @TableField(exist = false)
    private String nickName;

    public String getId(String uuid) {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getBeCommentedId() {
        return beCommentedId;
    }

    public void setBeCommentedId(String beCommentedId) {
        this.beCommentedId = beCommentedId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getId() {
        return Id;
    }

    public Boolean getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(Boolean checkLike) {
        this.checkLike = checkLike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
