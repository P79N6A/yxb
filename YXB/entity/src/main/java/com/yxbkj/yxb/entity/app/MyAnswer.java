package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableName;

public class MyAnswer {
    private String Id;
    private String answerId;
    private String questionId;
    private String answerContent;
    private String nickname;
    private String creatorTime;
    private String headimg;
    private Boolean checkLike;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public Boolean getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(Boolean checkLike) {
        this.checkLike = checkLike;
    }
}
