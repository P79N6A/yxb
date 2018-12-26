package com.yxbkj.yxb.entity.module;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * ClassName：Result
 * Description：响应结果信息
 * Author：李明
 * Created：2017/7/28
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("响应码")
    private int code = Code.SUCCESS;//响应码，默认成功

    @ApiModelProperty("是否弹出提示")
    private int is_alert = 0;//是否弹出提示，默认不弹出

    @ApiModelProperty("结果对象")
    private T data = null;//结果对象

    @ApiModelProperty("自定义返回对象")
    private T customs = null;

    @ApiModelProperty("响应消息")
    private String msg = "";//响应消息

    private String extra;//关联

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg, int is_alert) {
        this.code = code;
        this.msg = msg;
        this.is_alert = is_alert;
    }
    
    public Result(int code, String msg, T data,int is_alert) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.is_alert = is_alert;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIs_alert() {
        return is_alert;
    }

    public void setIs_alert(int is_alert) {
        this.is_alert = is_alert;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Result<T> cfgMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getCustoms() {
        return customs;
    }

    public void setCustoms(T customs) {
        this.customs = customs;
    }

    @ApiModelProperty(hidden = true)
    public boolean isSuccess() {
        return Code.SUCCESS == this.code;
    }

    @ApiModelProperty(hidden = true)
    public Result success(T data) {
        this.code = Code.SUCCESS;
        this.data = data;
        return this;
    }

    @ApiModelProperty(hidden = true)
    public boolean isFailed() {
        return Code.FAIL == this.code;
    }

    public Result failed() {
        this.code = Code.FAIL;
        return this;
    }

    public Result failed(String msg) {
        this.code = Code.FAIL;
        this.msg = msg;
        return this;
    }

    public Result failedWithAlert(String msg){
        this.code = Code.FAIL;
        this.msg = msg;
        this.is_alert = 1;
        return this;
    }
    @ApiModelProperty(hidden = true)
    public boolean isError() {
        return Code.ERROR == this.code;
    }
}
