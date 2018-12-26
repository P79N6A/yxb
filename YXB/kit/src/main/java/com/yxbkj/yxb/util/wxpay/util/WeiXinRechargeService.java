//package com.yxbkj.yxb.util.wxpay.util;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URLEncoder;
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.ofbiz.base.util.Debug;
//import org.ofbiz.base.util.UtilDateTime;
//import org.ofbiz.base.util.UtilMisc;
//import org.ofbiz.base.util.UtilProperties;
//import org.ofbiz.base.util.UtilValidate;
//import org.ofbiz.entity.Delegator;
//import org.ofbiz.entity.GenericEntityException;
//import org.ofbiz.entity.GenericValue;
//import org.ofbiz.service.LocalDispatcher;
//import org.ofbiz.webtools.applog.AppLogUtil;
//
//import com.brains.b2c.microshop.RechargeEvents;
//import com.brains.util.CommonUtil;
//import com.brains.util.HttpUtil;
//import com.brains.util.PayCommonUtil;
//import com.brains.util.PayConfigUtil;
//import com.brains.util.XMLUtil;
//
//import javolution.util.FastMap;
//import net.sf.json.JSONObject;
//
//
//public class WeiXinRechargeService {
//	private static String baseUrl = "http://www.gongjulai.com/m/control/";
//	public static final String module = WeiXinRechargeService.class.getName();
//	/**
//	 * 微信网页授权获取用户基本信息，先获取 code，跳转 url 通过 code 获取 openId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public static String userAuth(HttpServletRequest request, HttpServletResponse response){
//		try {
//			String orderId = request.getParameter("orderPaymentGroupId");
//			System.out.println("orderId:"+orderId);
//			//授权后要跳转的链接
//			String backUri = baseUrl + "/weiXinClient4Recharge";
//			backUri = backUri + "?orderId=" + orderId;
//			//URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
//			backUri = URLEncoder.encode(backUri,"UTF-8");
//			//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
//			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//					"appid=" + PayConfigUtil.APP_ID +
//					"&redirect_uri=" +
//					 backUri+
//					"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//			System.out.println("url:" + url);
//			response.sendRedirect(url);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "SUCCESS";
//	}
//	/**
//	 * 统一下单,跳转到微信支付页面,弹处输入密码界面
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public static String weiXinClient4Recharge(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		System.out.println("统一下单,跳转到微信支付页面,弹处输入密码界面");
//		String orderIdStr = request.getParameter("orderId");
//		String code = request.getParameter("code");
//		//获取openid
//		String openId ="";
//		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
//				+ PayConfigUtil.APP_ID + "&secret=" + PayConfigUtil.appsecret + "&code=" + code + "&grant_type=authorization_code";
//		System.out.println("URL:"+URL);
//		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
//		if (null != jsonObject) {
//			openId = jsonObject.getString("openid");
//			System.out.println("openId:" + openId);
//		}
//
//        Delegator delegator = (Delegator) request.getAttribute("delegator");
//        StringBuffer bodyStr = new StringBuffer();
//        bodyStr.append(orderIdStr);
//        String orderIds = "";
//        String totalPrice ="1";
//        if(UtilValidate.isNotEmpty(orderIdStr)){
//        	GenericValue orderPaymentGroup = delegator.findOne("OrderPaymentGroup", UtilMisc.toMap("orderPaymentGroupId", orderIdStr), false);
//        	if(UtilValidate.isNotEmpty(orderPaymentGroup)){
//        		orderIds = orderPaymentGroup.getString("orderIds");
//        		System.out.println("weiXinClient4HFAndLL:" + orderIds);
//        		request.setAttribute("orderId", orderIds);
//        	}else{
//        		Debug.logError("无效的支付请求编号:"+ orderIdStr, module);
//        	}
//        	GenericValue order = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId",orderIds), false);
//    		totalPrice =  (100*Double.parseDouble(order.getString("displayGrandTotal")))+"";
//
//        }
//        double parseDouble = Double.parseDouble(totalPrice);
//        int a = (int)parseDouble;
//        totalPrice = a+"";
//        System.out.println("显示的价钱：" + totalPrice);
//		String prepayId = weixinclient_pay(orderIdStr,totalPrice,bodyStr.toString(),openId);
//        //把后台数据返回给前端页面
//        SortedMap<Object,Object> payMap = new TreeMap<Object,Object>();
//        String key = PayConfigUtil.API_KEY; // api key
//        payMap.put("appId", PayConfigUtil.APP_ID);
//        payMap.put("timeStamp", create_timestamp());
//        payMap.put("nonceStr", create_nonce_str());
//        payMap.put("signType", "MD5");
//        payMap.put("package", "prepay_id=" + prepayId);
//        String sign = PayCommonUtil.createSign("UTF-8", payMap,key);
//        System.out.println("--------------------");
//        System.out.println("生成的签名paySign:"+sign);
//        payMap.put("paySign", sign);
//        String json = JSONObject.fromObject(payMap).toString();
//        System.out.println("返回给页面的参数json:"+json);
//        request.setAttribute("orderId", orderIds);
//        request.setAttribute("appId", payMap.get("appId"));
//        request.setAttribute("timeStamp", payMap.get("timeStamp"));
//        request.setAttribute("nonceStr", payMap.get("nonceStr"));
//        request.setAttribute("signType", payMap.get("signType"));
//        request.setAttribute("package", prepayId);
//        request.setAttribute("paySign", payMap.get("paySign"));
//		return "SUCCESS";
//	}
//
//	private static String create_timestamp() {
//        return Long.toString(System.currentTimeMillis() / 1000);
//    }
//	private static String create_nonce_str() {
//        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        String res = "";
//        for (int i = 0; i < 16; i++) {
//           Random rd = new Random();
//           res += chars.charAt(rd.nextInt(chars.length() - 1));
//        }
//        return res;
//	 }
//	@SuppressWarnings("rawtypes")
//	public static String weixinclient_pay(String orderId,String talPrice,String bodyStr,String openId) throws Exception {
//		// 账号信息
//        String appid = PayConfigUtil.APP_ID;  // 公众号appid
//        String mch_id = PayConfigUtil.MCH_ID; // 商户号
//        String key = PayConfigUtil.API_KEY; // api key
//        //生成随机字符串
//        String currTime = PayCommonUtil.getCurrTime();
//        String strTime = currTime.substring(8, currTime.length());
//        String strRandom = PayCommonUtil.buildRandom(4) + "";
//        String nonce_str = strTime + strRandom;
//        String order_price = talPrice; // 价格   注意：价格的单位是分
//        String body = bodyStr;   // 商品名称
////        int number= (int)(Math.random()*999)+1;
//
//        String out_trade_no = orderId; // 订单号
//        // 获取发起电脑 ip
//        String spbill_create_ip = PayConfigUtil.CREATE_IP;
//        // 回调接口
//        //String notify_url = PayConfigUtil.NOTIFY_URL;
//        String notify_url = UtilProperties.getPropertyValue("payment.properties", "payment.wechatpay.notifyUrl4Recharge").trim();
//        String trade_type = "JSAPI";
//
//        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
//        //公众号appid
//        packageParams.put("appid", appid);
//        //商户号
//        packageParams.put("mch_id", mch_id);
//        //随机字符串
//        packageParams.put("nonce_str", nonce_str);
//        //商品简单描述，该字段须严格按照规范传递
//        packageParams.put("body", body);
//        //商户系统内部的订单号,32个字符内、可包含字母
//        System.out.println("交易订单号:" + out_trade_no);
//        packageParams.put("out_trade_no", out_trade_no);
//        //订单总金额，单位为分
//        System.out.println("查看金额：" + order_price);
//        packageParams.put("total_fee", order_price);
//        //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
//        packageParams.put("spbill_create_ip", spbill_create_ip);
//        //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
//        packageParams.put("notify_url", notify_url);
//        //交易类型,取值如下：JSAPI，NATIVE，APP，
//        packageParams.put("trade_type", trade_type);
//        //用户的唯一标识openId
//        packageParams.put("openid", openId);
//        //调用PayCommonUtil生成签名算法,生成签名,传入编码格式,请求参数,appid
//        //(1)第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
//        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
//        //(2)第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
//        //再将得到的字符串所有字符转换为大写，得到sign值signValue。
//        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
//        //签名
//        packageParams.put("sign", sign);
//        //调用PayCommonUtil参数转义方法,CDATA标签用于说明数据不被XML解析器解析。
//        String requestXML = PayCommonUtil.getRequestXml(packageParams);
//        //调用HttpUtil连接微信服务器方法,传入微信开发平台应用id,请求参数,返回xml数据(微信服务器获取了一个支付url,生成的预支付交易单)
//        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);
//        //解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
//        System.out.println("resXml:"+resXml);
//        Map map = XMLUtil.doXMLParse(resXml);
//        //String return_code = (String) map.get("return_code");
//        //String prepay_id = (String) map.get("prepay_id");
//        //取出解析后的xml数据的二维码链接(trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付)
//        String prepayId = (String) map.get("prepay_id");
//        return prepayId;
//    }
//	/**
//	 * 微信客户端支付成功后方法
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//    public static String weixinclient_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
//    	LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
//		Delegator delegator = (Delegator) request.getAttribute("delegator");
//		Map<String, String> params = FastMap.newInstance();
//
//		String clazzMethodName = module + "#alipayNotify";//似乎是错误的
//		InputStream inputStream;
//		StringBuffer sb = new StringBuffer();
//		inputStream = request.getInputStream();
//		String s;
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//		while ((s = in.readLine()) != null) {
//			sb.append(s);
//		}
//		in.close();
//		inputStream.close();
//		// 解析xml成map
//		Map<String, String> m = new HashMap<String, String>();
//		m = XMLUtil.doXMLParse(sb.toString());
//		// 过滤空 设置 TreeMap
//		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
//		Iterator it = m.keySet().iterator();
//		while (it.hasNext()) {
//			String parameter = (String) it.next();
//			String parameterValue = m.get(parameter);
//
//			String v = "";
//			if (null != parameterValue) {
//				v = parameterValue.trim();
//			}
//			packageParams.put(parameter, v);
//		}
//		// 通知微信.回传信息.默认.
//		String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//				+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//		//文件名
//		String configString = "payment.properties";
//		//appid
//		String key = UtilProperties.getPropertyValue(configString, "payment.wechatpay.apiKey").trim();
//		if (!PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
//			AppLogUtil.log(dispatcher, "WXPAY_NOTIFY", "wxpay", "微信支付回调：签名验证失败", clazzMethodName, "system", params);
//			Debug.logError("签名验证失败[%s]", module, params.toString());
//			// 验证失败
//			out.write(resXml.getBytes());
//			out.flush();
//			return "error";
//		}
//		// 返回状态码是否成功
//		if (!"SUCCESS".equals((String) packageParams.get("result_code"))) {
//			out.write(resXml.getBytes());
//			out.flush();
//			return "error";
//		}
//		//-----------------------------------------成功后
//		String out_trade_no = (String) packageParams.get("out_trade_no");//支付订单号
//		String orderId = "";
//		String currentAmount = "";
//		String orderName = "";
//		String phoneNum = "";
//		try {
//			GenericValue orderPaymentGroup = delegator.findOne("OrderPaymentGroup", UtilMisc.toMap("orderPaymentGroupId", out_trade_no), false);
//			orderId = orderPaymentGroup.getString("orderIds");
//			currentAmount = orderPaymentGroup.getString("currentAmount");//当前实际充值话费
//			GenericValue orderHeader4HFAndLL = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId", orderId), false);
//			//查出order里的充值信息（流量、话费）
//			orderName = orderHeader4HFAndLL.getString("orderName");
//			//查出order里的号码信息
//			phoneNum = orderHeader4HFAndLL.getString("externalId");
//			if (UtilValidate.isNotEmpty(orderPaymentGroup) && UtilValidate.isNotEmpty(orderHeader4HFAndLL)) {
//				//状态更新
//				if(!"ORDER_FINISHED".equals(orderHeader4HFAndLL.getString("statusId"))){
//					//生成充值记录 partyId
//					RechargeEvents.executeRechargeCallBack(request,orderId);
//				}
//				List<GenericValue> toBeStored = new LinkedList<GenericValue>();
//				//--------------------------OrderPaymentGroup
//				GenericValue OrderPaymentGroup = delegator.makeValue("OrderPaymentGroup");
//				OrderPaymentGroup.set("orderPaymentGroupId", out_trade_no);
//				OrderPaymentGroup.set("paymentStatus", "ORDER_FINISHED");//将支付订单表中的支付状态改为finished
//				toBeStored.add(OrderPaymentGroup);
//				//--------------------------OrderHeader
//				GenericValue orderHeader = delegator.makeValue("OrderHeader");
//				Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
//				orderHeader.set("orderId",orderId);
//				orderHeader.set("payFlag","Y");//是否已支付，此时已支付
//				orderHeader.set("paymentMethodIds","wxpay");//支付方式Ids
//				orderHeader.set("paymentMethodNames","微信支付");//支付方式名称
//				orderHeader.set("statusId","ORDER_FINISHED");//支付状态
//				orderHeader.set("paidTime",nowTimestamp);//订单支付时间
//
//				toBeStored.add(orderHeader);
//				delegator.storeAll(toBeStored);
//			}
//		} catch (GenericEntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "error";
//		}
//
//		// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
//		resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
//				+ "</xml> ";
//		out.write(resXml.getBytes());
//		out.flush();
//		out.close();
//		request.setAttribute("orderPaymentGroupId", out_trade_no);
//		request.setAttribute("orderId", orderId);
//		request.setAttribute("price", currentAmount);
//		request.setAttribute("phoneNum", phoneNum);
//		return "success";
//	}
//}
