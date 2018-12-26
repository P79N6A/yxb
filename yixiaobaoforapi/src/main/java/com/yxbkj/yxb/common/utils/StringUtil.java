package com.yxbkj.yxb.common.utils;

import sun.security.provider.MD5;

import java.util.UUID;

public class StringUtil {


    /**
     * 产生UUID
     * @return
     */
    public  static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 产生TOKEN
     * @return
     */
    public  static String getTokenById(String id){
        String token  = id+System.currentTimeMillis();
        return MD5Util.encryption(token);
    }



}
