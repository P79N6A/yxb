package com.yxbkj.yxb.common.utils;


import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;


public class AES {

	public static void main(String[] args) throws Exception {
		System.out.println(aesDecryptCBC("PnwiF+cZFKbpOm8j94sLbQ==", AES_KEY, AES_IV));
	}
	
	private static final String AES_IV="399582023484524";
	
	private static final String AES_KEY="534424ab6klb44pg34";
	
	
	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}
	
	/**
	 * base 64 encode
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes){
		return new BASE64Encoder().encode(bytes);
	}
	
	/**
	 * base 64 decode
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception{
		return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}
	
	// /** 创建密钥 **/  
    private static SecretKeySpec createKey(String key) {  
        byte[] data = null;  
        if (key == null) {  
            key = "";  
        }  
        StringBuffer sb = new StringBuffer(16);  
        sb.append(key);  
        while (sb.length() < 16) {  
            sb.append("0");  
        }  
        if (sb.length() > 16) {  
            sb.setLength(16);  
        }  
  
  
        try {  
            data = sb.toString().getBytes("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return new SecretKeySpec(data, "AES");  
    }  

    private static IvParameterSpec createIV(String pwd) {
        byte[] data = null;  
        if (pwd == null) {
            pwd = "";
        }  
        StringBuffer sb = new StringBuffer(16);  
        sb.append(pwd);
        while (sb.length() < 16) {  
            sb.append("0");  
        }  
        if (sb.length() > 16) {  
            sb.setLength(16);  
        }  
  
        try {  
            data = sb.toString().getBytes("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return new IvParameterSpec(data);  
    }  

    public static byte[] encrypt(byte[] content, String appkey, String secret) {
        String CipherMode = "AES/CBC/PKCS5Padding";  
        try {  
            SecretKeySpec key = createKey(appkey);
            Cipher cipher = Cipher.getInstance(CipherMode);  
            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(secret));
            byte[] result = cipher.doFinal(content);  
            return result;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }

    public static String decrypt(byte[] content, String appkey, String secret) {
        String CipherMode = "AES/CBC/PKCS5Padding";  
        try {  
            SecretKeySpec key = createKey(appkey);
            Cipher cipher = Cipher.getInstance(CipherMode);  
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(secret));
            byte[] result = cipher.doFinal(content);  
            return new String(result,"utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
	/**
	 * AES加密为base 64 code
	 * @param content 待加密的内容(含偏移量)
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncryptByCBC(String content, String appkey, String secret) throws Exception {
		return base64Encode(encrypt(content.getBytes("utf-8"), appkey, secret));
	}
	
	/**
	 * 将base 64 code AES解密
	 * @param encryptStr 待解密的内容(含偏移量)
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecryptCBC(String encryptStr, String appkey, String secret) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : decrypt(base64Decode(encryptStr), appkey, secret);
	}
	
}
