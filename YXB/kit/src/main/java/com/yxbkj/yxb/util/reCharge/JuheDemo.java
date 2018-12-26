package com.yxbkj.yxb.util.reCharge;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public class JuheDemo {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="d3782ad3eb8bb5293fc95c928ca38537";

    //1.订单状态查询
    public static void getRequest1(){
        String result =null;
        String url ="http://test-v.juhe.cn/ofpay/sinopec/ordersta";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("orderid","");//商家订单号，8-32位字母数字组合
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2.账户余额查询
    public static void getRequest2(){
        String result =null;
        String url ="http://test-v.juhe.cn/ofpay/sinopec/yue";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("timestamp","");//当前时间戳，如：1432788379
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("sign","");//校验值，md5(OpenID+key+timestamp)，OpenID在个人中心查询

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.加油卡充值
    public static void getRequest3(){
        String result =null;
        String url ="http://test-v.juhe.cn/ofpay/sinopec/onlineorder";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("proid",10000);//产品id:10000(中石化50元加油卡)[暂不支持]、10001(中石化100元加油卡)、10003(中石化500元加油卡)、10004(中石化1000元加油卡)、10007(中石化任意金额充值)[暂不支持]、10008(中石油任意金额充值)
        params.put("cardnum","1");//充值数量（产品id为10007、10008时为具体充值金额(整数)，其余产品id请传固定值1）；注：中石油任意冲(产品id:10008)暂时只支持100\200\500\1000
        params.put("orderid","12345678");//商家订单号，8-32位字母数字组合
        params.put("game_userid","100011369");//加油卡卡号，中石化：以100011开头的卡号、中石油：以9开头的卡号
        params.put("gasCardTel","");//持卡人手机号码
        params.put("gasCardName","");//持卡人姓名
        params.put("chargeType","1");//加油卡类型 （1:中石化、2:中石油；默认为1)
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("sign","");//校验值，md5(OpenID+key+proid+cardnum+game_userid+orderid)，OpenID在个人中心查询

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

    }


    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
