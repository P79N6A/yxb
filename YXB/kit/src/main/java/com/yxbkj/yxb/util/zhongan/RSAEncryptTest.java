package com.yxbkj.yxb.util.zhongan;

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

import org.apache.commons.codec.binary.Base64;


public class RSAEncryptTest {
	public static void main(String[] args) throws Exception {
    	String publickey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8g6gmBfeLDGsfI3++W9ad45/SPTKiOrIvyxWKBVnCdZyXXXFcO8837ES+pfVEFRFExedfo5XDID0C0sjW2AQwQ3RiDZn26C2nLuA5loZR41y6jcR7SH5ZoMte3sglDm34Ybbq9VcQH+UrFzwEAi8SqXWu98Vq+GpdsbnyDz/x5wIDAQAB";
		String content = "123";
		String charset = "UTF-8";
		String econtent = rsaEncrypt(content,publickey,charset);
		System.out.println("加密："+econtent);
		String dcontent="Cpf9uMotpOi4DEY79FiHKUPv1gJqvkgO+WyAf1s4MBqSnnjKf3+7z2cETLLFKM4TnxUuIi0BumOU5saKhtOemO0sk1YLexhZRYQevjMgusXVNSE2MgF3dSzqeoV3sWYxA7MfzZMDI+IbbsGPpT988LDp+u1gpP2l50TCpAWOsu0=";
		String initprivatekey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALyDqCYF94sMax8j"+
								"f75b1p3jn9I9MqI6si/LFYoFWcJ1nJddcVw7zzfsRL6l9UQVEUTF51+jlcMgPQLS"+
								"yNbYBDBDdGINmfboLacu4DmWhlHjXLqNxHtIflmgy17eyCUObfhhtur1VxAf5SsX"+
								"PAQCLxKpda73xWr4al2xufIPP/HnAgMBAAECgYAw6sEBTN8VV92cMOv/C3m/cseQ"+
								"GI1BB5YhTMOEEytdiINfy8GUu3hj/I1CI3JnfkgC3o/Rg3NjNMRgLS4sgdVj9jk4"+
								"aSpcSmLBUbC9ztyq0La2OcuIeU1N5V8aojnA45wOBBor9HWqRdnBuBe+TY2nRxfW"+
								"r1UAdTEI+7kL6BWgkQJBAPFXnKkOCskFk0qK8GmwRJebr0yrfv58Ecs6UIjW1pNz"+
								"3R8bGkGsoIyNpcgId6N/+weySfKUTnnOthz7u53fg1MCQQDH9q0XMKVjmqtiPWNv"+
								"NwgRDXECJb8we0M/cB5sRrQSMLnr/OjWkMYM2nZwU8HCnBzE2esCTmiaKeCnDDIH"+
								"vPidAkABwa59OIHxlp3M1BgN3N+S7uomt7TT61lBzmnZpR6oshYw7MSTJ8t/WmST"+
								"gSRFx1+vXafWISg1EMEYkuh5rK/LAkEAp5NYF1kTIVfbTKb5j5hk0DWdgRk1EmIa"+
								"Ry2ksUTHX38qMFRwpHALKXZjT1tBw9+kVGvzgKlJC/kQZa3Yt5dsGQJBAOLD4XGP"+
								"oiGOKPNHPKPsoxMxR4dzIBzeXyv9CD4CjbU1FRTyYjWeBF1ev0HEdx4MguGrO/uP"+
								"CVrsiWmS3btugfE=";
		String result = rsaDecrypt(dcontent,initprivatekey,"utf-8");
		System.out.println("解密结果："+ result);
		//System.out.println("最终："+ DESPlusTest.deUnicode(result));
	}
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

