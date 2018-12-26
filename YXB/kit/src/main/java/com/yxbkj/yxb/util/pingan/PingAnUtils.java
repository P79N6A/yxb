package com.yxbkj.yxb.util.pingan;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.util.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author 李明
 * @Description: 平安E生保接口工具类
 * @date 2018年08月13日 上午11:44:45
 *
 */
public class PingAnUtils {
    //接口地址
    //const api_url = "http://test-mobile.health.pingan.com/ehis-hm";
    // public static String api_url = "https://test-mobile.health.pingan.com:42443/ehis-hm";
     public static String api_url =  "https://mobile.health.pingan.com/ehis-hm";//正式
    //渠道标志
    public static String  sign = "599";
    //渠道代码
    public static String  code = "YXB";
    //接口密钥
    //const keys = "Vz2yxXk13EbgXgXo";
    //const keys = "a3w3f8nMuUbANahF";
    //public static String  keys = "Vz2yxXk13EbgXgXo";//测试
    public static String  keys = "dQp18ciHivD4xcNA";//正式
    public static String  iv = "1234567890123456";

    public static void main(String[] args) throws Exception {
//        String  url = api_url+"/outChannel/qryPolByPolNo.do?c="+code;//保单查询
//        JSONObject requestJson = new JSONObject();
//        requestJson.put("policyNo","123");
//        String s = sendDataToPingAn(url, requestJson);
//        System.out.println("xxxxxxx"+s);
    }

    /**
     * 描述: 保险接口数据发送
     * 作者: 李明
     * 备注: 返回来的是三方接口的响应信息
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String sendDataToPingAn(String url,JSONObject json) throws Exception {
        json.remove("productIdSelf");
        json.remove("remark");
        json.remove("orderSource");
        json.remove("token");
        System.out.println("真实发送数据"+json.toJSONString());
        String encrypt = AESUtil.encrypt(json.toString(), keys);
        JSONObject requestJson = new JSONObject();
        requestJson.put("data",encrypt);
        requestJson.put("requestId","YXB"+System.currentTimeMillis());
        return sendPost(requestJson.toJSONString(), url);
    }
    /**
     * 描述: 签名验证
     * 作者: 李明
     * 备注:
     * @param map
     * @param sign
     * @return
     */
    public static boolean validityData(Map<String, String> map,String sign) {
        String signVeryfy = EPay.getSignVeryfy(map);
        if(sign.equals(signVeryfy)){
            return true;
        }
        return false;
    }

    /**
     * 发送POST
     * @param account
     * @param url
     * @return
     * @throws Exception
     */
    public static String sendPost(String account,String url) throws Exception {
        String jsonBody =account;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL smsUrl = new URL(url);
            URLConnection conn = smsUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.write(jsonBody);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 描述: 根据类型调用接口数据
     * 作者: 李明
     * 备注:
     * @param type 1保单查询 2保费试算 3核保 4承保
     * @param json
     * @return
     * @throws Exception
     */
    public static String sendDataToPingAnByType(int type,JSONObject json) throws Exception {
        String url = null;
        switch (type){
            case 1:
                url = api_url+"/outChannel/qryPolByPolNo.do?c="+code;//保单查询
                break;
            case 2:
                url = api_url+"/outChannel/calculatePremium.do?c="+code;//保费试算
                break;
            case 3:
                url = api_url+"/outChannel/validate.do?c="+code;//核保
                break;
            case 4:
                url = url = api_url+"/outChannel/accept.do?c="+code;//承保
                break;
        }
        return sendDataToPingAn(url,json);
    }

    /**
     * 描述: 根据data获取解密后的json数据
     * 作者: 李明
     * 备注:
     * @param data
     * @return
     */
    public static String decryptData(String data)  {
        String res = null;
        try{
            res =  AESUtil.decrypt(data, keys);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static String getOutChannel(String policyNo) {
        String url = api_url+"/outChannel/downloadPolicy.do?c="+code;//获取保单号地址
        try{
            url+="&policyNo="+URLEncoder.encode(AESUtil.encrypt(policyNo,keys),"UTF-8");
        }catch (Exception e){
            url+="&policyNo="+policyNo;
        }
        //$api = self::api_url."/outChannel/downloadPolicy.do?c=".self::code;
        //$url = $api."&policyNo=".urlencode(self::aes_deal($policyNo));
        return url;
    }
}
