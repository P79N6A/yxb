package com.yxbkj.yxb.exception;

import java.io.Serializable;

/**
 * @auther Lin_J
 * @className: (全局异常基类，如需定义其他异常类，继承该类)
 * @date 2018/06/04 15:08:22
 */
public class BaseException extends RuntimeException implements Serializable{
	private static final long serialVersionUID = -3243158073107841046L;

	private String msg;
	private String msgCode;
	private String params;
	private String[] paramArgs;
	protected Throwable cause;

	/**
	 * 全局异常基类
	 * @param code	错误号
	 */
	public BaseException(String code) {
		this.msg = null;
		this.msgCode = null;
		this.cause = null;

		this.msgCode = code;
	}

	/**
	 * 全局异常基类
	 * @param code	错误号
	 */
	public BaseException(String code, String msg) {
		this.msg = null;
		this.msgCode = null;
		this.cause = null;

		this.msgCode = code;
		this.msg = msg;
	}

	/**
	 * 全局异常基类
	 * @param code			错误号
	 * @param cause			异常		
	 */
	public BaseException(String code, Throwable cause) {
		this(code);
		this.cause = cause;
	}


	/**
	 * 全局异常基类
	 * @param code 错误号
	 * @param params 参数json字符串
	 */
	public BaseException(String code, String msg, String params) {
		this.msg = null;
		this.msgCode = null;
		this.cause = null;

		this.msgCode = code;
		this.msg = msg;
		this.params = params;
	}

	/**
	 * 全局异常基类
	 * @param code			错误号
	 * @param param			参数
	 * @param cause		异常	
	 */
	public BaseException(String code, String param, Throwable cause) {
		this(code, param);
		this.cause = cause;
	}

	/**
	 * 全局异常基类
	 * @param code			错误号
	 * @param paramArgs		参数数组
	 * @param cause		异常
	 */
	public BaseException(String code, String msg, String[] paramArgs, Throwable cause) {
		this(code, msg);
		this.paramArgs=paramArgs;
		this.cause = cause;
	}


	/**
	 * 获取原始异常
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * 设置原始异常
	 * @param cause
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}


	public String toString() {
		StringBuffer buffer = new StringBuffer(50);
		buffer.append(super.getClass().getName());
		buffer.append("code=");
		buffer.append(getMsgCode());
		if (getMsg() != null) {
			buffer.append(", desc=");
			buffer.append(getMsg());
		}
		if (getCause() != null) {
			buffer.append(", exception is:");
			buffer.append(getCause());
		}
		return buffer.toString();
	}

	/**
	 * 获取异常文字消息
	 * @return
	 */
	public String getMessage() {
		return msg;
	}

	/**
	 * 获取异常文字消息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置异常文字信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 取得异常编号
	 * @return
	 */
	public String getMsgCode() {
		return msgCode;
	}

	/**
	 * 设置异常编号
	 * @param msgCode
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String[] getParamArgs() {
		return paramArgs;
	}

	public void setParamArgs(String[] paramArgs) {
		this.paramArgs = paramArgs;
	}

	/**
	 * 取得异常stack trace
	 * @return	StringBuffer
	 */
	public StringBuffer getStack() {
		StringBuffer stack = new StringBuffer();
		StackTraceElement[] trace = this.getStackTrace();
		for (int i=0; i < trace.length; i++)
			stack.append("\tat " + trace[i] + "<br/>");
		return stack;
	}

}
