package com.yxbkj.yxb.util;

import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.util.wxbus.TemplateData;
import com.yxbkj.yxb.util.wxbus.WX_TemplateMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员工具类
 */
public class MemberUtils {
    private static Logger logger = LoggerFactory.getLogger(MemberUtils.class);
    /**
     * 发送增员推送信息  红包
     * @param memberInfo
     * @param parentInfo
     */
    public static void sendAddMemberInfoRedPack(MemberInfo memberInfo,MemberInfo parentInfo,String extra){
        try{
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("恭喜您，邀请好友成功，获得邀请红包一个。","#696969"));
            String phone = memberInfo.getPhone();
            param.put("keyword1",new TemplateData(phone.replace(phone.substring(3,7),"****"),"#696969"));
            param.put("keyword2",new TemplateData(memberInfo.getRegisterTime(),"#696969"));
            param.put("remark",new TemplateData("请到个人中心查看！","#696969"));
            WX_TemplateMsgUtil.sendMsg(parentInfo.getOpenId(),YxbConstants.WX_TEMPLATE_ADD_MEMBER,"","#000000",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送推送信息异常"+e.getMessage());
        }
    }

    /**
     * 发送增员推送信息 普通
     * @param memberInfo
     * @param parentInfo
     */
    public static void sendAddMemberInfoUsual(MemberInfo memberInfo,MemberInfo parentInfo,String extra){
        try{
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("您通过邀请码成功增员一名团队成员!"+extra,"#696969"));
            String phone = memberInfo.getPhone();
            param.put("keyword1",new TemplateData(phone.replace(phone.substring(3,7),"****"),"#696969"));
            param.put("keyword2",new TemplateData(memberInfo.getRegisterTime(),"#696969"));
            param.put("remark",new TemplateData("请到团队成员中查看具体信息！","#696969"));
            WX_TemplateMsgUtil.sendMsg(parentInfo.getOpenId(),YxbConstants.WX_TEMPLATE_ADD_MEMBER,"","#000000",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送推送信息异常"+e.getMessage());
        }
    }

    /**
     * 发送会员升级信息
     * @param memberInfo
     * @param parentInfo
     */
    public static void sendBuyMemberInfo(MemberInfo memberInfo,MemberInfo parentInfo){
        try{
            String name = "";
            String memberLevel = "";
            if(!StringUtil.isEmpty(memberInfo.getMemberName())){
                name = memberInfo.getMemberName();
            }
            if(StringUtil.isEmpty(name)){
                name = memberInfo.getPhone();
            }
            if("10000142".equals(memberInfo.getMemberlevel())){
                memberLevel = "合伙人";
            }
            if("10000143".equals(memberInfo.getMemberlevel())){
                memberLevel = "资深合伙人";
            }
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("您的团员\""+name+"\"已升级完成！","#696969"));
            param.put("keyword1",new TemplateData(memberInfo.getModifierTime(),"#696969"));
            param.put("keyword2",new TemplateData(memberInfo.getPhone(),"#696969"));
            param.put("remark",new TemplateData("您的团员已经升级为"+memberLevel+"账户~","#696969"));
            logger.info("发送推送信息"+parentInfo.getOpenId());
            WX_TemplateMsgUtil.sendMsg(parentInfo.getOpenId(),YxbConstants.WX_TEMPLATE_ADD_BUY_MEMBER,"","#000000",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送推送信息异常"+e.getMessage());
        }
    }

    /**
     * 订单出单通知
     * @param openId
     * @param orderNo
     * @param productName
     */
    public static void sendPayOrderInfo(String openId,String orderNo,String productName){
        try{
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("您的订单已下单成功。","#696969"));
            param.put("keyword1",new TemplateData(orderNo,"#696969"));
            param.put("keyword2",new TemplateData(productName,"#696969"));
            param.put("remark",new TemplateData("感谢您的使用。","#696969"));
            WX_TemplateMsgUtil.sendMsg(openId,YxbConstants.WX_TEMPLATE_ORDER_CHUDAN,"","#000000",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送推送信息异常"+e.getMessage());
        }
    }

}
