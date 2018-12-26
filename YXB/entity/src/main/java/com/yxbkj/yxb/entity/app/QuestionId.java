package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

public class QuestionId {
    private String questionContent;
    private String questionCreator;
    private String questionTime;
    private String answerContent;
    private String answerCreator;
    private String answerTime;
    private String answerHeadimg;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionCreator() {
        return questionCreator;
    }

    public void setQuestionCreator(String questionCreator) {
        this.questionCreator = questionCreator;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
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

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerHeadimg() {
        return answerHeadimg;
    }

    public void setAnswerHeadimg(String answerHeadimg) {
        this.answerHeadimg = answerHeadimg;
    }
}
