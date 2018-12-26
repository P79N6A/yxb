package com.yxbkj.yxb.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ContextHolder implements java.io.Serializable{
	private static final long serialVersionUID = -7268366146451571083L;
	private ConcurrentMap<String,Object> contexts = new ConcurrentHashMap<>();

	public Object get(String key) {
		return this.contexts.get(key);
	}

	public void put(String key, Object value) {
		this.contexts.put(key, value);
	}

	public void clear() {
		this.contexts.clear();
	}
}