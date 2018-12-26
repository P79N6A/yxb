package com.yxbkj.yxb.util.zrbx;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.util.Configurations;
import com.yxbkj.yxb.util.MemberUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.YxbConfig;
import com.yxbkj.yxb.util.zhongan.RC4Util;
import com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 中瑞产品工具类
 */
public class ZRBXProductUtils {

    private static Logger logger = LoggerFactory.getLogger(ZRBXProductUtils.class);
    public static  String baseUrl =ZRBXUtils.zr_host+"/outorder/promoteEntrance.html";
    //中瑞公钥
    public static  String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHumirhcj7FgXWVgNzASI4r9Kqm+KC4i4Jo18SwZx7Jhq11gVWTvn3HDf7x/VsI5x6AeS0OxOorEL2NTHjEck3mfpLaNCVVpe+66UkYT5qWJB0gA/CC3BW2oAFbt5ocvekNvcWaBfttJ/pe/G7TxJaQoJ/3CxpQfcTGHkmZIg0HQIDAQAB";
    //易小宝公钥
    String public_key_yxb = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV4ZGbqMHpLFgfwZnpRApv9n1o\n" +
            "tqE3jT8u0zjJ370eoYV2Tqo04FtNnCxxe5QVhBwHWrIlZpYESuXq7m/BVt6zLf7y\n" +
            "lBngJtB7d+EA6qYiCiYdRd2/44o2h+82k+PsWqVxaeHQQEkuB3YArz0dcHvyRw4o\n" +
            "rXtjFfExAXfAlraGSQIDAQAB";
    public static  String orgNo =ZRBXUtils.zr_code;
   // public static  String shareUrl ="http://yxbwx.ybw100.com";
    //public static  String backUrl ="http://app.ybw100.com/notify/zhongRuiNotify";
    //易小宝私钥
    public static String yxb_prk = "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAJXhkZuoweksWB/B" +
            "melECm/2fWi2oTeNPy7TOMnfvR6hhXZOqjTgW02cLHF7lBWEHAdasiVmlgRK5eru" +
            "b8FW3rMt/vKUGeAm0Ht34QDqpiIKJh1F3b/jijaH7zaT4+xapXFp4dBASS4HdgCv" +
            "PR1we/JHDiite2MV8TEBd8CWtoZJAgMBAAECgYAWfEjD+YVd3fE6kmjsvfBy65/U" +
            "3dFB7EbWV4nlf1N4DPNg1FxkomQQOKXSQJ/uumsaD1k6kzFeY34qyKqrbsTqEe24" +
            "Ja7Hhtcdjt+UrhJfg3dMMiAWvmdcpOdyCiIqW3JSFTYGK/Q1zsLCHCx2qOAGdC+B" +
            "5ky2DjjcqXIP4Kz1oQJBAMWgLe2tJKmvd7FtDTXD164WwNZ/2PexJu0TagtoqpSf" +
            "KsgBIvsrhnyFMh/1oqKV+tiXe3nz6/cOq1QnX5SqjMMCQQDCJxkhjsICDzHcm2+0" +
            "s0cVROcgGflKdxSziixPTEXLHDYwWgb+216Pgi1dHb78zMefDOkKUnGg5FJgEXAk" +
            "f6ADAkBqR9uLX7tA2lHHhHs/N+SNBkWM1dKsWoQxqWg1XIOoS/Uo/JuAcobv/n3X" +
            "fDWLtJbj1oucKVb0VdpD9qzLefEbAkBhZdXQpNokyFSeNAfU4b7+J4O+8ejCd3yW" +
            "GPHjkgLNQsjYdsFdptUILyjstphyH5Tg8EwUFonUSdYdRYI5fSDbAj8i/Z8t1WSn" +
            "4eaTCoPG6ypPQhbKSgXsnqopEiRKdNfpc8KlJxbDr1ckgVj2ashftZANabEg8MSN" +
            "uYB+I1y6mvI=";
    static {

    }

    public static String  getBackUrl(){
        if(YxbConfig.active.equals("prod")){
            return YxbConfig.zr_backUrl;
        }else{
            return YxbConfig.zr_backUrl_dev;
        }
    }

    public static String  getShareUrl(){
        if(YxbConfig.active.equals("prod")){
            return YxbConfig.zr_shareUrl;
        }else{
            return YxbConfig.zr_shareUrl_dev;
        }
    }

    /**
     * 获取中瑞跳转地址
     * @param productCode
     * @param extendBody
     * @return
     * @throws Exception
     */
     public  static String  getProductRedirectUrl(String productCode,String  extendBody) throws Exception {
         JSONObject json = new JSONObject();
         json.put("orderNo",orgNo+"OPRO"+StringUtil.getCurrentDateStr());
         json.put("orgNo",orgNo);
         json.put("backUrl",getBackUrl());
         json.put("productcode",productCode);
         json.put("shareurl",getShareUrl());
         json.put("extendBody",extendBody);
         logger.info("回调地址信息"+getShareUrl());
         //String encrypt = RSAUtils.encrypt(public_key, json.toJSONString());
         String content = DESPlusTest.enUnicode(json.toString());
         //String encrypt = RSAEncryptTest.rsaEncrypt(json.toJSONString(), com.yxbkj.yxb.util.zhongan.RSAUtils.publicKey, "UTF-8");
         String encrypt =  RSAEncryptTest.rsaEncrypt(content, public_key,"UTF-8");
         //String encrypt = RSAEncryptTest.rsaEncrypt(json.toJSONString(), com.yxbkj.yxb.util.zhongan.RSAUtils.publicKey,"UTF-8");
         return baseUrl+"?orderinfo="+encrypt;
     }

    /**
     * 解密中瑞密文
     * @param content
     * @return
     * @throws Exception
     */
    public static String des_zhongrui(String content) throws Exception {
       String des = RSAEncryptTest.rsaDecrypt(content, yxb_prk, "UTF-8");
      return  DESPlusTest.deUnicode(des);
    }


    public static void main(String[] args) throws Exception {


    }

}
