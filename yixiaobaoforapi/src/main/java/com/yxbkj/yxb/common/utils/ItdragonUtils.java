package com.yxbkj.yxb.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * 工具类（对密码进行MD5加密）
 * @author itdragon
 */
public class ItdragonUtils {

	private static final String ALGORITHM_NAME = "MD5";
    private static final Integer HASH_ITERATIONS = 1024;

    public static void entryptPassword(Object user) {
       /* String salt = UUID.randomUUID().toString();
        String temPassword = user.getPassWord();
        Object md5Password = new SimpleHash(ALGORITHM_NAME, temPassword, ByteSource.Util.bytes(salt), HASH_ITERATIONS);
        user.setSalt(salt);
        user.setPassWord(md5Password.toString());*/
    }
}
