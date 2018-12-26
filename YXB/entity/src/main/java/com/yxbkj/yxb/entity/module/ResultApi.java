package com.yxbkj.yxb.entity.module;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * ClassName：Result
 * Description：响应结果信息
 * Author：李明
 * Created：2017/7/28
 */
public class ResultApi<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("响应码")
    private int responseCode = Code.SUCCESS;//响应码，默认成功

    @ApiModelProperty("是否弹出提示")
    private String errorMessage = "操作成功!";//消息提示

    @ApiModelProperty("结果对象")
    private T responseData = null;//结果对象


    public ResultApi(int responseCode, String errorMessage, T responseData) {
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.responseData = responseData;
    }
    public ResultApi() {
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}
