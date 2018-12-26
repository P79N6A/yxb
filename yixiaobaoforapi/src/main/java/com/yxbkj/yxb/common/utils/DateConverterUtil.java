package com.yxbkj.yxb.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * @author chenfenghua
 * @date 2018年6月26日
 */

public class DateConverterUtil {
	
	public static Date convert(String source) {

		Date date = null;
		try {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = simpleDateFormat.parse(source);
		} catch(Exception e) {
		}

		if(date == null) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
				date = simpleDateFormat.parse(source);
			} catch(Exception e2) {
			
			}
		}
		return date;
	}
}
