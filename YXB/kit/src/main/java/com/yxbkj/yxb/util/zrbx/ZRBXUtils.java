package com.yxbkj.yxb.util.zrbx;

import com.yxbkj.yxb.util.MD5Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class ZRBXUtils {

    //public static String  zr_host="http://new.zgzrjt.cc";//测试
    //public static String   zr_sgin_key  ="15334f1dd836b75d2ba63a366674fef5";//测试
    public static String  zr_host="http://openplat.zgzrjt.cc";//线上
    public static String   zr_sgin_key  ="5b38ad9a1ddfa30272e9f5a1ce302d7c";//线上
    public static String  zr_rg_url  ="/v1/customer/regPracticeInfo.json";
    public static String  zr_code="YB001";
    /**
     * 作者: 李明
     * 描述: 中瑞保险认证接口
     * 备注:
     * @param map
     * @return
     */
    public static String zrbxAuthentication(Map<String,Object> map) throws  Exception{
            map.put("channelId",zr_code);
            String url = zr_host+zr_rg_url;
            String paramStr =  getParamStr(map);
            String data = "POST"+"&"+URLEncoder.encode(zr_rg_url,"utf-8")+"&"+URLEncoder.encode(paramStr,"utf-8");
            String sign = MD5Util.getHamcSha1(data, zr_sgin_key+"&").toLowerCase();
            data = data+"&"+sign;
            return sendPost(data, url);
    }

    /**
     * 作者: 李明
     * 描述: 参数排序
     * 备注:
     * @param parms
     * @return
     */
    private static String getParamStr(Map<String, Object> parms){
        StringBuffer sb = new StringBuffer();
        try {
            ArrayList<String> aa = new ArrayList<String>(parms.keySet());
            Collections.sort(aa);
            for (String key : aa) {
                sb.append(key).append("=").append(parms.get(key).toString()).append("&");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length()-1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 发送POST
     * @param account
     * @param url
     * @return
     * @throws Exception
     */
    public static String sendPost(String account,String url) throws Exception {
        String jsonBody =account;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL smsUrl = new URL(url);
            URLConnection conn = smsUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.write(jsonBody);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}
