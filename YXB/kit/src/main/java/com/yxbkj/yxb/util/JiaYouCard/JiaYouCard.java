package com.yxbkj.yxb.util.JiaYouCard;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.yxbkj.yxb.util.MD5Util;
import com.yxbkj.yxb.util.aliyun.SmsUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public class JiaYouCard {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="d726c90539969a0f8c42f3e8314e6463";
    public static final String OPENID = "JH1d88add084823a2922b05b902870270d";
    //1.订单状态查询
    public static String getRequest1(String orderid){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/sinopec/ordersta";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("orderid",orderid);//商家订单号，8-32位字母数字组合
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        try {
            result =net(url, params, "GET");
            return result;
            /*JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //2.账户余额查询
    public static Map<String,Object> getRequest2(){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/sinopec/yue";//请求接口地址
        Map params = new HashMap();//请求参数
        String timeStamp = new Date().getTime() + "";
        params.put("timestamp",timeStamp);//当前时间戳，如：1432788379
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("sign",MD5Util.MD5(OPENID + APPKEY + timeStamp).toLowerCase());//校验值，md5(OpenID+key+timestamp)，OpenID在个人中心查询

        try {
            Map<String,Object> map = new HashMap<>();
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                //System.out.println(object.get("result"));
                map.put("result",result);
                map.put("error",object.get("error_code"));
                return map;
            }else{
                //System.out.println(object.get("error_code")+":"+object.get("reason"));
                map.put("error",object.get("error_code"));
                map.put("reason",object.get("reason"));
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            return null;
        }
    }

    //3.加油卡充值
    public static String getRequest3(int proid, String cardNum,String game_userid, String gasCardTel, String gasCardName, int chargeType,String orderId){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/sinopec/onlineorder";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("proid",proid);//产品id:10000(中石化50元加油卡)[暂不支持]、10001(中石化100元加油卡)、10003(中石化500元加油卡)、10004(中石化1000元加油卡)、10007(中石化任意金额充值)[暂不支持]、10008(中石油任意金额充值)
        if(proid != 10007 && proid != 10008) {
            params.put("cardnum","1");//充值数量（产品id为10007、10008时为具体充值金额(整数)，其余产品id请传固定值1）；注：中石油任意冲(产品id:10008)暂时只支持100\200\500\1000
        }
        params.put("cardnum",cardNum);
        params.put("orderid",orderId);//商家订单号，8-32位字母数字组合
        params.put("game_userid",game_userid);//加油卡卡号，中石化：以100011开头的卡号、中石油：以9开头的卡号
        params.put("gasCardTel",gasCardTel);//持卡人手机号码
        params.put("gasCardName",gasCardName);//持卡人姓名
        params.put("chargeType",chargeType);//加油卡类型 （1:中石化、2:中石油；默认为1)
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        String sign = MD5Util.MD5(OPENID + APPKEY + proid + cardNum + game_userid + orderId).toLowerCase();
        if(sign == null) {
            return "校验值生成失败";
        }
        params.put("sign",sign);//校验值，md5(OpenID+key+proid+cardnum+game_userid+orderid)，OpenID在个人中心查询
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
                return result;
            }else{
                //System.out.println(object.get("error_code")+":"+object.get("reason"));
                return (object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "充值异常";
        }
    }
    //按日期查询订单
    public  static String queryByDate(int page,int pageSize,String startTime,String endTime) {
        String result =null;
        String url ="http://op.juhe.cn/ofpay/sinopec/sordersbydate";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("page",page);
        params.put("pagesize",pageSize);
        params.put("starttime",startTime);
        params.put("endtime",endTime);
        String timestamp = new Date().getTime() + "";
        params.put("timestamp",timestamp);
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        //md5(OpenID+key+starttime+endtime+timestamp)
        String sign = MD5Util.MD5(OPENID + APPKEY + startTime + endTime + timestamp);
        if(sign == null) {
            return "校验值生成失败";
        }
        params.put("sign",sign);
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
                return result;
            }else{
                //System.out.println(object.get("error_code")+":"+object.get("reason"));
                return (object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "充值异常";
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
