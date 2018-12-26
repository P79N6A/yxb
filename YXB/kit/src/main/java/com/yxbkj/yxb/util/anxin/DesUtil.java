package com.yxbkj.yxb.util.anxin;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by wangyong
 *
 * @Date: 2018/10/22.
 * @Description:通过指定的key进行加密
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String ENCODE = "utf-8";
    private final static String defaultKey = "ANXIN_CALL_BACK";

    /**
     * 使用 默认key 加密
     *
     * @param data 待加密数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     *
     * @param data 待解密数据
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 根据键值进行解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

        public static void main(String[] args) throws Exception {
            String str = "5/6qqo9j1b2AQdF6wRVHmmFx51/gtHAwWVtP8Vrv6r4QFE15lWGsRdPqyv4Gto5KpwA5J7k6bpNI\n" +
                    "wVEUA0PekfsuNcumwpsiYP2+Fwkl1TUmJLzsjX1TxI035FpI0FJ2/Y0puLp3+7d/CLJ9Pq2b5Gd/\n" +
                    "V/tXbi0nCDu26jHSC/eW7fuRg2c4VJN0op0L/prhXW4s4pNJNeIpoN5Wn7KSWB1Mnd1Hc5ithRAd\n" +
                    "XBx9ImPtZ0xluMi9O/z7DgY3dwE2bJykHsT4sWthqx1tEGk/8Yhee9NCsyVM22CoKQUs0usMDsVH\n" +
                    "O/oEIiuNc68PQ6dp0H9PAJmLErE1z4twPc+iVivWzddyMJsefSMH9Dv7IVL1XZM81zkbOvrytuAF\n" +
                    "DLlwVJPIphYvzCrJOswgV4AcR9dEawXZInIPQFXR/M+cmA2AMJ6FFec863K7tvKnQi/oSRoXQUGo\n" +
                    "2Lcml7gSfEvTygaKDfUjgu9m1DnRCWKyZwLbH/QnzZbwNcZ2We7Ab6tf69gnea/H+JVBmY/1J2C5\n" +
                    "v4Kjgo1NmL67IWtdGL0rAMmgygZxSVNW/7t4SUCMmhSc/SFQ3a5Xy8d4W8QCLwizf2sYzr0Y8Efd\n" +
                    "eTh6JouqL711FcomUkXzwrK9vnCid6YI6LV69QGc5uT1Q3x+y6UKkxZNDz/JSTnFne29bZcfTghv\n" +
                    "IBv+QBONGQFLo+q7MeG/z1TAneTz+F1nVDI0rJhC93D+652LxAj0U2WgsxWMSkN+ri38AekO+3HD\n" +
                    "ZuN6fFbF5K0vwpFhX23UNmkP4m4X0T0dbx1hi6te2q68+pzOr/O7pePdjMy6qEVRS6WryQyx9CYv\n" +
                    "Rp+xKM5MvJBqBVF0OJm2vC0QjuzB7r2tn14lmcu7eElAjJoUnKuuvgZouhamP8eP6ptvcoZi69pU\n" +
                    "e4hewUYx4d0BQt/jcfP13plg3wPCczTzDOxYAHr/wnr+9PkttfCTROL7TjgcMHoU6NEy0iI/Of3J\n" +
                    "3ztOCL4uHdmj26afJ3RhGeWi5CGNpSVpT+g9ARLoLJdLG+uUOMMG6zMLOtSqnQnoGVKAd0KrWV6a\n" +
                    "SlHL4+PNGJxg32D3rihCXqYN9ijk88mfXp+0wii6IydpyprZ9WRZun66CoYKT/TPfpdaz79X3HAp\n" +
                    "g9dC52Krq3zO4kPuYNRc4QPa6eJcg8/mGjXaf3WKF7ne7HkUAkaQYnBhlY3ITpn/6CC0BPSS3iNn\n" +
                    "u3hJQIyaFJxxUyH1u6TkN3W2PGJ/Ko1gpSo4igYSMyuUluA9ebaqUWQs83kIyaqGWPjyji4N9VQS\n" +
                    "HQacrhERZ2oLNgUfEjuecdUGvUBPl2XHQgI/gtuWwyU5Iz9hnSSVpKrj/GNN028ZsSDUK227DHvk\n" +
                    "AVPMz0+Ulmdw+GkBBNdtENaHawjbjX3A11cwc4/YGoK07PS6SJuIy+DrAt9HGl8S48kKOlTiBqrI\n" +
                    "F3KEOJ5XmyBGQc1nWhsUFjXTR2ac3eWxcmJN0/W5BK/ApaHi4rv7OpZM71c+syZy3GtFwjTB54gd\n" +
                    "E1XiaQw+W1s+CGLr1TT2rAfCymEB+THwDRnRD4KJcD1b+4xFa/fQridOLTNFcpLkLDKaW50a8+Ln\n" +
                    "El3TshPelfKQXY4fcOkFzCpata8BG9QBzwTBltHai3ABpLI8Qiz1xMsFTgzLBFx6NDsk9N2ltM42\n" +
                    "7mlUhoaRqX58YOc9XtLbKovjjzpSkiSWV+X6Uoh4qc1TA+3ud6wNrZ4fcYIwcsLijlAPyUPccmtm\n" +
                    "XqmdoGS/qHJVLWh2W/DlXwVJ9KpXbSi7l8CGoT9qRM2BvDRIzdr7PSrvsYsaTv7wpaxaP4ZTvsm5\n" +
                    "gNFMG6miHkWwqQQjfyk2tXHvvb1+DBD1H6/ren+Q+1GCqA7DaIXH72GUJ8L3FrHlglIhukIBWHOB\n" +
                    "4gAiESDKeoNzGPd1Gv3+JEr8QfHlZWW0yaNeMssHRXadaZo3HEDmmuUZ3s/izVl19eKHQPMbQSVD\n" +
                    "Kzc6v6QMQXn8oK7QRX3vsYsaTv7wpQp/YTxReRY4RuW87wSsmkZf+fAcdkh322Dk/DxvHYqQd7lZ\n" +
                    "Wjnt5o/8p1C9ypN/SLQQ7X6retZU6uQLc3mn6jmP4aazqTV4E75s6dtQGU9eFokDJFReVi/XaJrk\n" +
                    "SNR6moHiACIRIMp6g3MY93Ua/f4kSvxB8eVlZQLqkC5zmuRbq5fBnOuGm/mQBpt82JozaoBql+1L\n" +
                    "OJ11G0JwLBwCJu7q2xiPQiCbviVYmWyHrncDsNlLWYTnUPwmwZchfbii2u4vrs+i7QxJLqGp3u9D\n" +
                    "43fe9TYrEPky29q7rSzdu2xtSG4qZy5Mjl2xONTrZDHb7RR12TAKMA313raGEmEqIXTQ8yZJP6ot\n" +
                    "Be4SH9w6jDaIMnTqWF0a2+eOjmoJ+aKRePHCPK/DDTcPuumO50ekvIt3uVlaOe3mj2L8UfXLbCmH\n" +
                    "bcJVAzNSTXvnYsm6qgDILaIGYS7o5rv4DA7FRzv6BCKW0GyTEPtdFBPDYz1roz1TMx2cTOjgXyDy\n" +
                    "3E10v62coXy3z2nQhjdtCE5Y44AihyV+snj0T8DcIdny7v+zS0ip7CN8yMXu2UlFbV9Q+/3mgGwF\n" +
                    "T2r0E6N4KtRGfML6yfQsptSOi6zjDlvwP5QwuaN/29pRrHn/qeTv0COfolUV3QRd54aWhg5/gHAC\n" +
                    "Xy/hlh5zIr33pnX/2AjHLinlFsgVmFrQXdL+QSW67nc0e714VIvq64ApQHuS4tX2eyTw0gkO1dI4\n" +
                    "it7nsZzknvVOLA7uWHoLD217/dAyOgdJJ7LKH7xvZS0HHyDyF1GoY2rspTJefdxdTxRQrK+/wdla\n" +
                    "TKReW+NGPSnMy6U=";

            String decrypt = DesUtil.decrypt(str);
            System.out.println("订单解密"+decrypt);
            str = "5/6qqo9j1b2AQdF6wRVHmmFx51/gtHAwWVtP8Vrv6r4QFE15lWGsRdPqyv4Gto5KpwA5J7k6bpMu\n" +
                    "fkdQzs8WvpftQ8sAnPQovXwWsAq7do+MvLxm/+Hx9hlBbLUHWobG7ZUps+QAzYyVgpC+wrQwDiSo\n" +
                    "ZVJDNlAkTHYmHe7NYqArmFmCxUOMIsSdaUaEJLPB13Oy40G5dfE6t5lOyYm1yq4e5jocJOLu1qMt\n" +
                    "R+4om8we84hh37Q9zMZWcKU2672+GiGb081FnDgWPqsz5/aZED5xoT9BTgeuq1ZTBdGT5Axb8D+U\n" +
                    "MLmjf9fYsuFJ8QI9v0Ugin8AIXuYTHFr0uHPnv5xw5xnS3/sZx0eFH22+dOAcAJfL+GWHnMivfem\n" +
                    "df/Y2Ie92v+V0cIQPTzpAirl4IRhaQT+jGrMYzcr8RqOciFPxmj2I+UlaroTFoeTp6LnBhbt7/K/\n" +
                    "tV/0QuSHQmMwxI/pw2+TJ2pDKJQvFeNMH9kdXjO2OwI7ptCFVeei+D6hjAyAz7JC3U012n91ihe5\n" +
                    "3ux5FAJGkGJwYZWNyE6Z/+ggtAT0kt4jZ7t4SUCMmhSccVMh9buk5Dd1tjxifyqNYKUqOIoGEjMr\n" +
                    "p8kpG9vza4QQIvRKQaEMkyZ8R0iDbsRY136I38CL9zFoG4xEneFIO0SfrJY7OpxVoRSUloh4S23q\n" +
                    "KdBodVI99v/g8h1z3znmd/XWIzTi8KeK4e++RJO07QBD63icrPH9ZFqxCl3DAX1pwS14Vn7RYUOA\n" +
                    "g7H2LxWi3oeHyT0UCoU9wmIIMDVvDUhfDpNfz/HjDvWRtdR74VNfSJYzsCyecKXTMDd5FV4KE4lm\n" +
                    "WVkzZzT7oBduZ0mXCkP5NI/dvZ6/c4I3aQngYHm7eElAjJoUnIngVWYxHPfzvidXbZprqJ4zQZX5\n" +
                    "Rs1r/lkBnGlZ4l61fxThAO3BPX/ClffJOZuWqKJ9boLvonrg9UN8fsulCpMWTQ8/yUk5xZ3tvW2X\n" +
                    "H04IbyAb/kATjRkBS6PquzHhvx+xMOjxXzz1sNRv5O8rbqFQcf+AnLF+x9qZYWGn4LzE/m5HGuHR\n" +
                    "ePNyRdcxFUb7HUPZbbjyCxp80NRoDsyuptNlHPbfNx77pvNkt6d7kw+E8X83dnNqkYv/79fWAaZ5\n" +
                    "2PJpjWqxzdWR/CpR2UC8jD8KIMmqis2GFFhCIQBy3FKi4EnYJgMAMR/QVFtI+5kQ5wvnKLhUSsDK\n" +
                    "r2QwBKHHNVP6wFvM7nZSuSFrXRi9KwDJqNF/Bq0/d0Oa+/J2ibHOQHIUYB2fp5fWiKDkTgJA4FbT\n" +
                    "PRU2cMaewkMpEFxCzSvg8N6WbNjNlQvM1xogbUPqRTDgqp5crcnTfu74lOnLzKyrV590dMDqjN6z\n" +
                    "18boHI0Sk5ZYYdQwP7HsWIBWgpPv+sMHJeyYN69/UMRXOp5Cz7PPVMCd5PP4XauUt9J1mEqCwZHX\n" +
                    "UpA8l/Mx9Dju1cSJN1RplAkdBrIT/YgltyIfVYmo4BAkuwpEpBoC+fi2WuGAMn22zIQUGQ8zzTVe\n" +
                    "Aishv1STTxtXiFUiXzV5w1VvYGWB4gAiESDKesHniB0TVeJpBBcLnbKx/G4BZJhjZHqa95P5QIl0\n" +
                    "guOipFNYXl6rNpfBNiHw010+vUJo/AOsc8hAfkbR7teqe0JyqscfLZw4dm9G88l+q8mo+vFSn9Ze\n" +
                    "ZG9BYlHcAOkTh0rlDlZSnRtOAA60k7jxzzGYtZe9EtSmpESIoiNPd5jTIMu0JWp7ZE9ueUarwifh\n" +
                    "UnFVbc6cbgeQgolwPVv7jEXdDxab0SKW01zYCo+07VI6GVZj23t8uY/YbfkEDNcMZgd63Sr8sll3\n" +
                    "R7BaAd5FFK4dXjO2OwI7pgp4xkZqSkknmU/llRp6oaP3YvTm5XrTtN1BVLljZnIyWfxGyMNq7cwg\n" +
                    "rfud+KxMEg7V0jiK3uexBdIFsaJEvwg3H3QKc0TLZsntACGctDLLMi09ATmUdClkbkXRHIdYEQnt\n" +
                    "cxRIJOucGGfRPUyMiBfYbfkEDNcMZq1U3nPeeGKAwIahP2pEzYG8NEjN2vs9Ku+xixpO/vClrFo/\n" +
                    "hlO+ybmA0UwbqaIeRbCpBCN/KTa1ce+9vX4MEPUfr+t6f5D7UZg6XHms6WQJYZQnwvcWseX1r1Ip\n" +
                    "CnXpCyCt+534rEwSDtXSOIre57EF0gWxokS/CDHOQuVUBXHl2CkVOL5aB3iuxGpWygodpKDlf+7N\n" +
                    "es8WC0BCLDcDxXk5tx8pMB825YW8Ub+WbUU2pgjfZprQT7UfQ3pyPt0yd+GKkcxU/HEtvG9lLQcf\n" +
                    "IPIXUahjauylMqruh8HpX76/sSe9MQ0OfBfOW2awJ9VZp5M+ffSMacpKL4gXEDEcWwcsM219ZAU5\n" +
                    "E6hQ4eKUdZ9wc/DxwsBMaSHp7/vLExiGQzLaG5DCqQQJVubRvlRP5ss4C/beuSgne/dPjya1XSya\n" +
                    "TneY1tG6soQW1ba/SN/g1O6FksRGYp7o6LA2jDicbKK01vKFFFLTpdKK5vfn4EDGj+Gms6k1eBMc\n" +
                    "gPGXfDHyro8guUkV1oMctx4GFUVOGU50iuhp6o7be5HJH/julqxN2kedKyj4u3xbV4rzIUoHi5Ck\n" +
                    "9cFKKebqHSEwJvHd7BQbMIVOrBdg6/ccpOPj/i15n4/Ptr8b4+Y9okM5z/a5PnW4T6/iHkijszZh\n" +
                    "rjRFXAOR6L0VgR6At8S+AFfDAb7Q6Turt+0I8eUBWUhfOl0W+jAyIZYKyW0ebhowzNFySaynk8BN\n" +
                    "/UBFOKVBuZq8vUaL79LNRKk2203KbBPY/AliPP68v/kacHyoVWpsEOPMS/tT/1uX+5IaqMQU4hMO\n" +
                    "TEEPywFP3MinbwU94xH+QYxCtBbXrpDu66yTLyK3wYIzkv0//BxZOTWHYRrgUrGFoSJWdAgGVyQR\n" +
                    "Nj+B4gAiESDKekVtX1D7/eaAbAVPavQTo3gq1EZ8wvrJ9Cym1I6LrOMOW/A/lDC5o3/b2lGsef+p\n" +
                    "5O/QI5+iVRXdBF3nhpaGDn+AcAJfL+GWHnMivfemdf/YCMcuKeUWyBWYWtBd0v5BJbrudzR7vXhU\n" +
                    "i+rrgClAe5Li1fZ7JPDSCQ7V0jiK3uexnOSe9U4sDu5YegsPbXv90DI6B0knssofvG9lLQcfIPIX\n" +
                    "UahjauylMqruh8HpX76/oCL6bqMpD5sU+/rXV79Fa3VU/LpOtJPGHV4ztjsCO6bdfcygERJJcQ==";

            decrypt = DesUtil.decrypt(str);
            System.out.println("保单解密"+decrypt);

    }
}
