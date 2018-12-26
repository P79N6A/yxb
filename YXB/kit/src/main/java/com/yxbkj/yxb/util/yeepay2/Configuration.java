package com.yxbkj.yxb.util.yeepay2;

import java.util.ResourceBundle;


/**
 * @author lu.li
 *
 */
public class Configuration {
	

	private static Object lock              = new Object();
	private static Configuration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "merchantInfo";

    public static final String appId = "wx9e13bd68 a8f1921e";//微信公众号 appId 如果为公众号支付必填
    public static final String openId = "z ml_wechat";//微信公众号 openId
    public static final String clientId = "adasd";//网银对公银行客户号
    //public static final String REDIRECT_URL = "https://app.ybw100.com/notify/yeepayBuyMemberRedirect";// 页面回调地址
    //public static final String NOTIFY_URL ="https://app.ybw100.com/notify/yeepayBuyMemberNotify";// 服务器通知地址

	
	private Configuration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	public static Configuration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}
	
	public String getValue(String key) {
		return (rb.getString(key));
	}
}
