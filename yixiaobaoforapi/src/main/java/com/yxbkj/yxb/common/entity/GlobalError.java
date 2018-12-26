package com.yxbkj.yxb.common.entity;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 全局异常信息返回类
 */
public class GlobalError implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private String code;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 请求参数数组
     */
    private String[] paramsArgs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String[] getParamsArgs() {
        return paramsArgs;
    }

    public void setParamsArgs(String[] paramsArgs) {
        this.paramsArgs = paramsArgs;
    }

    @Override
    public String toString() {
        // ReflectionToStringBuilder 性能远大于Object toString
        return ReflectionToStringBuilder.toString(this);
    }
}
