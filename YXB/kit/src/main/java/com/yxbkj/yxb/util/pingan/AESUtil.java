package com.yxbkj.yxb.util.pingan;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {

    // mode_key:Cipher
    private static final Map<String, Cipher> CIPHERMAP_MAP = new HashMap<String, Cipher>();

    // constants
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String ALGORITHM_NAME = "AES";
    private static final String ALGORITHM_STR = ALGORITHM_NAME + "/CBC/PKCS5Padding";
    private static final String IV = "1234567890123456";

    /**
     * get cipher
     *
     * @param mode
     * @param key
     * @param charset
     * @return
     * @throws Exception
     */
    private static Cipher getCipher(int mode, String key, String charset) throws Exception {
        Cipher cipher = CIPHERMAP_MAP.get(mode + "_" + key);
        if (cipher != null) {
            return cipher;
        }

        // secretKeySpec
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(charset), ALGORITHM_NAME);
        // ivParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(charset));
        // cipher
        cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(mode, secretKeySpec, ivParameterSpec);
        // return
        CIPHERMAP_MAP.put(mode + "_" + key, cipher);
        return cipher;
    }

    public static String encrypt(String a, String key, String charset) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key, charset);
        byte[] resultByte = cipher.doFinal(a.getBytes(charset));
        return base64BytesToString(resultByte, charset);
    }

    public static String encrypt(String a, String key) throws Exception {
        return encrypt(a, key, DEFAULT_CHARSET);
    }

    public static String decrypt(String a, String key, String charset) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key, charset);
        byte[] inputByte = base64stringToBytes(a, charset);
        byte[] resultByte = cipher.doFinal(inputByte);
        return new String(resultByte, charset);
    }

    public static String decrypt(String a, String key) throws Exception {
        return decrypt(a, key, DEFAULT_CHARSET);
    }

    private static String base64BytesToString(byte[] bytes, String charset) throws Exception {
        return new String(Base64.encodeBase64(bytes), charset);
    }

    private static byte[] base64stringToBytes(String string, String charset) throws Exception {
        return Base64.decodeBase64(string.getBytes(charset));
    }

}
