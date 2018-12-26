package com.yxbkj.yxb.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信工具类
 *
 * @author 李明
 * @since 2018-07-30
 */
public class SmsUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static String API_ACCOUNT="vipyxb888";//创蓝账号
    public static String API_PASSWORD="Tch456789";//创蓝密码
    public static String API_SEND_URL="http://222.73.117.158/msg/HttpBatchSendSM";
    /**
     * 作者: 李明
     * 描述: 发送验证码
     * 备注:
     * @param message
     * @return
     */
    public static void sendSms(String message,String phone,int code){
        //logger.info("开始发送验证码");
        Map<String,Object> map = new HashMap<>();
        map.put("account",API_ACCOUNT);
        map.put("pswd",API_PASSWORD);
        map.put("msg","您的注册验证码是："+code+"。如需帮助请联系客服。");
        map.put("mobile",phone);
        map.put("needstatus",false);
        String response = HttpUtil.doPost(API_SEND_URL, map);
        //logger.info("发送相应信息"+response);
    }

}
