package com.yxbkj.yxb.common.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiParamsHelper {

    /**
     * 将对象转换为map
     * @param object
     * @return
     */
    public static Map<String, Object> getParams(Object object){
        Map<String, Object> params = new HashMap<>();
        Field[] fields = ReflectUtil.getFields(object.getClass());
        for ( Field field: fields){
            String fieldname = field.getName();
            Object value = ReflectUtil.getFieldValue(object, fieldname);
            if (value == null)
                continue;
            params.put(fieldname, value);
        }

        return params;
    }

    /**
     * 将List对象转换为list&lt;Map&gt;
     * @param objects
     * @return
     */
    public static List<Map<String, Object>> getListParams(List<? extends Object> objects){
        List<Map<String, Object>> params = new ArrayList<>();
        for (Object o: objects){
            params.add(getParams(o));
        }

        return params;
    }

}
