package com.yxbkj.yxb.util;

import com.yxbkj.yxb.entity.module.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @Description ：
 * @Author ： 李明
 * @Date ： 2017/07/19 09:59
 */
public class DispatcherUtils {

    private static Logger logger = LoggerFactory.getLogger(DispatcherUtils.class);

    public static Integer parseInteger(Object obj){
        Integer result = null;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Integer.parseInt(String.valueOf(obj));
        }
        return result;
    }
    public static int parseInt(Object obj){
        Integer result = parseInteger(obj);
        if(result == null){
            return 0;
        }
        return result;
    }

    public static Long parseLong(Object obj){
        Long result = null;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Long.parseLong(String.valueOf(obj));
        }
        return result;
    }
    public static Long parseLongFinal(Object obj){
        long result = 0l;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Long.parseLong(String.valueOf(obj));
        }
        return result;
    }

    public static boolean parseBoolean(Object obj){
        boolean result = false;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Boolean.parseBoolean(String.valueOf(obj));
        }
        return result;
    }

    public static String parseString(Object obj){
        String result = null;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = String.valueOf(obj);
        }
        return result;
    }

    public static Object parseObject(Object obj){
        Object result = null;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = obj;
        }
        return result;
    }
    public static Double parseDouble(Object obj){
        Double result = null;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Double.parseDouble(String.valueOf(obj));
        }
        return result;
    }
    public static double parseDoubleFinal(Object obj){
        double result = 0.00;
        if(obj != null && !"null".equals(obj) && !"".equals(obj)){
            result = Double.parseDouble(String.valueOf(obj));
        }
        return result;
    }
    public static void disposeNull(Map<String, Object> map){
        Map<String, Object> result = new HashMap<String, Object>();
        if(map != null && map.size() > 0){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.put(entry.getKey(), parseObject(entry.getValue()));
            }
            map = result;
        }
    }

    public static Map<String, Object> disposeResultData(Result result){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(result != null && result.getCode() == 1) {
            Map<String, Object> relMap = (Map<String, Object>) result.getData();
            if (relMap != null && relMap.size() > 0) {
                resultMap = relMap;
                disposeNull(resultMap);
            }
        }
        return resultMap;
    }

    public static List<Map<String, Object>> disposeResultDataList(Result result){
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(result != null && result.getCode() == 1) {
            List<Map<String, Object>> relMap = (List<Map<String, Object>>) result.getData();
            if (relMap != null && relMap.size() > 0) {
                for (Map<String, Object> map: relMap){
                    disposeNull(map);
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }

    /**
     * 毫秒数转换为yyyy-MM-dd HH:mm:ss格式
     * @param times
     * @return
     */
    public static Date parseDateTime(Long times){
        Date result = null;
        if(times != null && times > 0){
            String dateTime = parseDateTimeStr(times);
            try {
                result = DateUtils.parseDate(dateTime, DateUtils.DATE_FORMAT_DATETIME);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 毫秒数转换为yyyy-MM-dd HH:mm:ss格式
     * @param times
     * @return
     */
    public static String parseDateTimeStr(Long times){
        return parseDateTimeStr(times, DateUtils.DATE_FORMAT_DATETIME);
    }
    /**
     * 日期转换为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static String parseDateTimeStr(Date date, String pattern){
        String result = null;
        if(date != null) {
            result = DateUtils.dateToStr(date, pattern);
        }
        return result;
    }

    /**
     * 转换为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static String parseDateTimeStr(Date date){
        return parseDateTimeStr(date, DateUtils.DATE_FORMAT_DATETIME);
    }
    /**
     * 毫秒数转换为yyyy-MM-dd HH:mm:ss格式
     * @param times
     * @return
     */
    public static String parseDateTimeStr(Long times, String pattern){
        String result = null;
        if(times != null && times > 0) {
            if (times != null && times > 0) {
                result = DateUtils.dateToStr(new Timestamp(times), pattern);
            }
        }
        return result;
    }

    /**
     * 日期转换为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static Date parseDateTime(String date, String pattern){
        Date result = null;
        if(date != null) {
            try {
                result = DateUtils.parseDate(date, pattern);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转换为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static Date parseDateTime(String date){
        return parseDateTime(date, DateUtils.DATE_FORMAT_DATETIME);
    }

    /**
     * 读文件
     * @param path
     * @return
     */
    public static String readFile(String path) {
        if (path == null || "".equals(path.trim())) return null;

        File file = new File(path);
        BufferedReader reader = null;
        StringBuilder str = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String temp = null;
            while ((temp= reader.readLine()) != null) {
                str.append(temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件读取失败，路径为：{}", path);
            throw new RuntimeException("文件读取失败", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str.toString();
    }

    public static boolean saveFile(String queryData, String filePath) {

        try {
            File destFile = new File(filePath);
            //判断目标文件所在的目录是否存在
            if(!destFile.getParentFile().exists()) {
                //如果目标文件所在的目录不存在，则创建父目录
                System.out.println("目标文件所在目录不存在，准备创建它！");
                if(!destFile.getParentFile().mkdirs()) {
                    System.out.println("创建目标文件所在目录失败！");
                    return false;
                }
            }
            if(!destFile.exists()){
                destFile.createNewFile();
                destFile.setReadable(true, false);
                destFile.setWritable(true, false);
                //destFile.setExecutable(true,false);
            }
            FileOutputStream out = new FileOutputStream(filePath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
            int len = 4096;
            int c = queryData.length() / len;
            int lef = queryData.length() % len;
            int index = 0;
            for (int i = 0; i < c;) {
                bw.write(queryData, index, len);
                ++i;
                index = i * len;
            }
            bw.write(queryData, index, lef);
            bw.flush();
            bw.close();
            return true;
        }catch (Exception e){
            System.out.println("写文件错误："+e.getMessage());
        }
        return false;

    }

    /**
     * 使用Introspector进行转换
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null || map.size() == 0)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }

    public static <T> T toBean(Class<T> beanCls, Result rs) throws Exception {
        T t = null;
        Map<String,Object> map = disposeResultData(rs);
        t = map2Bean(map,beanCls);
        return t;
    }
    public static <T> List<T> toBeanList(Class<T> beanCls, Result rs) throws Exception {
        List<T> tList = new ArrayList<T>();
        List<Map<String,Object>> mapList = disposeResultDataList(rs);
        if(mapList != null && mapList.size() > 0){
            for (Map<String, Object> map : mapList){
                T t = map2Bean(map,beanCls);
                tList.add(t);
            }
        }
        return tList;
    }

    public static <T, K, V> T map2Bean(Map<K, V> mp, Class<T> beanCls) throws Exception, IllegalArgumentException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanCls);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        T t = beanCls.newInstance();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (mp.containsKey(key)) {
                Object value = mp.get(key);
                Method setter = property.getWriteMethod();// Java中提供了用来访问某个属性的
                setter.invoke(t, value);
            }
        }
        return t;
    }

    public static <T, K, V> Map<String, Object> bean2Map(T bean, Map<String, Object> mp) throws Exception, IllegalAccessException {

        if (bean == null) {
            return null;
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();

            if (!key.equals("class")) {

                Method getter = property.getReadMethod();// Java中提供了用来访问某个属性的
                // getter/setter方法
                Object value;

                value = getter.invoke(bean);
                mp.put(key, value);
            }

        }
        return mp;

    }
}
