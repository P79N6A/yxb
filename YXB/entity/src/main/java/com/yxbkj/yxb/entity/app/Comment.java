package com.yxbkj.yxb.entity.app;


public class Comment {
    private String Id;
    private String commentId;
    private String beCommentedId;
    private String content;
    private String commentTime;
    private String headimg;
    private String nickname;
    private String newsId;
    private String newsTitle;
    private String creatorTime;
    private Integer praise;
    private Integer commentNumber;
    private Boolean checkLike;

    public String getId() {
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

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getTitle() {
        return newsTitle;
    }

    public void setTitle(String title) {
        this.newsTitle = title;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Boolean getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(Boolean checkLike) {
        this.checkLike = checkLike;
    }
}
