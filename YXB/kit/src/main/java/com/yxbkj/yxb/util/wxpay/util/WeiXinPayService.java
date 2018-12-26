package com.yxbkj.yxb.util.wxpay.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
public class WeiXinPayService {

    public static void main(String[] args) {
        try {
            JSONObject jsonObject = weiXinH5Pay("oaF6kwQDjflpR3tHBEeRnb9hdHZk", "11121231", "1", "1","127.0.0.1");
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 统一下单,跳转到微信支付页面,弹处输入密码界面
	 * @return
	 * @throws Exception
	 */
	public static JSONObject weiXinH5Pay(String openId,String memberId,String memberLevel,String totalPrice,String ip) throws Exception{
		String orderIdStr = memberId;
        StringBuffer bodyStr = new StringBuffer();
        bodyStr.append(orderIdStr);
		String prepayId = weixinclient_pay(orderIdStr,totalPrice,bodyStr.toString(),openId,ip);
        //把后台数据返回给前端页面
        SortedMap<Object,Object> payMap = new TreeMap<Object,Object>();
        String key = PayConfigUtil.API_KEY; // api key  
        payMap.put("appId", PayConfigUtil.APP_ID);  
        payMap.put("timeStamp", create_timestamp());  
        payMap.put("nonceStr", create_nonce_str());  
        payMap.put("signType", "MD5");  
        payMap.put("package", "prepay_id=" + prepayId);  
        String sign = PayCommonUtil.createSign("UTF-8", payMap,key);
       //"生成的签名paySign
        payMap.put("paySign", sign);
        //返回给页面的参数
		JSONObject json = new JSONObject();
		json.put("orderId",orderIdStr);
		json.put("appId",payMap.get("appId"));
		json.put("timeStamp",payMap.get("timeStamp"));
		json.put("nonceStr", payMap.get("nonceStr"));
		json.put("signType",payMap.get("signType"));
		json.put("package",payMap.get("package"));
		json.put("paySign", payMap.get("paySign"));
		return json;
	}
    public static JSONObject weiXinH5PayRecharge(String openId,String memberId,String totalPrice,String ip) throws Exception{
        String orderIdStr = memberId;
        StringBuffer bodyStr = new StringBuffer();
        bodyStr.append(orderIdStr);
        String prepayId = weixinclient_pay_recharge(orderIdStr,totalPrice,bodyStr.toString(),openId,ip);
        //把后台数据返回给前端页面
        SortedMap<Object,Object> payMap = new TreeMap<Object,Object>();
        String key = PayConfigUtil.API_KEY; // api key
        payMap.put("appId", PayConfigUtil.APP_ID);
        payMap.put("timeStamp", create_timestamp());
        payMap.put("nonceStr", create_nonce_str());
        payMap.put("signType", "MD5");
        payMap.put("package", "prepay_id=" + prepayId);
        String sign = PayCommonUtil.createSign("UTF-8", payMap,key);
        //"生成的签名paySign
        payMap.put("paySign", sign);
        //返回给页面的参数
        JSONObject json = new JSONObject();
        json.put("orderId",orderIdStr);
        json.put("appId",payMap.get("appId"));
        json.put("timeStamp",payMap.get("timeStamp"));
        json.put("nonceStr", payMap.get("nonceStr"));
        json.put("signType",payMap.get("signType"));
        json.put("package",payMap.get("package"));
        json.put("paySign", payMap.get("paySign"));
        return json;
    }
	private static String create_timestamp() {  
        return Long.toString(System.currentTimeMillis() / 1000);  
    } 
	private static String create_nonce_str() {  
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        String res = "";  
        for (int i = 0; i < 16; i++) {  
           Random rd = new Random();  
           res += chars.charAt(rd.nextInt(chars.length() - 1));  
        }  
        return res;  
	 }
	public static String weixinclient_pay(String orderId,String talPrice,String bodyStr,String openId,String id) throws Exception {
		// 账号信息  
        String appid = PayConfigUtil.APP_ID;  // 公众号appid
        String mch_id = PayConfigUtil.MCH_ID; // 商户号  
        String key = PayConfigUtil.API_KEY; // api key  
        //生成随机字符串
        String currTime = PayCommonUtil.getCurrTime();  
        String strTime = currTime.substring(8, currTime.length());  
        String strRandom = PayCommonUtil.buildRandom(4) + "";  
        String nonce_str = strTime + strRandom;  
        String order_price = talPrice; // 价格   注意：价格的单位是分  
        String body = bodyStr;   // 商品名称  
//        int number= (int)(Math.random()*999)+1;
        
        String out_trade_no = orderId; // 订单号  
        // 获取发起电脑 ip  
        String spbill_create_ip = id;
        // 回调接口   
        //String notify_url = PayConfigUtil.NOTIFY_URL;  
        String notify_url = PayConfigUtil.NOTIFY_URL;  //UtilProperties.getPropertyValue("payment.properties", "payment.wechatpay.notifyUrl4Microshop").trim();
        String trade_type = "JSAPI";  
          
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        //公众号appid
        packageParams.put("appid", appid);  
        //商户号
        packageParams.put("mch_id", mch_id); 
        //随机字符串
        packageParams.put("nonce_str", nonce_str);  
        //商品简单描述，该字段须严格按照规范传递
        packageParams.put("body", body);  
        //商户系统内部的订单号,32个字符内、可包含字母
        System.out.println("交易订单号:" + out_trade_no);
        packageParams.put("out_trade_no", out_trade_no); 
        //订单总金额，单位为分
        System.out.println("查看金额：" + order_price);
        packageParams.put("total_fee", order_price); 
        //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
        packageParams.put("notify_url", notify_url); 
        //交易类型,取值如下：JSAPI，NATIVE，APP，
        packageParams.put("trade_type", trade_type);
        //用户的唯一标识openId
        packageParams.put("openid", openId);
        //调用PayCommonUtil生成签名算法,生成签名,传入编码格式,请求参数,appid
        //(1)第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
        //(2)第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
        //再将得到的字符串所有字符转换为大写，得到sign值signValue。
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key); 
        //签名
        packageParams.put("sign", sign);
        //调用PayCommonUtil参数转义方法,CDATA标签用于说明数据不被XML解析器解析。 
        String requestXML = PayCommonUtil.getRequestXml(packageParams);  
        //调用HttpUtil连接微信服务器方法,传入微信开发平台应用id,请求参数,返回xml数据(微信服务器获取了一个支付url,生成的预支付交易单)
        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);  
        //解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。   
        System.out.println("resXml:"+resXml);
        Map map = XMLUtil.doXMLParse(resXml);  
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id"); 
        //取出解析后的xml数据的二维码链接(trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付)
        String prepayId = (String) map.get("prepay_id");
        if(prepayId==null){
            throw new Exception(resXml);
        }
        return prepayId;  
    }
    public static String weixinclient_pay_recharge(String orderId,String talPrice,String bodyStr,String openId,String id) throws Exception {
        // 账号信息
        String appid = PayConfigUtil.APP_ID;  // 公众号appid
        String mch_id = PayConfigUtil.MCH_ID; // 商户号
        String key = PayConfigUtil.API_KEY; // api key
        //生成随机字符串
        String currTime = PayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        String order_price = talPrice; // 价格   注意：价格的单位是分
        String body = bodyStr;   // 商品名称
//        int number= (int)(Math.random()*999)+1;

        String out_trade_no = orderId; // 订单号
        // 获取发起电脑 ip
        String spbill_create_ip = id;
        // 回调接口
        //String notify_url = PayConfigUtil.NOTIFY_URL;
        String notify_url = PayConfigUtil.NOTIFY_URL_RECHARGE;  //UtilProperties.getPropertyValue("payment.properties", "payment.wechatpay.notifyUrl4Microshop").trim();
        String trade_type = "JSAPI";

        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        //公众号appid
        packageParams.put("appid", appid);
        //商户号
        packageParams.put("mch_id", mch_id);
        //随机字符串
        packageParams.put("nonce_str", nonce_str);
        //商品简单描述，该字段须严格按照规范传递
        packageParams.put("body", body);
        //商户系统内部的订单号,32个字符内、可包含字母
        System.out.println("交易订单号:" + out_trade_no);
        packageParams.put("out_trade_no", out_trade_no);
        //订单总金额，单位为分
        System.out.println("查看金额：" + order_price);
        packageParams.put("total_fee", order_price);
        //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        packageParams.put("spbill_create_ip", spbill_create_ip);
        //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
        packageParams.put("notify_url", notify_url);
        //交易类型,取值如下：JSAPI，NATIVE，APP，
        packageParams.put("trade_type", trade_type);
        //用户的唯一标识openId
        packageParams.put("openid", openId);
        //调用PayCommonUtil生成签名算法,生成签名,传入编码格式,请求参数,appid
        //(1)第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
        //(2)第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
        //再将得到的字符串所有字符转换为大写，得到sign值signValue。
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
        //签名
        packageParams.put("sign", sign);
        //调用PayCommonUtil参数转义方法,CDATA标签用于说明数据不被XML解析器解析。
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        //调用HttpUtil连接微信服务器方法,传入微信开发平台应用id,请求参数,返回xml数据(微信服务器获取了一个支付url,生成的预支付交易单)
        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);
        //解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
        System.out.println("resXml:"+resXml);
        Map map = XMLUtil.doXMLParse(resXml);
        //String return_code = (String) map.get("return_code");
        //String prepay_id = (String) map.get("prepay_id");
        //取出解析后的xml数据的二维码链接(trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付)
        String prepayId = (String) map.get("prepay_id");
        if(prepayId==null){
            throw new Exception(resXml);
        }
        return prepayId;
    }

}
