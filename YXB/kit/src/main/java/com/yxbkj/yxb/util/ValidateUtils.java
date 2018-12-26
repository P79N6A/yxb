package com.yxbkj.yxb.util;

import java.util.regex.Pattern;

/**
 * 数据验证相关类
 */
public class ValidateUtils {
    /**
     * 手机正则
     */
    public  static  String pattern_phone="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";
    public static  boolean isPhone(String phone){
        return Pattern.matches(pattern_phone, phone);
    }
}
