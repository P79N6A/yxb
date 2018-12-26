package com.yxbkj.yxb.util.wxbus;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WX_TemplateMsgUtil {
    private static Logger log = LoggerFactory.getLogger(WX_TemplateMsgUtil.class);


    public static void main(String[] args) {
        //新增用户成功 - 推送微信消息
        sendMsg("oaF6kwVRDMW2KLr9ICP87jt7PheY","GV9ORXy9_BR0-giU3P6TXS4P_IDJ4dfHgPbnYsuRPfo");

    }

    /**
     * 发送模板信息
     * @param openId
     * @param regTempId
     * @param clickurl
     * @param topcolor
     * @param param
     * @return
     */
    public static String sendMsg(String openId,String regTempId,String clickurl,String topcolor,Map<String,TemplateData> param){
        if(StringUtil.isEmpty(openId)){
            log.info("无法获取openid");
            return "无法获取openid!";
        }
        //用户是否订阅该公众号标识 (0代表此用户没有关注该公众号 1表示关注了该公众号)
        Integer  state= WX_UserUtil.subscribeState(openId);
        // 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信

        if(state==1){
            return WX_TemplateMsgUtil.sendWechatMsgToUser(openId, regTempId, clickurl, topcolor, packJsonmsg(param));
        }else{
            log.info(openId+"没有关注公众号");
            return "该账户尚未关注公众号!";
        }
    }


    /**
     * 发送模板信息
     * @param openId
     * @param regTempId
     */
    public static void sendMsg(String openId,String regTempId){
        //用户是否订阅该公众号标识 (0代表此用户没有关注该公众号 1表示关注了该公众号)
        Integer  state= WX_UserUtil.subscribeState(openId);
        // 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
        if(state==1){
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("明哥来也！","#696969"));
            param.put("keyword1",new TemplateData("15618551533","#696969"));
            param.put("keyword2",new TemplateData("2017年05月06日","#696969"));
            param.put("remark",new TemplateData("祝投资愉快！","#696969"));
            //注册的微信-模板Id
            //String regTempId =  getWXTemplateMsgId(WeiXinEnum.WX_TEMPLATE_MSG_NUMBER.USER_REGISTER_SUCCESS.getMsgNumber());
            //调用发送微信消息给用户的接口
            WX_TemplateMsgUtil.sendWechatMsgToUser(openId,regTempId, "", "#000000", packJsonmsg(param));
        }
    }

    /**
     * 封装模板详细信息
     * @return
     */
    public static JSONObject packJsonmsg(Map<String, TemplateData> param) {
        JSONObject json = new JSONObject();
        for (Map.Entry<String,TemplateData> entry : param.entrySet()) {
            JSONObject keyJson = new JSONObject();
            TemplateData dta=  entry.getValue();
            keyJson.put("value",dta.getValue());
            keyJson.put("color", dta.getColor());
            json.put(entry.getKey(), keyJson);
        }
        return json;
    }

    /**
     * 根据模板的编号 新增并获取模板ID
     * @param templateSerialNumber 模板库中模板的 "编号"
     * @return 模板ID
     */
    public static String getWXTemplateMsgId(String templateSerialNumber){
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="+ WX_TokenUtil.getAccessToken();
        JSONObject json = new JSONObject();
        json.put("template_id_short", templateSerialNumber);
        JSONObject result = WX_HttpsUtil.httpsRequest(tmpurl, "POST", json.toString());
        JSONObject resultJson = new JSONObject(result);
        String errmsg = (String) resultJson.get("errmsg");
        log.info("获取模板编号返回信息：" + errmsg);
        if(!"ok".equals(errmsg)){
            return "error";
        }
        String templateId = (String) resultJson.get("template_id");
        return templateId;
    }

    /**
     * 根据模板ID 删除模板消息
     * @param templateId 模板ID
     * @return
     */
    public static String deleteWXTemplateMsgById(String templateId){
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token="+ WX_TokenUtil.getAccessToken();
        JSONObject json = new JSONObject();
        json.put("template_id", templateId);
        try{
            JSONObject result = WX_HttpsUtil.httpsRequest(tmpurl, "POST", json.toString());
            JSONObject resultJson = new JSONObject(result);
            log.info("删除"+templateId+"模板消息,返回CODE："+ resultJson.get("errcode"));
            String errmsg = (String) resultJson.get("errmsg");
            if(!"ok".equals(errmsg)){
                return "error";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }


    /**
     * 发送微信消息(模板消息)
     * @param touser 用户 OpenID
     * @param templatId 模板消息ID
     * @param clickurl URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
     * @param topcolor 标题颜色
     * @param data 详细内容
     * @return
     */
    public static String sendWechatMsgToUser(String touser, String templatId, String clickurl, String topcolor, JSONObject data) {
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ WX_TokenUtil.getAccessToken();
        JSONObject json = new JSONObject();
        json.put("touser", touser);
        json.put("template_id", templatId);
        json.put("url", clickurl);
        json.put("topcolor", topcolor);
        json.put("data", data);
        try{
            JSONObject result = WX_HttpsUtil.httpsRequest(tmpurl, "POST", json.toString());
            JSONObject resultJson = new JSONObject(result);
            log.info("发送微信消息返回信息：" + resultJson.get("errcode"));
            String errmsg = (String) resultJson.get("errmsg");
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                return "error";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }finally {
            if(templatId!=null) {
                //删除新增的 微信模板
                //deleteWXTemplateMsgById(templatId);
            }
        }
        return "success";
    }
}
