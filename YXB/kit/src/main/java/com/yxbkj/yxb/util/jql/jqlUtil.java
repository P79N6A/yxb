package com.yxbkj.yxb.util.jql;

import com.yxbkj.yxb.util.MD5Util;

import java.util.Base64;


public class jqlUtil {


    public static String encode_pass(String tex,String key,String type){

        String[] chrArr = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                "0","1","2","3","4","5","6","7","8","9"};

        if(type.equals("decode")){
            if(tex.length()<14){
                return "";
            }
            String verity_str = tex.substring(0,8);
            tex = tex.substring(8);
            if(!verity_str.equals(MD5Util.MD5(tex).toLowerCase().substring(0,8))){
                //完整性验证失败
                return "";
            }
        }
        String key_b = type.equals("decode")?tex.substring(0,6):chrArr[((int)(Math.random()*62))%62]+chrArr[((int)(Math.random()*62))%62]+chrArr[((int)(Math.random()*62))%62]+chrArr[((int)(Math.random()*62))%62]+chrArr[((int)(Math.random()*62))%62]+chrArr[((int)(Math.random()*62))%62];
        String rand_key = key_b+key;
        rand_key = MD5Util.MD5(rand_key).toLowerCase();

        tex = type.equals("decode")?new String(Base64.getDecoder().decode(tex.substring(6))):tex;

        int texlen = tex.length();
        String reslutstr = "";
        for(int i=0; i < texlen; i++){
            reslutstr += (char)(tex.charAt(i)^rand_key.charAt(i%32));
        }
        if(!type.equals("decode")){
            reslutstr = myTrim(key_b+Base64.getEncoder().encodeToString(reslutstr.getBytes()),'=');
            reslutstr = MD5Util.MD5(reslutstr).toLowerCase().substring(0,8)+reslutstr;
        }
        return reslutstr;

    }

    public static String myTrim(String s, char ts){
        int start=0,end=s.length()-1;

        while(start<=end&&s.charAt(start)==ts){
            start++;
        }
        while(start<=end&&s.charAt(end)==ts){
            end--;
        }
        return s.substring(start,end+1);
    }




}
