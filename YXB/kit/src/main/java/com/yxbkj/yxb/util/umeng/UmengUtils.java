package com.yxbkj.yxb.util.umeng;

import com.alibaba.fastjson.JSON;
import com.yxbkj.yxb.util.YxbConfig;
import com.yxbkj.yxb.util.umeng.android.*;
import com.yxbkj.yxb.util.umeng.ios.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;


public class UmengUtils {

	private  static Logger loggers = LoggerFactory.getLogger(UmengUtils.class);

	private String appkey = null;
	private String appMasterSecret = null;
	private String timestamp = null;
	private PushClient client = new PushClient();
	
	public UmengUtils(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void sendAndroidBroadcast() throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setTicker( "Android broadcast ticker");
		broadcast.setTitle(  "中文的title");
		broadcast.setText(   "Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendAndroidUnicast() throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "your device token");
		unicast.setTicker( "Android unicast ticker");
		unicast.setTitle(  "中文的title");
		unicast.setText(   "Android unicast text");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}
	
	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("M100001587", "ALIAS_LOGIN_NAME");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidCustomizedcast(String memberId, String title , String content, Map<String,String> map) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(memberId, "ALIAS_LOGIN_NAME");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  title);
		customizedcast.setText(   content);
		JSONObject json = new JSONObject();
		customizedcast.setCustomField(json);

		Set<String> keys = map.keySet();
		for(String key : keys){
			customizedcast.setExtraField(key,map.get(key));
		}
		//customizedcast.goAppAfterOpen();
		customizedcast.goCustomAfterOpen(json);
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setTicker( "Android filecast ticker");
		filecast.setTitle(  "中文的title");
		filecast.setText(   "Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}
	
	public void sendIOSBroadcast() throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendIOSUnicast() throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge( 0);
		groupcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		groupcast.setTestMode();
		client.send(groupcast);
	}
	
	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("M100000317", "易小保");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
	public void sendIOSFilecast() throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge( 0);
		filecast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		filecast.setTestMode();
		client.send(filecast);
	}
	
	public static void main(String[] args) throws Exception {
		if(1==1){
			UmengUtils demo = new UmengUtils("5b90e020b27b0a7bd30001b1", "5gvjlbtpv4bpyvrwisboecvz1crrpfew");
			//demo.sendAndroidUnicast();
			demo.sendAndroidCustomizedcast("M100001587","世界谁最帅","赵洲第二帅!",null);
			return;
		}

		if(1==1){
			//sendAndroidMessage();
			return;
		}

		// TODO set your appkey and master secret here
		//Demo demo = new Demo("5b90e020b27b0a7bd30001b1", "5gvjlbtpv4bpyvrwisboecvz1crrpfew");
		UmengUtils demo = new UmengUtils("5bd0397cf1f556fbf20001f4", "g0zeii7ukcvunc9xgsssi3f2l5yctq0m");


		try {
			demo.sendAndroidUnicast();
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSBroadcast();
			 * demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
			//demo.sendAndroidCustomizedcast("M100001587","世界谁最帅","赵洲第二帅!");
			demo.sendIOSCustomizedcast();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 作者: 李明
	 * 描述: 发送Android推送消息
	 * 备注:
	 * @param memberId  会员ID
	 * @param title 标题
	 * @param content 内容
	 * @param map 自定义的键值对
	 */
	public  static  void sendAndroidMessage(String memberId,String title,String content,Map<String,String> map){
		try{
			loggers.info("开始发送Android消息");
			UmengUtils demo = new UmengUtils(YxbConfig.UMENG_KEY, YxbConfig.UMENG_SECRET);
//			Map<String,String> map = new HashMap<>();
//			map.put("type","1");
//			map.put("questionId","1");
			loggers.info("开始发送Android消息"+title+"   "+content+"   "+JSON.toJSONString(map));
			demo.sendAndroidCustomizedcast(memberId,title,content,map);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

}
