package com.yxbkj.yxb.common.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class PEnvUtil {

    // 当打开多个资源文件时，缓存资源文件
    private static HashMap<String, PEnvUtil> configMap = new HashMap<>();
    // 打开文件时间，判断超时使用
    private Date loadTime = null;
    // 资源文件
    private ResourceBundle resourceBundle = null;
    // 默认资源文件名称
    private static final String NAME = "app,redis";
    // 缓存时间
    private static final Integer TIME_OUT = 60 * 1000;

    // 私有构造方法，创建单例
    private PEnvUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PEnvUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PEnvUtil getInstance(String name) {
    	if(!StringUtils.isEmpty(name)){
    		String[] str=name.split(",");
    		if(str.length>0){
    			for(String s:str){
    	    		PEnvUtil conf = configMap.get(s);
    	            if (null == conf) {
    	                conf = new PEnvUtil(s);
    	                configMap.put(s, conf);
    	            }
    	            // 判断是否打开的资源文件是否超时1分钟
    	            if ((new Date().getTime() - conf.getLoadTime().getTime()) > TIME_OUT) {
    	                conf = new PEnvUtil(s);
    	                configMap.put(s, conf);
    	            }
    	            return conf;
    	    	}
    		}
    	}
        return null;
    }

    // 根据key读取value
    public String get(String key) {
        try {
            String value = new String(resourceBundle.getString(key).getBytes(Consts.ISO_8859_1), Consts.UTF_8);
            return value;
        }catch (Exception e) {
            return "";
        }
    }

    // 根据key读取value(整形)
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        }catch (MissingResourceException e) {
            return null;
        }
    }

    // 根据key读取value(布尔)
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        }catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }

}
