package com.yxbkj.yxb.util.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Charsets;
import com.yxbkj.yxb.util.YxbConfig;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;

public class WxUtils {

    public static void main(String[] args) {
        //app
        String stringObjectSortedMap = new WxUtils().unifiedOrder("1", 1);
        String id = System.currentTimeMillis()+"";
        //js
        UnifiedOrderResponseData unifiedOrderResponseData = new WxUtils().unifiedOrder("1", "1", 1, "1", "1", 1);
        int i = 0;
    }


    /**
     * 调用微信统一下单接口,返回客户端数据
     *
     * @param userId
     * JSAPI支付
     * @return UnifiedOrderResponseData
     */
    private UnifiedOrderResponseData unifiedOrder(String userId, String proId,int price, String ip, String openid,int type) { // proId 问题id
        String orderId = null;
        if (type == 1) {
            orderId = System.currentTimeMillis()+"";
        } else if (type == 2) {
            orderId = proId;// 本地订单号
        }
		/*if (PayApp.theApp.isDebug()) {// 测试时候支付一分钱，买入价值6块的20分钟语音
			price = 1;//1分钱
		}*/
        // 生成请求数据对象
        UnifiedOrderRequestData data = constructData(orderId, price, ip, openid);
        // 调用微信统一下单接口
        UnifiedOrderResponseData responseData = WxPayUtil.unifiedOder(data);
        System.out.println( "H5 的响应 JSON 返回数据"+JSONObject.toJSONString(responseData));
        //LoggerUtils.payLogger.info("UnifiedOrderResponseData => "+ JSONObject.toJSONString(responseData));
        return responseData;
    }

    /**
     * 构建统一下单参数，发给微信服务器
     *
     * @param tradeNo
     * @param totalFee
     * @param tradeNo
     * @param ip
     * @return
     */
    private UnifiedOrderRequestData constructData( String tradeNo,int totalFee,String ip,String openid) {
        UnifiedOrderRequestData data = new UnifiedOrderRequestData.
                UnifiedOrderReqDataBuilder(
                WeixinConstant.FEBDA_PAY_BODY, tradeNo, totalFee, ip,
                WeixinConstant.TRADE_TYPE).setOpenid(openid).build();
        // 产生签名信息
        data.setSign(WxPayUtil.getSign(data));
        return data;
    }


    /**
     * 微信预支付 统一下单入口 ANDROID IOS
     * @param ip
     * @param price
     * @return
     */
    public  String unifiedOrder( String ip,int price) {
        try {
            // 设置订单参数
            //咪咕体验会员 支付1角购买20秒
            String orderId = "qwe";
            SortedMap<String, Object> parameters = prepareOrder(ip, orderId,price);
            parameters.put("sign", PayCommonUtil.createSign(Charsets.UTF_8.toString(), parameters));// sign签名 key
            //parameters.put("sign","D144D8A4DF49BBCC8A3D93CF707A5911");// sign签名 key

            String requestXML = PayCommonUtil.getRequestXml(parameters);// 生成xml格式字符串
            String responseStr = HttpUtil.httpsRequest(
                    ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);// 带上post
            // 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
            if (!PayCommonUtil.checkIsSignValidFromResponseString(responseStr)) {
                //LoggerUtils.payLogger.error("微信统一下单失败,签名可能被篡改 "+responseStr);
                return "RestResult.fail( 统一下单失败 );";
            }
            // 解析结果 resultStr
            SortedMap<String, Object> resutlMap = XMLUtil.doXMLParse(responseStr);
            if (resutlMap != null && WeixinConstant.FAIL.equals(resutlMap.get("return_code"))) {
               // LoggerUtils.payLogger.error("微信统一下单失败,订单编号: " + orderId + " 失败原因:"+ resutlMap.get("return_msg"));
                return "RestResult.fail('统一下单失败');";
            }
            // 获取到 prepayid
            // 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
            SortedMap<String, Object> map = buildClientJson(resutlMap,"appId","mch_id");
            map.put("outTradeNo", orderId);
           // LoggerUtils.payLogger.info("统一下定单成功 "+map.toString());
            return "统一下定单成功";
        } catch (Exception e) {
                    //LoggerUtils.payLogger.error( "下订单异常com.fs.module.weixin.logic.WeixinLogic receipt(String userId,String proId,String ip)：{},{}",
                    //"用户："+userId + " 商品号:" + proId + " IP：" + ip, "失败原因"+e.getMessage());
            e.printStackTrace();
            return "RestResult.fail('预支付请求失败');"; // 抽离到统一错误码泪中 统一定一下
        }

    }
    /**
     * 生成订单信息
     *
     * @param ip
     * @param orderId
     * @return
     */
    private SortedMap<String, Object> prepareOrder(String ip, String orderId,int price) {
        Map<String, Object> oparams = ImmutableMap.<String, Object> builder()
                .put("appid", YxbConfig.getWxAPPID())// 服务号的应用号
                .put("body", WeixinConstant.PRODUCT_BODY)// 商品描述
                .put("mch_id", YxbConfig.getWxMCH_ID())// 商户号 ？
                .put("nonce_str", PayCommonUtil.CreateNoncestr())// 16随机字符串(大小写字母加数字)
                .put("out_trade_no", orderId)// 商户订单号
                .put("total_fee", price)// 支付金额 单位分 注意:前端负责传入分
                .put("spbill_create_ip", ip)// IP地址
                .put("notify_url", ConfigUtil.NOTIFY_URL) // 微信回调地址
                .put("trade_type", ConfigUtil.TRADE_TYPE)// 支付类型 app
                .build();
        return MapUtils.sortMap(oparams);
    }

    /**
     * 生成预付快订单完成，返回给android,ios唤起微信所需要的参数。
     *
     * @param resutlMap
     * @return
     * @throws UnsupportedEncodingException
     */
    private SortedMap<String, Object> buildClientJson(
             Map<String, Object> resutlMap
            ,String APPID
            ,String MCH_ID
    ) throws UnsupportedEncodingException {
        // 获取微信返回的签名
        Map<String, Object> params = ImmutableMap.<String, Object> builder()
                .put("appid", APPID)
                .put("noncestr", PayCommonUtil.CreateNoncestr())
                .put("package", "Sign=WXPay")
                .put("partnerid", MCH_ID)
                .put("prepayid", resutlMap.get("prepay_id"))
                .put("timestamp", DateUtils.getTimeStamp()) // 10 位时间戳
                .build();
        // key ASCII排序 // 这里用treemap也是可以的 可以用treemap // TODO
        SortedMap<String, Object> sortMap = MapUtils.sortMap(params);
        sortMap.put("package", "Sign=WXPay");
        // paySign的生成规则和Sign的生成规则同理
        String paySign = PayCommonUtil.createSign(Charsets.UTF_8.toString(), sortMap);
        sortMap.put("sign", paySign);
        return sortMap;
    }



}
