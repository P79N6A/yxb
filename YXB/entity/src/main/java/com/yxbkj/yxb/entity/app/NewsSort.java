package com.yxbkj.yxb.entity.app;


import com.baomidou.mybatisplus.annotations.TableName;

@TableName("yxb_news_sort")
public class NewsSort {
    private String Id;
    private String newsId;
    private String columnType;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
