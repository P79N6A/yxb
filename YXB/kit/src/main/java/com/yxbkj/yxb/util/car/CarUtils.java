package com.yxbkj.yxb.util.car;


import com.yxbkj.yxb.util.HttpUtil;
import com.yxbkj.yxb.util.MD5Util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author 李明
 * @Description: 车辆违章查询工具类
 * @date 2018年08月13日 上午11:44:45
 *
 */
public class CarUtils {
    //接口地址
    public static String api_url = "http://platapi.chedaiol.com/";
    //查违章
    public static String illegal = "v1/car/illegal";
    //令牌
    public static String token = "1f71c2ddd4ad37b30105904b4ddb05ba";
    //盐值
    public static String key = "d55f4fe0dd019de05765f1e07fe899c7";
    public static void main(String[] args) {
        String res = searchCarInfo("009039", "川A8XV66", "FB418599");
        //System.out.println("响应信息"+res);
        //{"code":"000000","msg":"获取违章信息成功","data":{"fen":3,"carplate":"川A8XV66","money":100,"illegals":1,"list":[{"date":"2018-07-06 18:39:12","area":"嘉陵江路","act":"机动车违反禁止标线指示的","code":"1345","fen":"3","wzcity":"","money":"100","handled":"0","archiveno":"510104A400280444"}]}}
    }

    /**
     * 作者: 李明
     * 描述: 车辆违章信息查询
     * 备注:
     * @param vin  VIN码
     * @param license 车牌号
     * @param engineNo 发动机号码
     * @return
     */
    public static String searchCarInfo(String vin,String license,String engineNo){
        String url = api_url+illegal;
        String sign = null;
        String timestamp =  System.currentTimeMillis()+"";
        Map<String,Object> map = new HashMap<>();
        map.put("count",6);
        map.put("timestamp",timestamp);
        map.put("token",token);
        String paramStr = "POST"+getParamStr(map)+"v1/car/illegal";
        map.put("vin",vin);
        map.put("license",license);
        map.put("engineNo",engineNo);
        sign = MD5Util.getHamcSha1(paramStr,key);
        return HttpUtil.doPost(url, map,token,timestamp,sign);
    }


    /**
     * 作者: 李明
     * 描述: 参数排序
     * 备注:
     * @param parms
     * @return
     */
    private static String getParamStr(Map<String, Object> parms){
        StringBuffer sb = new StringBuffer();
        try {
            ArrayList<String> aa = new ArrayList<String>(parms.keySet());
            Collections.sort(aa);
            for (String key : aa) {
                sb.append(key).append("=").append(java.net.URLEncoder.encode(parms.get(key).toString(),"UTF-8")).append("&");
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
