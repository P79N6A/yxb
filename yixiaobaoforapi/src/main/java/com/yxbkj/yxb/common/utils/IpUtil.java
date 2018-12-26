package com.yxbkj.yxb.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @Author Lin_J
 * @ClassName: IpUtil 
 * @Description: (得到用户访问的真实Ip地址)
 * @date 2017年10月17日 上午11:44:45 
 *
 */
public class IpUtil {
	public static String getAddrIP(HttpServletRequest request){
		String ipAddress = request.getHeader("X-Real-IP");
		if (ipAddress != null && ipAddress.length() > 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
			return ipAddress;
		}
		ipAddress=request.getHeader("X-Forwarded-For");
  		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
  			ipAddress = request.getHeader("Proxy-Client-IP"); 
  		}
  		if (ipAddress==null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
  			ipAddress = request.getHeader("WL-Proxy-Client-IP");
  		}
  		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getRemoteAddr();  
            if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){
                // 根据网卡取本机配置的IP
                try {
					ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
            }
        }  
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		// "***.***.***.***".length() = 15
        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){  
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
            }  
        }  
  		return ipAddress;
	}
}
