package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

public class QuestionList {
    private String titleId;
    private String title;
    private String titleCreator;
    private String titleCreatorTime;
    private String answerId;
    private String answerContent;
    private String answerCreator;
    private String answerCreatorTime;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleCreator() {
        return titleCreator;
    }

    public void setTitleCreator(String titleCreator) {
        this.titleCreator = titleCreator;
    }

    public String getTitleCreatorTime() {
        return titleCreatorTime;
    }

    public void setTitleCreatorTime(String titleCreatorTime) {
        this.titleCreatorTime = titleCreatorTime;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getAnswerCreator() {
        return answerCreator;
    }

    public void setAnswerCreator(String answerCreator) {
        this.answerCreator = answerCreator;
    }

    public String getAnswerCreatorTime() {
        return answerCreatorTime;
    }

    public void setAnswerCreatorTime(String answerCreatorTime) {
        this.answerCreatorTime = answerCreatorTime;
    }
}
