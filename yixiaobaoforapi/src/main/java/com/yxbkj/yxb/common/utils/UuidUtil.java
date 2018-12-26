package com.yxbkj.yxb.common.utils;

import java.util.UUID;

public class UuidUtil {

    public static String randomUuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if (uuid.length() > 36)
            uuid = uuid.substring(0, 36);
        return uuid;
    }

}
