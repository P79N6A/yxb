package com.yxbkj.yxb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebserviceUtils {



    /**
     * 作者: 李明
     * 描述: 发送webservice数据
     * 备注:
     * @return
     */
    public static String sendServiceData(String xml,String remote_url) throws Exception,
            IOException {

        String sendMsg = xml;

        // 开启HTTP连接ַ
        URL url = new URL(remote_url);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        // 设置HTTP请求相关信息
        httpConn.setRequestProperty("Content-Length",
                String.valueOf(sendMsg.getBytes().length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        // 进行HTTP请求
        OutputStream outObject = httpConn.getOutputStream();
        outObject.write(sendMsg.getBytes());

        // 关闭输出流
        outObject.close();

        // 获取HTTP响应数据
        InputStreamReader isr = new InputStreamReader(
                httpConn.getInputStream(), "utf-8");
        BufferedReader inReader = new BufferedReader(isr);
        StringBuffer result = new StringBuffer();
        String inputLine;
        while ((inputLine = inReader.readLine()) != null) {
            result.append(inputLine);
        }

        // 打印HTTP响应数据
       // System.out.println(result);

        // 关闭输入流
        inReader.close();
        isr.close();


        return result.toString();
    }

}
