package com.yxbkj.yxb.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class XmlUtils {


    public static Map<String, Object> Dom2Map(Document doc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if (list.size() > 0) {
                map.put(e.getName(), Dom2Map(e));
            } else
                map.put(e.getName(), e.getText());
        }
        return map;
    }


    public static Map Dom2Map(Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = Dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }


    public static Map<String, Object> xmlToMap(String str) throws Exception {
        Document doc = null;
        doc = DocumentHelper.parseText(str);
        return XmlUtils.Dom2Map(doc);
    }


    /**
     * 作者: 李明
     * 描述: 强行截取xml字符串
     * 备注:  工具类
     * @return
     */
    public static String getRes(String res,String key){
       try{
           String[] responseCodes = res.split(key);
           String responseCode = responseCodes[1];
           String[] split = responseCodes[1].split("</");
           return  split[0].replace(">","").replace("<","");
       }catch (Exception e){
           e.printStackTrace();
           return "";
       }
    }

}
