package com.yxbkj.yxb.util.yeepay;

import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.config.AppSdkConfig;
import com.yeepay.g3.sdk.yop.encrypt.DigitalEnvelopeDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;
import com.yxbkj.yxb.util.YxbConfig;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class YeePayUtils {

    public  static String isv_private_key  =  "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAnKoWdJ+faGbZ6yxpVjGfQKRChoJLe8TY1VUHbeYa3TfEEEgM50EKAp6vb3Q+vhoAvPEFv6lMmSOpi5aVYEsQ5Rf+mB3gY5+crJ4cjfsiP7tleMJF41eo0bza/lh+GVv7zRaHNqdvtUT46IF16qA3hDEGlAWA9VtrzhKcLe8EdsmB5sCefurqOPsXUNc8APb03y/Ml3LBy4q2ClmXQr6an18MNeBAw0vVccu5tgWw6JIC9xj4mtncwvo4NZ22lBY2tR9ewYSjOS4myajVycBWa9FT7wX4rKvVlneOTBVlzIth7vj+gfx009Wt+SARy5AeCbt+lQaNnMoCIvkBK5nXAgMBAAECggEAS3SezcHqjKnB/gRd8Acj4d0ZAfbMpuCcouK2mj8+C3DScPNkcj2TCP50f1USjGUX+GrhtY+3EjuekrBUzprKL8VZCIPSs31/fXpA4VvjhTqDBYuN31b8bQ1wkcM7w++46chveTD1lzJwnuA6ZCYdjbS+1q8tc3WAB4XFoV/JpYH1410aWouFHXu/1VdJ4hUt1SdDL5ZbZXELYx1RrBMtLS/Z+uhjvr9TvitO+Zy9B4MX8O2UhOyJE8Iy9nOIav5yW/Lu1sizjh5X3xUV1Xw65u0HYTgyQzzu5Fcn7AvsSc7xjW5vSo6dBA4I2MXqFA/Ycgco9LoBm4+8Q+s7udRU0QKBgQDyR8G3jOKfgCK7mbQ/Gocb7P+mJg3VzJUEnNOKm6IuD5n/XYf7ej2j1p9o4V16+SMz/+jh/EfG5nSdsoHDoMdS3zdL+muFRmLFMlkpOlS+zeXKiGT23B9GR2xhw2Lz/y1gRQYTxXh/VFaWt/TiUcfgmng834GFtZPL/pqhbsipBQKBgQCH5RolEx23CnjpJ+Ogf1VX/F/Li7V4LjBu0jH4Npkc1oQN6sWa53IGcvhh4VYIwIg15ATls5J8Hdiab/RIPrABO1wJruHF87Hzaxrv5bF8Epxhzuk7XUX+1zBhwJro+5fIRAuonXeI9duVcWkruGlWJrEgemJbv5dhnqdpIOg+KwKBgBv4aMbV/vvHATuDPSnj2CFDNShrUEGUgseri0h/OzPyHSc8FwzTSfZ2OqUTD4W46XWBX9NnGqo9xMzH5O8+yoQovOuKl4slWxXFAEIgN9RnVNYS9QUC2KF2cW6/7daZaRWqIk96BrU8eVqhFiZipp6pdqQgcYWMNungFN36OjQ5AoGAU8v2Hz52aFRQ0pUCTAm4upSpDX6M3Bq/StbacVBZgerVay+FwktNiKSEfPbScnsmgvSkAHCGt3pAApteat4c8eWEj4QDPGlVJeC5r4xXsUZYoAdS0oRlkQtojh6SZ3Ac3CT5e3P+LbpNOQ3B6G/CpJkfSAOvexvdYRjP6oecLhkCgYEA1An2b6Jii1h55F4vuKqaDNBsOxeGQS0w9sahf/wkh4QNAvuBTrSlpx4KkIWqwBDrM8rglnvrzADx0V6hNverRc1ArYLOHeL3LDxJdxhtDxhFugTZpnM575FSrzzZLJExM0ZrR6bVMpGSWFifMFCPOn/1kGRFbVh34dM4XEmR+Dw=";
    //public  static String isv_public_key  =  "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKLgI+64mmJdNg1TwlCPBnNH3b3qfw2TdHVc2uDd4LTyQI8nRr0heFhhdj0OZi6agqekIyzAH/XmO9PdLrTi4YXJXOfiO/dYwKA6gSktRe6FKY4C2WzX1yA4fGfqJMV7RYVoL6In50Hur6rGnavNSQZqbiDJOgy5yokJ14Mey1iMqqqWvADtKN9SqxtbyIxYD/jj/6qLWwmu88wSwSaGdO3wNFgzajsHgRJe9G9IhD0zr5d72HvJGoedq7VaPn3jhIszcPQE6oqbXAddZRGKBehA4WSCjLEl87XH33zZPrxrQlBTHVVGzfxjbB4QvYz0hlEoWh1ntxeDHTfgyhdPQpAgMBAAECggEATmxMSLW6Xe08McpkmwT9ozq0Oy4BvKW1EIGS15nfcEmRc7sAN7Z1k0BxIDGuu91gcqGbvfJuL+0gCQ7LGqTnsmFvZnp9SU3CNTw33ISBxhKdv1jtthodN7Vw3CjQsYYvmThtc7Mfk9FOWk+4e7VVSnHW98XjGbMBIE2AF1heNgeZ40ubdgzuz9+4g4pphjWncPpwcaMfsDZm3JtFyvUp0+LME0CmUqrxvONZAkpFR/PyejGHnIh3ptHzhe/VjNcuIC4PphkCNBakCBCrtohTy0YeeWfDAUTAO4tPXF/JUhlxjPuqR6rpQY/0uQdMAtTpiWHVJar7eGdK81QnuuOFRQKBgQDrklUPM0pkvGG/wREa0bgUI+ki+1/wv7O8X94/8onomJqPpkD8z4hv/Lev/wD5gDcgmgLC36u/XDuhFfVNOmw4eUWenU6pzonroEjhi91AKcRRfzDfOfWg3wPm1J9WQOn5A033tNRydCpVcX/Ot4qDbKcAwLiPNPXXMTn4LUQE/wKBgQDbtmE0KS/kSfjscWJOqwv1XbxckipkxncqIbdiSdU+DzaLd+Vuaco7TLQJRFp7S7WJW4Tz6KBX2UiA7O7ezXY9PwlgXxXiZDDtneXNAqk7DNxmTTZHrF2C7qdU98klppCFiFx9bysGY6lFWofWmg3Pu5IiPqO3iLRPTvZgQOE+1wKBgQC9SCgmfYzyIlfcjtIinY5uSGiEnjz5od9WpiVbdpOPHEdc0zZ2rH6xlPs3ZAuxbm9dN8KuOLC0ovSau50Nv7rDKdZh234gfP9fH7xP1mUhsC25Why30MdnyqpE6GVbFe+qERitx1PI30RAwWDzhZC7hystNK1XDDPZBAnTOvPjmwKBgDFuujX7IkxRnFDOPdkHQNyGp2+Ib0NXJ85x4YmapQCeeZ4tbpBF+vsWidcf6t+crA5oaeRarWC2gUqIhEHapkSnXxuwqQLTmfKMOPzEIYEoppnZu2Gq1Ss1OK60RSxUamWwxWZvUZXRbG8vLCrLZFodkIZl433SowbI9EO5tTPnAoGAJRsy1z95Q1GPkKrFtKivkxZy1k7zJXjM0VWDc7lT9fBnoeGUyt+vuq+lC5i2aiWKJK7pe8MM9QFDGlWPnly+J8jbyMfm99k5oJtCWDfF0or1pAQ4mw0kjL9TvDVXdojgYA+rxSMQ09hwsYukQ4bblrwfBUmRjLN5WibcRzIW5ZA=";
    public  static String isv_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6p0XWjscY+gsyqKRhw9MeLsEmhFdBRhT2emOck/F1Omw38ZWhJxh9kDfs5HzFJMrVozgU+SJFDONxs8UB0wMILKRmqfLcfClG9MyCNuJkkfm0HFQv1hRGdOvZPXj3Bckuwa7FrEXBRYUhK7vJ40afumspthmse6bs6mZxNn/mALZ2X07uznOrrc2rk41Y2HftduxZw6T4EmtWuN2x4CZ8gwSyPAW5ZzZJLQ6tZDojBK4GZTAGhnn3bg5bBsBlw2+FLkCQBuDsJVsFPiGh/b6K/+zGTvWyUcu+LUj2MejYQELDO3i2vQXVDk7lVi2/TcUYefvIcssnzsfCfjaorxsuwIDAQAB";
    public  static String appKey = "OPR:10025176806";
    public  static String url = "https://open.yeepay.com/yop-center";

    public static void main(String[] args) throws Exception {
        String s = "customerIdentification=OPR%3A10025176806&response=dvGBwJ-DeWgquYWbkldyHuIJ3MflwcgqKL7tj0-qquHTc9Ls_1UPbnlj7MN2Tx76zKDtvIwkDglGUPwJ8jLJrq3s6-KBuM-2k3gP41Gd1Wi8cuqgMAEDHS3TXcqanNiRP4pN6PQ3JnIVTGejfetCeQnDKvWRRMw2y16kJVW2A_bnIetAqjqw1wUx9rRO_d1hNcLjfUboQgRgBz1LmL_zz8lcyJ81x1J_-plKt34A58J-PT5fm7m1dmXmSLU19hrn34fo5uO5Q9QqdPTF6YS1SlYoei6PjMcMmm009VaT-ZN9xANvvakg0f9lok5ltqN1CaB7_v7O7ke-T70w41fG8w%24kCj8TfjQkj_Wx_dJVEroRbVnX5LyDFVrtszuiW4uyEcAJ4wocMzmQGk1TVIbU49MAAyrk3fsJ5WMV-0iW-MbBM1i3CrSo7vhr1e1urRcFvN8-PZfWZkj2zJrK8Dj0aLI-SxvfexZrdVYNRMOdjdsOl5AKeNPpHVW0F3-nUqUcIOImJLFdZ4huOStduGXAyn6ufaIVnZ6hNr3Td9s2O1DeY1-0tpwOaq-myo09zeURlNQB3Nn1BOLBf9QW5VH9xDtOvyutylZWX_NuJbql_XpMJjv7Hc1AB8Nv9_1CjcjSx8BBWlmowGFzQmyY3YQGmBLbFBUl9Ca3ZfdkWDfcOwx61z3u3uF8XTv0iUOuVtxpORqcuEKBkg4y3DbkVziBnq6zucFFfOdwz9WfWbHhSZ5ulXLkh2v0xCap7sPufFBOHrFZ5M5NlLrP_K-YMFuY93a92jxnPlUEiwjIShuaGKF1PwtZvfG26OuTuH6yXuDz0CWR03Dlh3wmMfTV-ipv6OrCdvdfu03sDIEJDvhjFoll8Apmk5xLY7BmZSy4Vaqr5nD0MVRF6fVvCBhSlbUsdNxoVDn1tU2Unve68cva6-B54Qy9AGI7Po0Cwa06AjIUotzy4mDoeuR4ZD-qruMYDFzCu4blVINsm7uKtZG4ewoUsmtwLEHxOki5FLNn2Hf_MZATmY__XPRNQ-tIQ9DjRanUdL7NXk2qayvrowk6s2QZMeiHgsiTPsepzFXzUgwivUyvTaFO-sQ8JiFL6l2HpuAEL2zOctCByEHZHcYUYbbuFAWRluZjXlkdVvvsmHbdKrCvkX8PBfYlRw_ZC-LYoPl9rLsI4GTWAtD4SvgJZANYKD93AJ39fExDygroReNwbryhRR4t17god65cj1LKGVT2e0ue6eiyLNUbx_PLhPa8FF1Lzrw6AjbgIylYygsU6cofCzLFCDWGUgTx2f36aW09ibB-iwKjwjWevwP69QOQgVkehORjTi2Ro4POiQx-jfbLoZNLsiWxX-s1mjTLrk7L7PEvu5LwYilehduIsV_U3ffRekr4DWpdRLpd9tGbWHi5IkyCthtcl0xrG3MpP_gx0qUuVpg6tzoFkT_8l4dObO_tBUTm_fzV0WK4YdyqgS8SP-WOCQBbdEwpkwVfgVjY-idAOz6YgRjuIaAOJ9Alw%24AES%24SHA256";
        String decode = URLDecoder.decode(s, "UTF-8");

        String[] split = decode.split("&");
        for(String str : split){
            String[] split1 = str.split("=");
           // System.out.println(split1[1]);
        }

        String ss = "dvGBwJ-DeWgquYWbkldyHuIJ3MflwcgqKL7tj0-qquHTc9Ls_1UPbnlj7MN2Tx76zKDtvIwkDglGUPwJ8jLJrq3s6-KBuM-2k3gP41Gd1Wi8cuqgMAEDHS3TXcqanNiRP4pN6PQ3JnIVTGejfetCeQnDKvWRRMw2y16kJVW2A_bnIetAqjqw1wUx9rRO_d1hNcLjfUboQgRgBz1LmL_zz8lcyJ81x1J_-plKt34A58J-PT5fm7m1dmXmSLU19hrn34fo5uO5Q9QqdPTF6YS1SlYoei6PjMcMmm009VaT-ZN9xANvvakg0f9lok5ltqN1CaB7_v7O7ke-T70w41fG8w$kCj8TfjQkj_Wx_dJVEroRbVnX5LyDFVrtszuiW4uyEcAJ4wocMzmQGk1TVIbU49MAAyrk3fsJ5WMV-0iW-MbBM1i3CrSo7vhr1e1urRcFvN8-PZfWZkj2zJrK8Dj0aLI-SxvfexZrdVYNRMOdjdsOl5AKeNPpHVW0F3-nUqUcIOImJLFdZ4huOStduGXAyn6ufaIVnZ6hNr3Td9s2O1DeY1-0tpwOaq-myo09zeURlNQB3Nn1BOLBf9QW5VH9xDtOvyutylZWX_NuJbql_XpMJjv7Hc1AB8Nv9_1CjcjSx8BBWlmowGFzQmyY3YQGmBLbFBUl9Ca3ZfdkWDfcOwx61z3u3uF8XTv0iUOuVtxpORqcuEKBkg4y3DbkVziBnq6zucFFfOdwz9WfWbHhSZ5ulXLkh2v0xCap7sPufFBOHrFZ5M5NlLrP_K-YMFuY93a92jxnPlUEiwjIShuaGKF1PwtZvfG26OuTuH6yXuDz0CWR03Dlh3wmMfTV-ipv6OrCdvdfu03sDIEJDvhjFoll8Apmk5xLY7BmZSy4Vaqr5nD0MVRF6fVvCBhSlbUsdNxoVDn1tU2Unve68cva6-B54Qy9AGI7Po0Cwa06AjIUotzy4mDoeuR4ZD-qruMYDFzCu4blVINsm7uKtZG4ewoUsmtwLEHxOki5FLNn2Hf_MZATmY__XPRNQ-tIQ9DjRanUdL7NXk2qayvrowk6s2QZMeiHgsiTPsepzFXzUgwivUyvTaFO-sQ8JiFL6l2HpuAEL2zOctCByEHZHcYUYbbuFAWRluZjXlkdVvvsmHbdKrCvkX8PBfYlRw_ZC-LYoPl9rLsI4GTWAtD4SvgJZANYKD93AJ39fExDygroReNwbryhRR4t17god65cj1LKGVT2e0ue6eiyLNUbx_PLhPa8FF1Lzrw6AjbgIylYygsU6cofCzLFCDWGUgTx2f36aW09ibB-iwKjwjWevwP69QOQgVkehORjTi2Ro4POiQx-jfbLoZNLsiWxX-s1mjTLrk7L7PEvu5LwYilehduIsV_U3ffRekr4DWpdRLpd9tGbWHi5IkyCthtcl0xrG3MpP_gx0qUuVpg6tzoFkT_8l4dObO_tBUTm_fzV0WK4YdyqgS8SP-WOCQBbdEwpkwVfgVjY-idAOz6YgRjuIaAOJ9Alw$AES$SHA256";
        String s1 = parseYiBaoResponse(ss);
        System.out.println(s1);
    }



    /**
     * 作者: 李明
     * 描述: 获取易宝支付链接
     * 备注:
     * @return
     */
    public  static String getPayUrl(
            String orderId
            ,String orderAmount
            ,String goodsName
            ,String goodsDesc
            ,String source
        ){
        //String timeoutExpress = "1";//单位：分钟，默认 24 小时，最小 1分钟，1 最大 180 天23
        // String requestDate = "2019-12-12 13:23:45";// 请求时间，用于计算订单有效期，格式 yyyy-MM-dd HH:mm:ss，不传默认为易宝接收到请求的时间
        try{
            String goodsParamExt = "{\"goodsName\":\""+goodsName+"\",\"goodsDesc\":\""+goodsDesc+"\"}";
            Map<String, String> params = new HashMap<>();
            params.put("orderId", orderId);
            params.put("orderAmount", orderAmount);
            //params.put("timeoutExpress", timeoutExpress);
            //params.put("requestDate", requestDate);
            String param_str = "";
            if("10000014".equals(source) || "10000013".equals(source)){
                 if("10000014".equals(source)){
                       param_str = "?top=1";//安卓
                 }else{
                     param_str = "?top=2";//IOS
                 }
            }
            if("JIAYOU".equals(goodsName)) {
                params.put("redirectUrl", YxbConfig.getYeepayRechargeRedirect()+param_str);
                params.put("notifyUrl", YxbConfig.getYeepayRechargeNotify());
            } else {
                params.put("redirectUrl", YxbConfig.getYeepayBuyMemberRedirect()+param_str);
                params.put("notifyUrl", YxbConfig.getYeepayBuyMemberNotify());
            }
            params.put("goodsParamExt", goodsParamExt);
            String uri = YeepayService.getUrl(YeepayService.TRADEORDER_URL);
            YopRequest yop  = new YopRequest(appKey,isv_private_key,url);
            Map<String, String> result = YeepayService.requestYOP(yop,params, uri, YeepayService.TRADEORDER);
            System.out.println("token   "+result.get("token"));
            String parentMerchantNo = YeepayService.getParentMerchantNo();
            String merchantNo = YeepayService.getMerchantNo();
            String token = result.get("token");
            String timestamp = ""+System.currentTimeMillis();//request.getParameter("timestamp");
            /*
            设置该参数后，直接调用支 付工具，不显示易宝移动收 银台页面。 枚举值： WECHAT： WX 支付 ALIPAY： ZFB 支付 YJZF： 易宝一键支付 CFL: 分期支付 DBFQ: 担保分期
             */
            String directPayType = "YJZF";//request.getParameter("directPayType");
            String cardType = "DEBIT";//request.getParameter("cardType");
            String userNo = ""+System.currentTimeMillis();//request.getParameter("userNo");
            String userType = "IMEI";//request.getParameter("userType");
            //String ext = "{'appId':"+Configuration.appId+"\",\"openId\":\""+Configuration.openId+"\",\"clientId\":\""+Configuration.clientId+"\"}";
            String ext = "{'appId':'"+Configuration.appId+"','openId':'"+Configuration.openId+"','clientId':'"+Configuration.clientId+"'}";
            params = new HashMap<String,String>();
            params.put("parentMerchantNo", parentMerchantNo);
            params.put("merchantNo", merchantNo);
            params.put("token", token);
            params.put("timestamp", timestamp);
            params.put("directPayType", directPayType);
            params.put("cardType", cardType);
            params.put("userNo", userNo);
            params.put("userType", userType);
            params.put("ext", ext);
            String url = YeepayService.getUrl(params);
            System.out.println("----"+url);
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    };

    /**
     * 作者: 李明
     * 描述: 解析易宝密文
     * 备注:
     * @param response
     * @return  返回json串
     * @throws Exception
     */
    public static String parseYiBaoResponse(String response) throws Exception {
        //String response="vR-YgAN_RTYTsnYx5B17169sU8bELjX5J8fSL6GhyUARUaG9pS4kky2YuBxsqFMlCPd0V9iNAcLyS1By8YN7N8lIz8ta3qeZBc0vLq_PbiL8NFjred0074tsa3TDj1LATfnwOWafGQm217YG8Ur11RecE4bfgWRkCU5cPitGnZdLYo_up7QfS0Oo7TrS2BRVLFAYO1H8hVGFEXRFTUSujZ91-cV46L5Dlc5_oeJUBgRrUisBCcfSwVfDGJgT4vs4T0s_IX2ti8ljg-oHX5C-ej4jpXNhhxUjoWSk4vGCrj9qaHrta2Ckygl8-egrvfpQKA1ONDwlIy2N9lQsbIeDiw$yVKTnpEfFPoneHc_MNlnQyGs634ebx1sfiQNQKoWcEHc-p_Ou1z2HpokXjNurPbO9727FNyLrrLmJyRkFXYuqSQ1UwlH_jxsRNkB8rCBmgEa29FEbKvVBi2bEMVmwM-glXDm2VPC4xixbvq_VqMHj__D1INPKQuCA_TSLc1zUBxfSgmNDaeC0KYgP0NErgOzyI9dkfvoSII7ip47RZ_dsjj7Lflxu9zFEqu-3Y2-LsFr84yr-IYopNIWJRdF5q7O6jlctlDjQr5ugVOP9kxEVQcyP87B2-xog00RzHeEBkak68bmaMGkDcJnGOpuNr5Yw5nUDjxp26JsNhKJA2XMLj2IsIwhzeqssMtx9qo52BqVZwfTdeqejlucoK5wdOr3193YsJiwum0tL4qR2oGhyaJhiYMXcOJv7JSt8jYZ1XMgqrcVwaHt3_iWdYdRPzhz4esQ-4_iyCZP33xc0zyQqnajiBSXIYq9yBNXTCpYkahpnPqrmBlKubXsus41fk-lTeehvqaZbvocgiReTnu-fSJih7F_coXNX3Q4p4-rSZRaGSyF2X0OoiHqTokB9_bGnaQ6ReLlSs4cdee68oCW7z13xetHtXrHAQ78D1LdMUSLBDTsO8o08gOXwdtCm8AWKfbrcXSpXuijhRB_aMpShD8OdPwjnmhpciI3YEwY-BR8WAJgBlAZO7v4MiRGkYoS30sEkzpb0Yt3uxZWQZJCz5sb7BXI8P152m6rOZWTgJ7H-QQRnHkDlrPOTvtXbtGhf_6pWYEJGXhO6WCN9rPE-qUrao-71_sARcX0eMgympvO6gQQjW4-YzV4rq4_d9wNUvRTfrGWd3HA8iayEJ4NeNkckTjIUkRZo8VpmcAJTw4olby3mltsF9LpZfsEZhK02CQu5ljjme4iZaOf0l7n6_h4QYYTMu295xky_NmSyqJQYgldaR3BBlihZx-lEzh7xLLX3LZNw0L-b_Vt1Q5Fls2cO8ZoA1DeMVemc02za2ySxdC_bjeJHfoA2xBPsqaIof3I0p4BExKX5QEyC5TgLgbkXQ5EVLOtIiFbF74L92BdITnzBSVIxGjw2SI9w4bAlek9IEtVxtQE2hJJrTmCuPpTBsldecTUlPINGSq_lw695PC2vsMBhL9VQvje78T5JPQLaBl_xfgAPby0XV57Hw$AES$SHA256";
        //{"unionID":null,"preAuthAccountTime":"","bankTrxId":"18091119565539814","orderId":"1536663781382","openID":null,"paySuccessDate":"2018-09-11 19:04:16","cardType":"DEBIT","platformType":"NCPAY","bankPaySuccessDate":"2018-09-11 19:04:16","customerFee":"0.00","uniqueOrderNo":"1001201809110000000173015279","bankId":"CCB","fundProcessType":"REAL_TIME","payAmount":"0.01","orderAmount":"0.01","requestDate":"2018-09-11 19:03:01","parentMerchantNo":"10000466938","paymentProduct":"NCPAY","status":"SUCCESS","merchantNo":"10000466938"}
        //开始解密
        response = URLDecoder.decode(response,"UTF-8");
        Map<String,String> jsonMap  = new HashMap<>();
        DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
        dto.setCipherText(response);
        //InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
        PrivateKey privateKey = com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils.getPrivateKey(YeePayUtils.isv_private_key);
        System.out.println("privateKey: "+privateKey);
        PublicKey publicKey = com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils.getPublicKey(YeePayUtils.isv_public_key);
        dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
        // System.out.println("解密结果:"+dto.getPlainText());
        //jsonMap = YeepayService.parseResponse(dto.getPlainText());
        //System.out.println(jsonMap);
        return  dto.getPlainText();
    }
}
