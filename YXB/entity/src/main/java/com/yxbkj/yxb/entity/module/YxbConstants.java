package com.yxbkj.yxb.entity.module;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YxbConstants implements Serializable {
    /**
     * 系统名称
     */
    public static final String SYSTEM_NAME = "易小保";
    /**
     * 系统异常状态码
     */
    public static final String SYSTEM_ERROR_CODE = "500";
    /**
     * 数据有效性 正常
     */
    public static final String DATA_NORMAL_STATUS_CODE = "10000001";
    /**
     * 有效字段
     */
    public static final String DATA_NORMAL_FILED = "validity";
    /**
     * 签到活动类型  易豆签到
     */
    public static final String ACTIVETYPE_YIDOU_SIGN = "10000591";
    /**
     * 签到活动类型  易豆支付
     */
    public static final String ACTIVETYPE_YIDOU_PAY = "10000592";

    /**
     * 数据有效性 无效
     */
    public static final String DATA_DELETE_STATUS_CODE = "10000002";
    /**
     * 违章已处理
     */
    public static final String CAR_HANDED = "10000621";
    /**
     * 违章未处理
     */
    public static final String CAR_NO_HANDED = "10000622";

    /**
     * 已从业认证
     */
    public static final String IEC_STATUS_TYPE_YES = "10000151";
    /**
     * 未从业认证
     */
    public static final String IEC_STATUS_TYPE_NO = "10000152";
    /**
     * 已认证
     */
    public static final String MEMBER_TYPE_YES = "10000131";
    /**
     * 未认证
     */
    public static final String MEMBER_TYPE_NO = "10000132";

    /**
     * 产品解读
     */
    public static final String ARTICLE_TYPE_PRODUCT = "10000684";

    /**
     * 文章
     */
    public static final String ARTICLE_TYPE_NEWS = "10000681";
    /**
     * 充值类型 加油卡
     */
    public static final String FUELCARD = "10000444";
    /**
     * 充值类型 手机
     */
    public static final String PHONENUM = "10000445";

    /**
     * 评论
     */
    public static final String ARTICLE_TYPE_COMMENT = "10000682";
    /**
     * 产品上架
     */
    public static final String ISDEFAULT = "10000461";
    /**
     * 产品下架
     */
    public static final String NOTISDEFAULT = "10000462";



    public static final String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String DDEFAULT_MEMBERLEVRL = "10000141";//会员默认等级  会员
    public static final String DDEFAULT_MEMBERLEVRL_HEHUOREN = "10000142";//会员等级  合伙人
    public static final String DDEFAULT_MEMBERLEVRL_TOUZIREN = "10000143";//会员等级  投资人
    public static final String ORDER_CHUDAN = "10000531";//订单 已出单
    public static final String ORDER_NO_CHUDAN = "10000532";//订单 未出单
    public static final String ORDER_PAY_YES = "10000521";//订单 已支付
    public static final String ORDER_PAY_NO = "10000522";//订单 未支付
    public static final String ORDER_FENYONG_NO = "10000392";//订单 未分佣
    public static final String PAY_WAY_ZFB = "10000542";//支付方式 支付宝
    public static final String PAY_WAY_WX = "10000543";//支付方式 微信
    public static final String PAY_WAY_YQB = "10000548";//支付方式 易钱包
    public static final String PAY_WAY_YIDOU = "10000550";//支付方式 易豆
    public static final String PAY_WAY_YIBAO = "10000551";//支付方式 易宝
    public static final String CASH_INITED = "10000422";//提现方式  提现初审
    public static final String ORDER_TYPE_PRO = "10000442";//非车险订单
    public static final String COLLECTION_TYPE = "10000684";//点赞收藏类型
    public static final String INVITE_ACTIVITY_NO = "ACTI20180921181818001";//邀请活动ID



    public static final String MEMBER_ID_INDEXCHA = "M";//会员开头的字母
    public static final String BUYMEMBER_CONFIG = "buyMember";//购买会员配置串
    public static final String LIMIT_TIME_FOR_MONEY = "limitTimeForMoney";//会员到期时间配置
    public static final String DEFAULT_HEAD_IMG = "/file/yxb_default_headphoto.png?f_id=23b68285d4794f4a8b18dc6ca5ca2067";//默认头像
    public static final String DEFAULT_NICK_NAME = "请修改昵称";//默认昵称

    public static final String MEMBER_SOURCE_WX = "10000012";//会员来源 微信
    public static final String WX_TEMPLATE_ADD_MEMBER = "e4YWj0axR5UHacko1enVH0TGSwujBxZdPEw7HS44_2g";//微信推送模板ID 增员
    public static final String WX_TEMPLATE_ADD_BUY_MEMBER = "qJhg5JLvQsinch-044i8Vc-gT-QcJJ62Fc9oHTrfb6Q";//微信推送模板ID 会员升级
    public static final String WX_TEMPLATE_ORDER_CHUDAN = "OfPPLuM6aOdd2gu9Hu3m5r1p88PUSdjt9PGsXY8ahuo";//微信推送模板ID 下单通知



    public static String sysDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public static String sysDateNow() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String sysDate(Long add) {
        Date currentTime = new Date();
        currentTime.setTime(currentTime.getTime()+add);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String sysNextDayDate(Long add) {
        Date currentTime = new Date();
        currentTime.setTime(currentTime.getTime()+add);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString+" 23:59:59";
    }

    public static String sysAddDate(Long add,Long add2) {
        Date currentTime = new Date();
        currentTime.setTime(add2+add);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString+" 23:59:59";
    }

    public static String sysDate(String str) {
        Date currentTime = new Date();
        String dateString = "";
       try{
           currentTime.setTime(Long.parseLong(str));
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(currentTime);
       }catch (Exception e){
           e.printStackTrace();
       }
        return dateString;
    }

    /**
     * 分页最大页码
     */
    public static final int MAX_PAGE_CURRENT = 100;
    /**
     * 分页每页最大数据量限制
     */
    public static final int MAX_PAGE_LIMIT = 100;
    /**
     * 会员注册情况直线图统计周数
     */
    public static final int CHART_LINE_WEEK_COUNT = 12;

    /**
     * 请求正常码
     */
    public static final String OK_CODE = "200";
    /**
     * 请求参数错误码
     */
    public static final String PARAMETER_ERROR_CODE = "400";
    /**
     * 禁止服务错误码
     */
    public static final String FORBIDEN_CODE = "403";
    /**
     * 首选卡
     */
    public static final String CARD_PREFERRED = "10000037";
    /**
     * 未首选卡
     */
    public static final String CARD_NOT_PREFERRED = "10000038";
    /**
     * 提现状态
     */
    public static final String PAY_STATUS_TYPE = "PAY_STATUS_TYPE";
    /**
     * 用户来源
     */
    public static final String SOURCE_TYPE = "SOURCE_TYPE";
    /**
     * 所属银行
     */
    public static final String BANK_CODE = "bankCode";
    /**
     * 支付方式
     */
    public static final String PAY_TYPE = "PAY_TYPE";
    /**
     * 默认的申请审核人的ID
     */
    public static final String DEFAULT_AUDIT_USER = "000000";

}
