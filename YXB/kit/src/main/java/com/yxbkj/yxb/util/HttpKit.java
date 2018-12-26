package com.yxbkj.yxb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/12.
 */
public class HttpKit {
    protected static Logger logger = LoggerFactory.getLogger(HttpKit.class);
    private static final Pattern LAN_IP_PATTERN;//局域网

    static {
        String reg = "(127|0)|((10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1}))";//正则表达式=。 =、懒得做文字处理了
        LAN_IP_PATTERN = Pattern.compile(reg);
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取消息头
     *
     * @return
     */
    public static HeaderData getHeaderData() {
        return getHeaderData(getHttpServletRequest());
    }

    /**
     * 获取消息头
     *
     * @param request
     * @return
     */
    public static HeaderData getHeaderData(HttpServletRequest request) {
        HeaderData headerData = new HeaderData();
        headerData.setApp_key(request.getParameter("app_key"));
        headerData.setSource(DispatcherUtils.parseInt(request.getParameter("source")));
        headerData.setChannel(request.getParameter("channel"));
        headerData.setApp_version(request.getParameter("app_version"));
        headerData.setClient_id(request.getParameter("client_id"));
        headerData.setUser_id(DispatcherUtils.parseLong(request.getParameter("user_id")));
        headerData.setEncrypt(request.getParameter("encrypt"));
        headerData.setTimeStamp(DispatcherUtils.parseLong(request.getParameter("timeStamp")));
        headerData.setLat(DispatcherUtils.parseDouble(request.getParameter("lat")));
        headerData.setLng(DispatcherUtils.parseDouble(request.getParameter("lng")));
        headerData.setProvince(request.getParameter("province"));
        headerData.setCity(request.getParameter("city"));
        headerData.setDistrict(request.getParameter("district"));
        return headerData;
    }

    /**
     * 是否为局域网ip
     * @param ip
     * @return true=内网地址
     */
    public static boolean isLanIp(String ip) {
        Matcher matcher = LAN_IP_PATTERN.matcher(ip);
        return matcher.find();
    }


    /**
     * 获取客户端真实ip
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String finalIp = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        if(finalIp!=null && finalIp.length()>=15){
            return finalIp.substring(0,15);
        }else{
            return finalIp==null?"":finalIp.trim();
        }
    }

    public static String getClientIP() {
        return getClientIP(getHttpServletRequest());
    }


    /**
     * 打印请求Request信息
     *
     * @param request
     */
    public static void logRequest(HttpServletRequest request) {
        logger.info("=========== this is debug begin =============");
        logger.info("RequestURI():" + request.getRequestURI());
        logger.info("QueryString():" + request.getQueryString());
        logger.info("------------- header -------------");
        for (java.util.Enumeration<String> ss = request.getHeaderNames(); ss.hasMoreElements(); ) {
            String s = ss.nextElement();
            logger.info(s + ":" + request.getHeader(s));
        }

        logger.info("------------- parameter -------------");
        for (java.util.Enumeration<String> pns = request.getParameterNames(); pns.hasMoreElements(); ) {
            String s = pns.nextElement();
            logger.info(s + ":" + request.getParameter(s));
        }

        logger.info("------------- sessionid:" + request.getSession().getId());
        for (java.util.Enumeration<String> pns = request.getSession().getAttributeNames(); pns.hasMoreElements(); ) {
            String s = pns.nextElement();
            logger.info(s + ":" + request.getSession().getAttribute(s));
        }

        logger.info("=========== this is debug end =============");
    }


    public static Map<String, String> getRequestParameter(HttpServletRequest _request) {
        Map<String, String> map = new java.util.HashMap<String, String>();
        java.util.Enumeration<String> pns = _request.getParameterNames();
        while (pns.hasMoreElements()) {
            String s = pns.nextElement();
            map.put(s, _request.getParameter(s));
        }
        return map;
    }

    public static String mapToQueryString(Map<String, String> map, boolean encode) {
        StringBuilder string = new StringBuilder();
        if (map != null && !map.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    string.append(entry.getKey());
                    string.append("=");
                    string.append(encode ? URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")
                            : String.valueOf(entry.getValue()));
                    string.append("&");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return string.toString();
    }

    /**
     * 发送POST请求
     * @param url
     * @param param
     * @return
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
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
