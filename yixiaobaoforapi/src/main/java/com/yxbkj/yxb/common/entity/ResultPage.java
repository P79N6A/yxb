package com.yxbkj.yxb.common.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author Lin_J
 * @ClassName: Page
 * @Description:  (暴露web接口返回参数基础类)
 * @date 2017年9月6日 下午5:16:45 
 *
 * @param <T>
 */
public class ResultPage<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	private String code;
	private boolean success = false;
	private String msg;
	private T data;
	private String xml;
	private String url;
	private List<T> list = new ArrayList<>();
	private Map<String, Object> map = new HashMap();

	private Map<String, Object> params = new HashMap();
	private int pageNo = 1;
	private int pageSize = 10;
	private int pages = 0;
	private int totalSize = 0;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultPage(String code) {
		this.code = code;
	}

	public ResultPage(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultPage() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getXml() {
		return xml;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public String toString() {
		// ReflectionToStringBuilder 性能远大于Object toString
		// ToStringStyle 一定要设置为jsonStyle 以便于数据处理
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
}