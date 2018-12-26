package com.yxbkj.yxb.common.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebUtils {

    protected static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * 验证接口参数签名
     *
     * @param request
     * @return
     */
    public static boolean valiSignature(HttpServletRequest request) {
        //1、获取请求参数
        Map<String, String> paramMap = RequestParams.getParams(request);
        removeNoSignParam(paramMap);
        String paramsStr = RequestParams.getOrderParamString(paramMap);//排序后的参数串
        String signature = request.getParameter("signature");// 签名信息
        String public_key = "0016f49477a906ba9645b2c46f88015e";// public_key为 项目名 的md5值
        if (StringUtils.isEmpty(signature)) {//签名为空
            return false;
        }
        String timeStamp = request.getParameter("timeStamp");// 签名信息
        if(timeStamp!=null && System.currentTimeMillis()-Long.parseLong(timeStamp)>=60L*1000L){
            //大于60秒 直接返回
            logger.info("超时请求------------接口排序后参数paramsStr：" + paramsStr);
            //return false;
        }
        //2、后端签名
        String msg = public_key + paramsStr;
        String local_signature = MD5Util.MD5(msg);
        System.out.println(local_signature+"**********");
        //3、校验签名是否一致
        boolean success = signature.equalsIgnoreCase(local_signature);
        return success;
    }


    private static void removeNoSignParam(Map<String, String> paramMap) {
        List<String> list = new ArrayList<>();
        for (String key : paramMap.keySet()) {
            if (key.equalsIgnoreCase("signature")) {
                list.add(key);
            }
        }
        for (String key : list) {
            paramMap.remove(key);
        }
    }

    /**
     * 输出响应信息
     *
     * @param response
     * @param result
     */
    public static void outPrint(HttpServletResponse response, String result) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.write(result);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
