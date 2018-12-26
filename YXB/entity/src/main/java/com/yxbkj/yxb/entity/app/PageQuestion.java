package com.yxbkj.yxb.entity.app;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_question")
public class PageQuestion {
    private String id;
    private String questionId;
    private String questionContent;

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
}
