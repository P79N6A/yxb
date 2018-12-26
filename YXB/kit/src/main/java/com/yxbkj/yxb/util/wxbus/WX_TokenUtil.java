package com.yxbkj.yxb.util.wxbus;

import com.yxbkj.yxb.util.wxshare.TokenJson;
import com.yxbkj.yxb.util.wxshare.WxParams;
import com.yxbkj.yxb.util.wxshare.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WX_TokenUtil {
    private static Logger log = LoggerFactory.getLogger(WX_TokenUtil.class);


   public static String getAccessToken() {
        //处理token失效的问题
        try {
            long tokenTimeLong = Long.parseLong(WxParams.tokenTime);
            long tokenExpiresLong = Long.parseLong(WxParams.tokenExpires);
            //时间差
            long differ = (System.currentTimeMillis() - tokenTimeLong) / 1000;
            if (WxParams.token == null || differ > (tokenExpiresLong - 1800)) {
                System.out.println("token为null，或者超时，重新获取");
                TokenJson tokenJson = WxUtil.getAccess_token();
                if (tokenJson != null) {
                    WxParams.token = tokenJson.getAccess_token();
                    WxParams.tokenTime = System.currentTimeMillis() + "";
                    WxParams.tokenExpires = tokenJson.getExpires_in() + "";
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            TokenJson tokenJson = WxUtil.getAccess_token();
            if (tokenJson != null) {
                WxParams.token = tokenJson.getAccess_token();
                WxParams.tokenTime = System.currentTimeMillis() + "";
                WxParams.tokenExpires = tokenJson.getExpires_in() + "";
            }
        }
        return WxParams.token;
    }


}
