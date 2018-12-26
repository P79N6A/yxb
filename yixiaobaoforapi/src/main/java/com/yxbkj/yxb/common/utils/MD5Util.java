package com.yxbkj.yxb.common.utils;


import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author Lin_J
 * @date 2018年1月5日 下午5:03:46 
 * <p>
 * 			为什么要加盐？因为同样的密码一旦泄露加密出来密文也一样，会导致大面积密码泄露，
 * 		也容易被在线加密工具破解;用于Md5加盐加密密码（包含的salt值算法：用户ID(如果是自增，考虑其他组合方式)+注册时间搓的hashcode值）。
 * </p>
 * <p>需要加密的字符串结构：salt+用户密码</p>
 */
public class MD5Util {

    public final static String MD5(String content) {
    	if(null==content)
    		return content;
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',  'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = content.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return content;
        }
    }

    /**
     *	32位MD5加密
     * @param msg 明文
     * @return 32位密文
     */
    public static String encryption(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return null;
        }
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
    
    /**
     * 对用户密码MD5+salt+1024次加密
     * */
    public final static String MD5AndSalt(String content,String salt) {
    	int hashIterations = 1024;
    	Md5Hash mh = new Md5Hash(content, salt, hashIterations);
		return mh.toString();
    }
    
    
    /**
     * JAVA获得0-9的随机数
     * */
    public final static String getSalt(int length) {
    	Random random = new Random();
    	StringBuffer buffer = new StringBuffer();
    	for (int i = 0; i < length; i++) {
    	buffer.append(random.nextInt(10));
    	}
    	return buffer.toString();
    }
    
    
    
}
