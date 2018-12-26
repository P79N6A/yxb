package com.yxbkj.yxb.util.wxpay.util;
public class PayConfigUtil {  
	/**
	 * 服务号相关信息
	 */
    // 第三方用户唯一凭证密钥
    public static String appsecret = "892df3e02e911494d29e84580ebcbc4e";
    //获取openId
    public static String oauth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	 public final static String SCOPE = "snsapi_userinfo";//应用授权作用域
	 public final static String APP_ID = "wxfa5e7a563ca2d87c";//公众号appid
	 public final static String CREATE_IP = "";//发起支付ip
	 public final static String MCH_ID = "1502915591";//商户号
	 public final static String API_KEY = "a8w56G7854Gg87r62s123st96jSjkUmg";//api key
	 public final static String NOTIFY_URL = "http://app.ybw100.com:9991/notify/wxh5Notify";//回调地址
	public final static String NOTIFY_URL_RECHARGE = "http://app.ybw100.com:9991/notify/wxh5NotifyRecharge";//回调地址
	public final static String UFDODER_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";//微信开发平台应用id

} 