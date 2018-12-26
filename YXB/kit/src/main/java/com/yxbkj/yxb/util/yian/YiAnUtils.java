package com.yxbkj.yxb.util.yian;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.util.HttpUtil;
import com.yxbkj.yxb.util.YxbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class YiAnUtils {
    public static  String url = YxbConfig.active.equals("dev")?"http://183.60.22.143/":"http://open.1an.com/";//测试
    //public static  String url = "http://open.1an.com/sns/";//生产
    public static  String app_id = YxbConfig.active.equals("dev")?"D0C06F394C4F1D1D57426F012937F476":"26B1CB4F9C0E2B0F2F8AB919AA5D78C2";
    public static  String app_secret = YxbConfig.active.equals("dev")?"3981FE2B883A1F4EC864CDF9DB735901":"D966A4F9BD3F6A87DFBEC3E2CFC94597";
    public static String authorize = YxbConfig.active.equals("dev")?"sns/oauth2/authorize":"sns/oauth2/authorize";//第一步  鉴权  获取 tooken
    public static String token_refresh = YxbConfig.active.equals("dev")?"sns/oauth2/token_refresh":"sns/oauth2/token_refresh";//第一步（附加 刷新access_token(sns/oauth2/ token_refresh)
    public static  String create_order = YxbConfig.active.equals("dev")?"sns/dy-order-service-dev":"sns/dy-order-service";//创建订单
    public static  String pay_order = YxbConfig.active.equals("dev")?"sns/pay-service-dev":"sns/pay-service";//支付订单

    public static void main(String[] args) throws UnsupportedEncodingException {
        if(1==1){
            System.out.println(url);
            return;
        }
        JSONObject accessToken = getAccessToken();
        JSONObject order = createOrder(json, accessToken.getString("access_token"), accessToken.getString("open_id"));
        System.out.println(order.toJSONString());

    }

    public static   String json = "{\n" +
            "    \"agrtCode\": \"10011006002004\",\n" +
            "    \"requestTime\": \"2018-10-11 09:17:13\",\n" +
            "    \"dataSource\": \"O-SCYXB\",\n" +
            "    \"outBusinessCode\": \"NO00000002\",\n" +
            "    \"interfaceCode\": \"CreateOrder\",\n" +
            "    \"data\": {\n" +
            "        \"createOrderReq\": {\n" +
            "            \"orderList\": [\n" +
            "                {\n" +
          //  "                    \"projectCode\": \"AAA001\",\n" +
            "                    \"premium\": \"208.00\",\n" +
            "                    \"startDate\": \"2018-10-21 00:00:00\",\n" +
            "                    \"endDate\": \"2019-10-20 23:59:59\",\n" +
            "                    \"uwCount\": 1,\n" +
            "                    \"customerList\": [\n" +
            "                        {\n" +
            "                            \"customerSameInd\": \"\",\n" +
            "                            \"customerType\": \"1\",\n" +
            "                            \"docType\": \"01\",\n" +
            "                            \"sex\": \"01\",\n" +
            "                            \"customerFlag\": 1,\n" +
            "                            \"docNo\": \"532125199205040014\",\n" +
            "                            \"birthDate\": \"1992-05-04\",\n" +
            "                            \"email\": \"\",\n" +
            "                            \"customerName\": \"李明\",\n" +
            "                            \"phoneNo\": \"13000000000\"\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"itemAcciList\": [\n" +
            "                        {\n" +
            "                            \"nominativeInd\": \"1\",\n" +
            "                            \"occupationCode\": \"0101001\",\n" +
            "                            \"quantity\": \"1\",\n" +
            "                            \"rationType\": \"\",\n" +
            "                            \"acciInsuredList\": [\n" +
            "                                {\n" +
            "                                    \"acciBenefitList\": [\n" +
            "                                        {\n" +
            "                                            \"insuredRelation\": \"01\",\n" +
            "                                            \"docType\": \"01\",\n" +
            "                                            \"sex\": \"01\",\n" +
            "                                            \"customerFlag\": \"3\",\n" +
            "                                            \"docNo\": \"532125199205040014\",\n" +
            "                                            \"birthDate\": \"1992-05-04\",\n" +
            "                                            \"customerName\": \"李明\",\n" +
            "                                            \"benifitPercent\": \"100\",\n" +
            "                                            \"phoneNo\": \"13000000000\"\n" +
            "                                        }\n" +
            "                                    ],\n" +
            "                                    \"docType\": \"01\",\n" +
            "                                    \"sex\": \"01\",\n" +
            "                                    \"customerFlag\": \"2\",\n" +
            "                                    \"docNo\": \"532125199205040014\",\n" +
            "                                    \"birthDate\": \"1992-05-04\",\n" +
            "                                    \"appliRelation\": \"01\",\n" +
            "                                    \"customerName\": \"李明\",\n" +
            "                                    \"phoneNo\": \"13000000000\"\n" +
            "                                }\n" +
            "                            ]\n" +
            "                        }\n" +
            "                    ],\n" +
            "\t\t\t\t\t\"riskDynamicList\":[\n" +
            "                        {\n" +
            "                            \"fieldAM\":\"NO123\",\n" +
            "                            \"fieldAN\":\"2017-12-14 00:12:12\",\n" +
            "                            \"fieldAO\":\"北京\",\n" +
            "                            \"fieldAP\":\"上海\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";

    private static Logger logger = LoggerFactory.getLogger(YiAnUtils.class);

    public static JSONObject orderPay(JSONObject json,String access_token,String open_id) throws UnsupportedEncodingException {
//        JSONObject json = new JSONObject();
//        json.put("interfaceCode","yaH5Pay");
//        json.put("requestTime","2018-10-10 15:00:00");
//        json.put("dataSource","O-SCYXB");
//        json.put("agrtCode","10011006002004");
//        JSONObject data = new JSONObject();
//        data.put("payWay","01");
//        data.put("orderExt","738653380276125696");
//        data.put("orderCode","1049917198107148288");
//        data.put("redirectUrl","http://www.baidu.com");
//        json.put("data",data);
        logger.info("【易小保科技 订单支付  入参信息】"+url+"参数"+json.toJSONString()+" "+access_token+" "+open_id);
        String res_str = HttpUtil.doPostJson(url+pay_order+"?"+"access_token="+access_token+"&open_id="+open_id,null,json.toJSONString());
        return JSONObject.parseObject(URLDecoder.decode(res_str, "UTF-8"));
    }



    /**
     * 作者: 李明
     * 描述: 创建订单
     * 备注:
     * @param str 参数json串
     * @return
     */
    public static JSONObject createOrder(String str,String access_token,String open_id) throws UnsupportedEncodingException {
        String res_str = HttpUtil.doPostJson(url+create_order+"?"+"access_token="+access_token+"&open_id="+open_id,null,str);
        logger.info("【易小保科技 创建订单  入参信息】"+str+" "+access_token+" "+open_id);
        return JSONObject.parseObject(URLDecoder.decode(res_str, "UTF-8"));
    }


    /**
     * 作者: 李明
     * 描述: 获取AccessToken
     * 备注:
     * @return
     */
    public static JSONObject getAccessToken() {
        Map param = new HashMap();
        param.put("app_id",app_id);
        param.put("app_secret",app_secret);
        param.put("code","1anOpcode");
        param.put("grant_type","authorization_code");
        //String res_str = HttpUtil.doPostJson(url+authorize, null, json.toJSONString());
        String res_str = HttpUtil.doGet(url+authorize, param,false);
        //{"code":"0000","message":"success","state":"1","data":{"access_token":"ce5e0755c44c4e09b2d202c2a7b64561","refresh_token":"8d6ff17505194429bda6d97743cfa7d6","expires_in":0,"open_id":"7fe8078197834032afa1bb4d7ea70f46","scope":null,"interface_list":null}}
        JSONObject jsonObject = JSONObject.parseObject(res_str);
        if(!"0000".equals(jsonObject.getString("code"))){
            //获取token失败
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if(data.getIntValue("expires_in")==0){
            //需要调用刷新token接口
            return refreshAccessToken(data.getString("refresh_token"));
        }
        return data;
    }


    /**
     * 作者: 李明
     * 描述: 刷新AccessToken
     * 备注:
     * @return
     */
    public static JSONObject refreshAccessToken(String refresh_token){
        Map param = new HashMap();
        param.put("app_id",app_id);
        param.put("app_secret",app_secret);
        param.put("refresh_token",refresh_token);
        param.put("grant_type","refresh_token");
        String refresh_token_str = HttpUtil.doGet(url+token_refresh, param,false);
        JSONObject jsonObject = JSONObject.parseObject(refresh_token_str);
        if("0000".equals(jsonObject.getString("code"))){
            JSONObject data = jsonObject.getJSONObject("data");
            return data;
        }
        return null;
    }

}
