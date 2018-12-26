package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_question")
public class Question {
    private String id;
    private String questionId;
    private String questionContent;
    private String validity;
    private String creatorId;
    private String creator;
    private String creatorTime;
    @TableField(exist = false)
    private Integer total;
    @TableField(exist = false)
    private String questionHeadimg;
    @TableField(exist = false)
    private Integer isRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getQuestionHeadimg() {
        return questionHeadimg;
    }

    public void setQuestionHeadimg(String questionHeadimg) {
        this.questionHeadimg = questionHeadimg;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
