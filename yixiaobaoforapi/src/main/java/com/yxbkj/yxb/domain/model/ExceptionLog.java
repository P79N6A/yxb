package com.yxbkj.yxb.domain.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ClassName：ExceptionLog
 * Description：异常日志信息
 * Author：李明
 * Created：2018/7/23
 */
public class ExceptionLog extends Model<ExceptionLog> {
    private Long id;
    private String app_key;
    private String class_name;//异常类名
    private String method_name;//方法名
    private String exp_msg;//异常信息
    private String request_param;//请求参数
    private String exp_stack;//异常栈
    private Timestamp exp_time;//异常时间
    private Timestamp create_time;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getExp_msg() {
        return exp_msg;
    }

    public void setExp_msg(String exp_msg) {
        this.exp_msg = exp_msg;
    }

    public String getRequest_param() {
        return request_param;
    }

    public void setRequest_param(String request_param) {
        this.request_param = request_param;
    }

    public Timestamp getExp_time() {
        return exp_time;
    }

    public void setExp_time(Timestamp exp_time) {
        this.exp_time = exp_time;
    }

    public String getExp_stack() {
        return exp_stack;
    }

    public void setExp_stack(String exp_stack) {
        this.exp_stack = exp_stack;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String toString() {
        return "ExceptionLog{" +
                "id=" + id +
                ", class_name='" + class_name + '\'' +
                ", method_name='" + method_name + '\'' +
                ", exp_msg='" + exp_msg + '\'' +
                ", request_param='" + request_param + '\'' +
                ", exp_time=" + exp_time +
                ", create_time=" + create_time +
                '}';
    }
}
