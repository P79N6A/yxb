package com.yxbkj.yxb.util;

/**
 * 易小保配置类
 */
public class YxbConfig {
    public  static  String active = "dev";//激活环境

    //友盟配置
    public  static String UMENG_KEY = "5b90e020b27b0a7bd30001b1";
    public  static String UMENG_SECRET = "5gvjlbtpv4bpyvrwisboecvz1crrpfew";
    //通用的start
    //public static String BORROW_WS_URL = "http://123.124.175.228:8050//frontend/services/ProposalSaveServiceV4X.ProposalSaveServiceV4XHttpSoap12Endpoint";//借款人意外险ws地址

    //通用的end
    //正式环境
    public  static  String  pingan_return_url="http://webapp.ybw100.com/20170721/kegugg.html";
    public  static  String  pingan_notify_url="https://app.ybw100.com/notify/pingAnNotify";
    public  static  String   zr_shareUrl="http://yxbwx.ybw100.com";
    public  static  String   zr_backUrl="http://app.ybw100.com/notify/zhongRuiNotify";
    //测试环境
    public  static  String  pingan_return_url_dev="http://webapp.ybw100.com/20170721/kegugg.html";
    public  static  String  pingan_notify_url_dev="https://testapp.ybw100.com/notify/pingAnNotify";
    public  static  String   zr_shareUrl_dev="http://yxbwx.ybw100.com";
    public  static  String   zr_backUrl_dev="http://testapp.ybw100.com/notify/zhongRuiNotify";


    //public final static String APPID = "wxfa5e7a563ca2d87c";// 应用号
   // public final static String MCH_ID = "1502915591";// 商户号 xxxx 公众号商户id
    public static String getWxAPPID(){
        return "prod".equals(active)?"wxfa5e7a563ca2d87c":"wx4e59a5acfdd5d49f";
    }
    public static String getWxMCH_ID(){
        return "prod".equals(active)?"1502915591":"1502915591";
    }


    // public static final String WX_APPID = "wxfa5e7a563ca2d87c";//微信公众号APPID
    //public static final String WX_APPSECRET = "cf8ba16ba924a8d93d2cc0f2173b33ad";//微信公众号APPSECRET
    public static String getWxPayAPPID(){
       return "prod".equals(active)?"wxfa5e7a563ca2d87c":"wx4e59a5acfdd5d49f";
    }
    public static String getWxWX_APPSECRET(){
        return "prod".equals(active)?"cf8ba16ba924a8d93d2cc0f2173b33ad":"d03db3e689cc9fbb5f65e74885c1c71e";
    }
    public static String getYeepayBuyMemberRedirect(){//获取易宝购买会员跳转
        return "prod".equals(active)?"http://yxbwx.ybw100.com/shenjiSuccess":"http://testwebapp.ybw100.com/shenjiSuccess";
    }
    public static String getYeepayRechargeRedirect(){//获取易生活充值跳转
        return "prod".equals(active)?"http://yxbwx.ybw100.com/shenjiSuccess":"http://testwebapp.ybw100.com/shenjiSuccess";
    }
    public static String getYeepayBuyMemberNotify(){//获取易宝购买会员回调
        return "prod".equals(active)?"https://app.ybw100.com/notify/yeepayBuyMemberNotify":"https://testapp.ybw100.com/notify/yeepayBuyMemberNotify";
    }
    public static String getYeepayRechargeNotify(){//获取易生活充值回调
        return "prod".equals(active)?"https://app.ybw100.com/notify/getYeepayRechargeNotify":"https://testapp.ybw100.com/notify/getYeepayRechargeNotify";
    }

    public static String getPinganApiUrl(){//获取平安apiurl
        return "prod".equals(active)?"https://mobile.health.pingan.com/ehis-hm":"https://test-mobile.health.pingan.com:42443/ehis-hm";
    }
    public static String getPinganApiKey(){//获取平安apiurl
        return "prod".equals(active)?"6GIXR58EZE3SHMOPW87UX9K7JKIYKFK8SUTZG9K9SFV2N4X3KR8A13M29CZ5OG81QU17GCDFMRYTQ1WG1I5CMJ2DS14J491EN97J6ORAOXZTN46GOS2NTZJW4EEL4PTK":"SXEZ7FBDBQROTQ9YU1VYCVGXM1796XJFLFECE4WCETWXZJW47DL4AQ3TMTQ116BBYDZI29HUQSL5LU7QV5ABXUHRPVC8ZUC5USVUUA3KR7VZ2UKGOE3YF551D99ZQ8XB";
    }

    public static String getBrrowWSUrl(){//借款人意外险webservice地址
        return "prod".equals(active)?"http://123.124.175.228:8006//frontend/services/ProposalSaveServiceV4X.ProposalSaveServiceV4XHttpSoap12Endpoint":"http://123.124.175.228:8050//frontend/services/ProposalSaveServiceV4X.ProposalSaveServiceV4XHttpSoap12Endpoint";
    }

    public static String getDownloadWSUrl(){//借款人意外险webservice下载保单地址
        //http://123.124.175.228:8050//frontend/services/DLTEPolicyDownloadService?wsdl保单下载地址 ws地址
        return "prod".equals(active)?"http://123.124.175.228:8006//frontend/services/DLTEPolicyDownloadService.DLTEPolicyDownloadServiceHttpSoap11Endpoint":"http://123.124.175.228:8050//frontend/services/DLTEPolicyDownloadService.DLTEPolicyDownloadServiceHttpSoap11Endpoint";
    }


    public static String getGsPayUrl(){//借款人意外险 国寿支付地址
        return "prod".equals(active)?"http://gpicpay.chinalife-p.com.cn/clppay/_cdMAction.do":"http://106.37.195.142:5120/clppay/_cdMAction.do";
    }

    public static String getGsMd5Key(){//借款人意外险 国寿加密key
        return "prod".equals(active)?"yxbb123456":"123456";
    }


    public static String getGsCallUrl(){//借款人意外险 国寿前台通知地址
        return "prod".equals(active)?"http://app.ybw100.com/notify/gsPayReturn":"http://testapp.ybw100.com/gspayres";
    }

}
