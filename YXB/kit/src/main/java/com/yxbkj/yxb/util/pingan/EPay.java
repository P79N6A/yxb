package com.yxbkj.yxb.util.pingan;

import com.yxbkj.yxb.util.Configurations;
import com.yxbkj.yxb.util.MD5Util;
import com.yxbkj.yxb.util.YxbConfig;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @Author 李明
 * @Description: 平安E生保支付工具类
 * @date 2018年08月14日 上午17:11:25
 */
public class EPay {
    //api地址
    //const api_url = "http://test-mobile.health.pingan.com/ehis-hm";
    //public static String  api_url = "https://test-mobile.health.pingan.com:42443/ehis-hm";
    //public static String  api_url = "https://mobile.health.pingan.com/ehis-hm";//正式
    //移动端渠道代码
    public static String  code = "000070";
    //收银台秘钥
    // public static String  keys = "SXEZ7FBDBQROTQ9YU1VYCVGXM1796XJFLFECE4WCETWXZJW47DL4AQ3TMTQ116BBYDZI29HUQSL5LU7QV5ABXUHRPVC8ZUC5USVUUA3KR7VZ2UKGOE3YF551D99ZQ8XB";
    //public static String  keys = "6GIXR58EZE3SHMOPW87UX9K7JKIYKFK8SUTZG9K9SFV2N4X3KR8A13M29CZ5OG81QU17GCDFMRYTQ1WG1I5CMJ2DS14J491EN97J6ORAOXZTN46GOS2NTZJW4EEL4PTK";//正式
    //同步
    //public static String  return_url = "http://webapp.ybw100.com/20170721/kegugg.html";
    //public static String  return_url = "https://wap.ybw100.com/20170721/kegugg.html";
    // const return_url = "https://wap.ybw100.com/20170721/kegugg.html";// 正式
    //异步
    //public static String notify_url = "https://app.ybw100.com/notify/pingAnNotify";//测试
    //public static String  notify_url = "https://server.ybw100.com/epay/notify?terminalType=weixin";//正式
    //签名方式
    public static String  sign_type = "SHA-256";



    public  static  String getNotifyUrl(){
         if(YxbConfig.active.equals("prod")){
             return YxbConfig.pingan_notify_url;
         }else{
             return YxbConfig.pingan_notify_url_dev;
         }
    }
    public  static  String getReturnUrl(){
        if(YxbConfig.active.equals("prod")){
            return YxbConfig.pingan_return_url;
        }else{
            return YxbConfig.pingan_return_url_dev;
        }
    }

    /**
     * 描述: 获取跳转支付的url
     * 作者: 李明
     * 备注:
     * @param map
     * @return
     */
    public static String pay(Map<String,String> map){
        String api = YxbConfig.getPinganApiUrl()+"/cashier/pay.do";
//        map.put("channel_order_no","88000000023959547");
//        map.put("goods_desc","test");
//        map.put("total_fee","23412");
        map.put("channel_id",code);
        map.put("return_url",getReturnUrl());
        map.put("notify_url",getNotifyUrl());
        map.put("sign_type","SHA-256");
        //参数签名
        String sign = getSignVeryfy(map);
        map.put("sign",sign);
        //参数排序 + 拼接
        String params = paramSortAndAdd(map);
        return api+"?"+params;
    }
    /**
     * 描述: 参数拼接成字符串
     * 作者: 李明
     * 备注:
     * @param parms
     * @return
     */
    private static String paramSortAndAdd(Map<String, String> parms) {
        StringBuffer sb = new StringBuffer();
        try {
            ArrayList<String> list = new ArrayList<String>(parms.keySet());
            Collections.sort(list);
            for (String key : list) {
                sb.append(key).append("=").append(java.net.URLEncoder.encode(parms.get(key),"UTF-8")).append("&");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length()-1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 描述: 把map参数加密
     * 作者: 李明
     * 备注:
     * @param map
     * @return
     */
    public static String getSignVeryfy(Map<String, String> map) {
         //除去待签名参数数组中的空值和签名参数
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type") || "".equals(map.get(key))) {
            }else{
                list.add(key);
            }
        }
        //对待签名参数数组排序
        Collections.sort(list);
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String paramStr = getParamStr(list,map);
        return MD5Util.getSHA256Str(paramStr+YxbConfig.getPinganApiKey());
    }



    /**
     * 描述: 获取拼接的字符串
     * 作者: 李明
     * 备注:
     * @param list
     * @param parms
     * @return
     */
    private static String getParamStr(List<String> list,Map<String, String> parms){
        StringBuffer sb = new StringBuffer();
        try {
            Collections.sort(list);
            for (String key : list) {
                sb.append(key).append("=").append(parms.get(key)).append("&");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length()-1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
