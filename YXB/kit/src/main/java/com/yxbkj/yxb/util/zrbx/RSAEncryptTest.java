package com.yxbkj.yxb.util.zrbx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

public class RSAEncryptTest {


    public static String rsaEncrypt(String content, String publicKey, String charset) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] data = content.getBytes(charset);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
        out.close();

        return new String(encryptedData, charset);
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        Reader reader = new InputStreamReader(ins);
        char[] buffer = new char[1024];
        int amount;

        while ((amount = reader.read(buffer)) >= 0) {
            writer.write(buffer, 0, amount);
        }

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String rsaDecrypt(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密  
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            throw new Exception("EncodeContent = " + content + ",charset = " + charset, e);
        }
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        Reader reader = new InputStreamReader(ins, "utf-8");
        StringWriter writer = new StringWriter();
        char[] buffer = new char[1024];
        int amount;
        while ((amount = reader.read(buffer)) >= 0) {
            writer.write(buffer, 0, amount);
        }
        String data = writer.toString();
        byte[] encodedKey = data.getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
}

