package com.yxbkj.yxb.common.utils;

import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ClassName：RequestParams
 * Description：请求参数
 * Author：李明
 * Created：2017/7/23
 */
public class RequestParams {

    private static String contentEncoding = "UTF-8";

    /**
     * 获取request请求里的参数
     * @param request
     * @return
     */
    public static Map<String,String> getParams(HttpServletRequest request){
        Map<String, String> urlParams = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        if(requestParams !=null){
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                urlParams.put(name, valueStr);
            }
        }
        return urlParams;
    }

    /**
     * 参数key排序
     * @param urlParams
     * @return
     */
    public static String getOrderParamString(Map<String, String> urlParams) {
        //return URLEncodedUtils.format(getOrderParamsList(urlParams), contentEncoding);
        return getParamStr(urlParams);
    }

    private static String getParamStr(Map<String, String> parms){
        StringBuffer sb = new StringBuffer();
        try {
            ArrayList<String> aa = new ArrayList<String>(parms.keySet());
            Collections.sort(aa);
            for (String key : aa) {
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

    private static List<BasicNameValuePair> getOrderParamsList(Map<String, String> urlParams) {
        List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();
        ArrayList<String> keys = new ArrayList<String>(urlParams.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            lparams.add(new BasicNameValuePair(key, urlParams.get(key)));
        }
        return lparams;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", "+86"); System.out.println(getOrderParamString(map));
    }
}
