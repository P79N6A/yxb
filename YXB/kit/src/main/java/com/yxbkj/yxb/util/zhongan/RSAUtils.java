package com.yxbkj.yxb.util.zhongan;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    //公钥信息
//    public static final String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWbPLqh06czsemumqzghgDJnvM\n" +
//            "P9yX7VGhG9HyUeBN3Pkn1gpp/CDtYXFxveVSgX1Z7gCrkdVoUOQh8C1flvrVO7nX\n" +
//            "GqXCZQ2VpygjIaxBaeNXS8N5/Fr1pclJA54a9c5L4KbHRIs03c4SKwyU5dquJstE\n" +
//            "vgiPR2HeguPuMCFOfQIDAQAB";//keyMap.get("publicKey");
    public static final String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHumirhcj7FgXWVgNzASI4r9Kqm+KC4i4Jo18SwZx7Jhq11gVWTvn3HDf7x/VsI5x6AeS0OxOorEL2NTHjEck3mfpLaNCVVpe+66UkYT5qWJB0gA/CC3BW2oAFbt5ocvekNvcWaBfttJ/pe/G7TxJaQoJ/3CxpQfcTGHkmZIg0HQIDAQAB";
    //私钥信息
    public static final String  privateKey = "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAJXhkZuoweksWB/B" +
            "melECm/2fWi2oTeNPy7TOMnfvR6hhXZOqjTgW02cLHF7lBWEHAdasiVmlgRK5eru" +
            "b8FW3rMt/vKUGeAm0Ht34QDqpiIKJh1F3b/jijaH7zaT4+xapXFp4dBASS4HdgCv" +
            "PR1we/JHDiite2MV8TEBd8CWtoZJAgMBAAECgYAWfEjD+YVd3fE6kmjsvfBy65/U" +
            "3dFB7EbWV4nlf1N4DPNg1FxkomQQOKXSQJ/uumsaD1k6kzFeY34qyKqrbsTqEe24" +
            "Ja7Hhtcdjt+UrhJfg3dMMiAWvmdcpOdyCiIqW3JSFTYGK/Q1zsLCHCx2qOAGdC+B" +
            "5ky2DjjcqXIP4Kz1oQJBAMWgLe2tJKmvd7FtDTXD164WwNZ/2PexJu0TagtoqpSf" +
            "KsgBIvsrhnyFMh/1oqKV+tiXe3nz6/cOq1QnX5SqjMMCQQDCJxkhjsICDzHcm2+0" +
            "s0cVROcgGflKdxSziixPTEXLHDYwWgb+216Pgi1dHb78zMefDOkKUnGg5FJgEXAk" +
            "f6ADAkBqR9uLX7tA2lHHhHs/N+SNBkWM1dKsWoQxqWg1XIOoS/Uo/JuAcobv/n3X" +
            "fDWLtJbj1oucKVb0VdpD9qzLefEbAkBhZdXQpNokyFSeNAfU4b7+J4O+8ejCd3yW" +
            "GPHjkgLNQsjYdsFdptUILyjstphyH5Tg8EwUFonUSdYdRYI5fSDbAj8i/Z8t1WSn" +
            "4eaTCoPG6ypPQhbKSgXsnqopEiRKdNfpc8KlJxbDr1ckgVj2ashftZANabEg8MSN" +
            "uYB+I1y6mvI=";
    //私钥信息
    public static final String PRK = "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAJXhkZuoweksWB/B" +
            "melECm/2fWi2oTeNPy7TOMnfvR6hhXZOqjTgW02cLHF7lBWEHAdasiVmlgRK5eru" +
            "b8FW3rMt/vKUGeAm0Ht34QDqpiIKJh1F3b/jijaH7zaT4+xapXFp4dBASS4HdgCv" +
            "PR1we/JHDiite2MV8TEBd8CWtoZJAgMBAAECgYAWfEjD+YVd3fE6kmjsvfBy65/U" +
            "3dFB7EbWV4nlf1N4DPNg1FxkomQQOKXSQJ/uumsaD1k6kzFeY34qyKqrbsTqEe24" +
            "Ja7Hhtcdjt+UrhJfg3dMMiAWvmdcpOdyCiIqW3JSFTYGK/Q1zsLCHCx2qOAGdC+B" +
            "5ky2DjjcqXIP4Kz1oQJBAMWgLe2tJKmvd7FtDTXD164WwNZ/2PexJu0TagtoqpSf" +
            "KsgBIvsrhnyFMh/1oqKV+tiXe3nz6/cOq1QnX5SqjMMCQQDCJxkhjsICDzHcm2+0" +
            "s0cVROcgGflKdxSziixPTEXLHDYwWgb+216Pgi1dHb78zMefDOkKUnGg5FJgEXAk" +
            "f6ADAkBqR9uLX7tA2lHHhHs/N+SNBkWM1dKsWoQxqWg1XIOoS/Uo/JuAcobv/n3X" +
            "fDWLtJbj1oucKVb0VdpD9qzLefEbAkBhZdXQpNokyFSeNAfU4b7+J4O+8ejCd3yW" +
            "GPHjkgLNQsjYdsFdptUILyjstphyH5Tg8EwUFonUSdYdRYI5fSDbAj8i/Z8t1WSn" +
            "4eaTCoPG6ypPQhbKSgXsnqopEiRKdNfpc8KlJxbDr1ckgVj2ashftZANabEg8MSN" +
            "uYB+I1y6mvI=";


    public  static String decode(String data) throws InvalidKeySpecException, NoSuchAlgorithmException {
      return   RSAUtils.privateDecrypt(data, RSAUtils.getPrivateKey(privateKey));
    }

    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

}
