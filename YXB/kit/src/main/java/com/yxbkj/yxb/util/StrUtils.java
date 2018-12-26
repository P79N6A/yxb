package com.yxbkj.yxb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName：StrUtils
 * Description：字符串工具类
 * Author：李明
 * Created：2017/07/27
 */
public class StrUtils {

    public static boolean notEmpty(String value){
        if(value == null){
            return false;
        }
        return value.trim().length() >0;
    }

    public static boolean isEmpty(String value){
        if(value == null){
            return true;
        }
        return value.trim().length()  == 0;
    }

    public static boolean equals(String str1, String str2){
        return notEmpty(str1) && str1.equals(str2);
    }

    public static String htmlEncode(String str){
        if(str == null){
            return "";
        }
        if(str.contains("<")){
            str=str.replaceAll("<", "&lt;");
        }
        return str;
    }

    /**
     * MethodName: isChineseCharacters
     * description: 判断是否中文
     * @param str
     * @return
     */
    public static boolean isChineseCharacters(String str){
        Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = p_str.matcher(str);
        return m.find() && m.group(0).equals(str);
    }

    /**
     * 字符串包含特定字符数量
     * @param count
     * @param str1
     * @param str2
     * @return 包含总数 + count
     */
    public static int countStr(int count, String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            return count;
        } else if (str1.indexOf(str2) != -1) {
            count++;
        }
        return countStr(count, str1.substring(str1.indexOf(str2) + str2.length()), str2);
    }

    private static String charStr = "ABC0DE1FGH2IJ3KL4MN5OP6QR7STU8VW9XYZ";

    /**
     * 获取随机字符串
     * @param length 字符串长度
     * @return
     */
    public static String getRandomStr(int length){
        StringBuffer str = new StringBuffer("");
        for (int i = 0; i < length; i++) {
            char a = charStr.charAt((int)(Math.random() * 36));
            str.append(a);
        }
        return str.toString();
    }

    /**
     * 判断某个字符串是否包含在某个数组中。如果数组为null则返回false
     *
     * @param str
     * @param array
     * @return
     */
    public static boolean isContainsString(String str, String[] array) {
        if (array == null) {
            return false;
        }
        for (String s : array) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换成用B,KB,MB,GB,TB单位来表示的大小
     */
    public static String formatFileLength(long sizes) {
        if (sizes < 0)
            sizes = 0;
        String str = "";
        if (sizes < 1024) { // 小于1KB
            str += "" + sizes + "B";
        } else if (sizes < 1024 * 1024) { // 小于1MB
            str += "" + numformat.format(sizes / 1024.0) + "K";
        } else if (sizes < 1024 * 1024 * 1024) { // 小于1GB
            str += "" + numformat.format(sizes / (1024 * 1024.0)) + "M";
        } else if (sizes < 1024 * 1024 * 1024 * 1024L) { // 小于1TB
            str += "" + numformat.format(sizes / (1024 * 1024 * 1024.0)) + "G";
        } else { // 大于1TB
            str += "" + numformat.format(sizes / (1024 * 1024 * 1024 * 1024.0)) + "T";
        }
        for (int i = 0; i < 8 - str.length(); i++) {
            str = " " + str;
        }
        return str;
    }

    /**
     * 把指定byte数组转换成16进制的字符串
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(hex[((b >> 4) & 0xF)]).append(hex[((b >> 0) & 0xF)]);
        }
        return sb.toString();
    }

    /**
     * 把指定16进制的字符串转换成byte数组
     */
    public static byte[] hexStringToBytes(String inString) {
        int fromLen = inString.length();
        int toLen = (fromLen + 1) / 2;
        final byte[] b = new byte[toLen];
        for (int i = 0; i < toLen; i++) {
            b[i] = (byte) hexPairToInt(inString.substring(i * 2, (i + 1) * 2));
        }
        return b;
    }

    /**
     * 将数组进行排序然后再组成字符串
     * @param totalStringList
     * @return
     */
    public static String ArrayToSortString(List<String> totalStringList) {
        StringBuffer str = new StringBuffer("");

        if (totalStringList != null && totalStringList.size() > 0) {
            String[] strs = totalStringList.toArray(new String[totalStringList.size()]);
            Arrays.sort(strs);
            for (String s : strs) {
                str.append(s);
            }
        }
        return str.toString();
    }

    /**
     * 把指定cid字符串转换成byte数组
     */
    public static byte[] convertStringCid2Bytes(String sCid) {
        byte[] cid = new byte[20];
        for (int i = 0; i < cid.length; i++) {
            cid[i] = (byte) Integer.parseInt(sCid.substring(i * 2, i * 2 + 2), 16);
        }
        return cid;
    }

    /**
     * 在指定字符串数组里查找指定字符串，找到则返回索引号，找不到返回-1
     */
    public static int search(String no, String[] noes) {
        for (int i = 0; i < noes.length; i++) {
            if (no.equals(noes[i]))
                return i;
        }
        return -1;
    }

    private static int hexPairToInt(String inString) {
        String digits = "0123456789abcdef";
        String s = inString.toLowerCase();
        int n = 0;
        int thisDigit = 0;
        int sLen = s.length();
        if (sLen > 2)
            sLen = 2;
        for (int i = 0; i < sLen; i++) {
            thisDigit = digits.indexOf(s.substring(i, i + 1));
            if (thisDigit < 0)
                throw new NumberFormatException();
            if (i == 0)
                thisDigit *= 0x10;
            n += thisDigit;
        }
        return n;
    }

    public static String read(InputStream in, String charset) throws IOException {
        int pos = -1;
        byte[] buf = new byte[1024 * 8];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((pos = in.read(buf)) != -1) {
            out.write(buf, 0, pos);
        }
        return (charset == null) ? new String(out.toByteArray()) : new String(out.toByteArray(), charset);
    }

    public static String read(InputStream in) throws IOException {
        return read(in, null);
    }

    /**
     * 转换成js代码
     */
    public static final String escapeJs(String unicode) {
        return org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(unicode);
    }

    /**
     * 对字符进行URL编码。客户端使用js的decodeURIComponent进行解码
     * @param str 字符串源码
     * @return URL编码后的字符串
     */
    public static String encodeURL(String str) {
        try {
            return java.net.URLEncoder.encode(str, "utf-8").replaceAll("\\+", "%20");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 对url进行解码
     * @param str
     * @return
     */
    public static String decodeURL(String str) {
        try {
            return java.net.URLDecoder.decode(str, "utf-8");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 转换成html代码
     */
    public static final String escapeHtml(String unicode) {
        return org.apache.commons.lang.StringEscapeUtils.escapeHtml(unicode);
    }


    /**
     * 判断字符串是否为非空
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * 需要进行过滤并替换的sql字符
     */
    private static final String[][] sqlhandles = { { "'", "''" }, { "\\\\", "\\\\\\\\" } };
    private static final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final Format numformat = new DecimalFormat("#.##");
    private static final String zeros = "00000000000000000000";

    public static String listingString(Object data) {
        return listingString(data, true);
    }

    public static String listingString(Object data, boolean snapped) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(data.getClass().getSimpleName()).append("[");
        try {
            boolean flag = false;
            boolean isstring = true;
            Object obj = null;
            String str = "";
            for (java.lang.reflect.Method m : data.getClass().getDeclaredMethods()) {
                if ((m.getName().startsWith("get") || m.getName().startsWith("is")) && m.getParameterTypes().length == 0) {
                    int l = m.getName().startsWith("get") ? 3 : 2;
                    obj = m.invoke(data);
                    if (snapped && obj == null)
                        continue;
                    isstring = obj instanceof String;
                    if (!isstring && snapped) {
                        if (obj instanceof Number && ((Number) obj).intValue() == 0)
                            continue;
                        if (obj instanceof Boolean && ((Boolean) obj) == false)
                            continue;
                    }
                    str = isstring ? ("\"" + obj + "\"") : String.valueOf(obj);
                    if (flag)
                        sb.append(", ");
                    sb.append(m.getName().substring(l).toLowerCase()).append("=").append(str);
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("]");
        return sb.toString();
    }

    public static String subString(String t, int size) {
        if (t == null)
            return null;
        int hansize = size * 3 / 2;
        int len = hansize;
        if (t.length() > size) {
            int p = 0;
            for (int i = 0; i < hansize && i < t.length(); i++) {
                if (t.charAt(i) > 127)
                    p++;
            }
            len -= p * 2 / 3;
            if (len < size)
                len = size;
            if (t.length() <= len)
                return t;
            return t.substring(0, len) + "...";
        }
        return t;
    }


    /**
     * 将字符串中可能包含有非法的sql字符进行过滤，例如过滤'。
     * @param str  需要进行过滤的字符串
     * @return 过滤后的安全字符串
     */
    public static final String escapeSql(String str) {
        if (str == null) {
            return "";
        }
        for (String[] ss : sqlhandles) {
            str = str.replaceAll(ss[0], ss[1]);
        }
        return str;
    }

    /**
     * 将数值转换成特定长度的字符串
     * @param value
     * @param length
     * @return
     */
    public static String toLenString(long value, int length) {
        String val = value + "";
        if (val.length() > length) {
            try {
                throw new Exception("定义的长度小于数值的长度。");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (val.length() < length) {
            return zeros.substring(0, length - val.length()) + val;
        } else {
            return val;
        }
    }

    /**
     * 将字符串中可能包含有非法的sql字符进行过滤，例如过滤'。
     * @param obj  过滤对象
     * @return 过滤后的安全字符串
     */
    public static final String escapeSql(Object obj) {
        if (obj == null) {
            return "";
        }
        return escapeSql(obj.toString());
    }

    /**
     * 将对象安全转换成int类型，失败时返回0
     * @param o 目标对象
     * @return int数字
     */
    public static int safeToInt(Object o) {
        int rs = 0;
        try {
            rs = Integer.parseInt(o.toString());
        } catch (Exception ex) {
            rs = 0;
        }
        return rs;
    }

    /**
     * 将对象安全转换成short类型
     * @param o 目标对象
     * @return short数字
     */
    public static int safeToShort(Object o) {
        short rs = 0;
        try {
            rs = Short.parseShort(o.toString());
        } catch (Exception ex) {
            rs = 0;
        }
        return rs;
    }

    /**
     * 将对象安全转换成long类型
     * @param o 目标对象
     * @return long数字
     */
    public static long safeToLong(Object o) {
        long rs = 0;
        try {
            rs = Long.parseLong(o.toString());
        } catch (Exception ex) {
            rs = 0;
        }
        return rs;
    }

    /**
     * 将对象安全转换成double类型
     * @param o 目标对象
     * @return double数字
     */
    public static double safeToDouble(Object o) {
        double rs = 0;
        try {
            rs = Double.parseDouble(o.toString());
        } catch (Exception ex) {
            rs = 0;
        }
        return rs;
    }

    /**
     * 得到系统的时间戳
     */
    public static String getTradeSn() {
        return "" + new java.util.Date().getTime();
    }

    /**
     * 尝试将对象转换成double类型，如果失败时也不抛出异常而返回0
     * @param fieldValue
     * @return
     */
    public static double tryParseDouble(Object fieldValue) {
        try {
            double rs = (Double) fieldValue;
            return rs;
        } catch (Exception ex) {
            try {
                return Double.parseDouble(fieldValue.toString());
            } catch (Exception exx) {
                return 0;
            }
        }
    }

    /**
     * 将手机号码中的中间四位转换成*
     * @param src
     * @return
     */
    public static String phoneChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        StringBuffer tempStr = new StringBuffer();
        int srcLength = src.length();
        for (int i = 0; i < srcLength; i++) {
            if (i > 2 && i < 7) {
                tempStr.append("*");
            } else {
                tempStr.append(src.charAt(i));
            }
        }
        return tempStr.toString();
    }

    /**
     * 将银行卡号限前4后3中间用****填充
     * @param src
     * @return
     */
    public static String bankNoChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        return src.substring(0, 4) + "****" + src.substring(src.length() - 3, src.length());
    }

    /**
     * 字符串转换成带*的字符串,转换[start,end)位置的字符为*
     * @param str       待转换字符串
     * @param start     开始位置
     * @param end       结束位置
     * @return  转换后的字符串
     */
    public static String stringChangeStar(String str,int start,int end){
        if(str == null || str.trim().length()==0){
            return "";
        }
        char[] chars = str.trim().toCharArray();
        for(int i=0;i<chars.length;i++){
            if(i>= start && i< end){
                chars[i] = '*';
            }
        }
        return String.valueOf(chars);
    }

    /**
     * 手机号码转为带*的手机号码，显示形式：186****3578
     * @param phone 手机号码
     * @return  带*号的手机号码
     */
    public static String getStarPhone(String phone){
        if(phone == null || phone.trim().length()==0){
            return "";
        }
        return stringChangeStar(phone,3,phone.length()-4);
    }

    /**
     * 用户姓名转为带*号的姓名，显示形式：任**
     * @param name  姓名
     * @return  带*号的姓名
     */
    public static String getStarName(String name){
        if(name == null || name.trim().length()==0){
            return "";
        }
        return stringChangeStar(name,1,name.length());
    }

    /**
     * 身份证号码转为带*的身份证号码，显示形式：500233********6392
     * @param idNo  身份证号码
     * @return  带*的身份证号码
     */
    public static String getStarIdNo(String idNo){
        if(idNo == null || idNo.trim().length()==0){
            return "";
        }
        return stringChangeStar(idNo,6,14);
    }

    /**
     * 银行卡号转为带*的银行卡，显示形式：************8516
     * @param bankNo    银行卡号
     * @return  带*的银行卡号
     */
    public static String getStarBank(String bankNo){
        if(bankNo == null || bankNo.trim().length()==0){
            return "";
        }
        return stringChangeStar(bankNo,0,bankNo.length()-4);
    }
    /**
     * 将真实姓名限前**后1个名字
     * @param src
     * @return
     */
    public static String realNameChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        return "**" + src.charAt(src.length() - 1);
    }

    /**
     * 将收款人按长度前面用**显示
     * @param src
     * @return
     */
    public static String PayeeNameChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        StringBuffer mark = new StringBuffer("");
        if (src.trim().length() > 7) {
            for (int i = 0; i < src.trim().length() - 4; i++) {
                mark.append("*");
            }
            return src.substring(0, 4) + mark;
        }
        if (src.trim().length() > 3) {
            for (int i = 0; i < src.trim().length() - 2; i++) {
                mark.append("*");
            }
            return src.substring(0, 2) + mark;
        }
        if (src.trim().length() > 1) {
            for (int i = 0; i < src.trim().length() - 1; i++) {
                mark.append("*");
            }
            return src.substring(0, 1) + mark;
        }
        return src.substring(0, 1) + "*";
    }

    /**
     * 将身份证号限前4后4中间用****填充
     * @param src
     * @return
     */
    public static String idCardChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        return src.substring(0, 4) + "****" + src.substring(src.length() - 4, src.length());
    }

    /**
     * 将email限前4后4中间用****填充
     * @param src
     * @return
     */
    public static String emailChange(String src) {
        if (src == null || src.trim().length() <= 0) {
            return "";
        }
        return src.substring(0, 4) + "****" + src.substring(src.length() - 4, src.length());
    }

    /**
     * 去空格
     * @param param
     * @return
     */
    public static String stringToTrim(String param) {
        return param;
    }

    /**
     * 日期字符串转换为LocalDate，格式为yyyy-MM-dd
     * @param charStr   待验证字符串
     * @return  LocalDate
     */
    public static LocalDate strToLocalDate(String charStr){
        if(charStr == null || charStr.length()==0){
            return null;
        }
        try {
            return LocalDate.parse(charStr);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 判断字符串是否不是日期字符串，格式为yyyy-MM-dd
     * @param str   字符串
     * @return  判断结果，true-不是日期字符串，false-是日期字符串
     */
    public static boolean isNotDate(String str){
        return strToLocalDate(str)==null;
    }

    /**
     * 判断字符串是否日期字符串
     * @param str   日期字符串
     * @return  判断结果，true-日期字符串，false-不是日期字符串
     */
    public static boolean isDate(String str){
        return !isNotDate(str);
    }
    
    /**
     * 
     * 字符串替换
     */
	public static String replaceStr(String sourceStr, String replaceKey, String replaceValue) {
		String replaceStrReg = "";
		for (char str_char : replaceKey.toCharArray()) {
			replaceStrReg += "[";
			replaceStrReg += str_char;
			replaceStrReg += "]";
		}
		String startReg = "^" + replaceStrReg + "([\\+\\-\\*/,)])";
		String endReg = "([\\+\\-\\*/,(])" + replaceStrReg + "$";
		String reg = "([^a-zA-Z])(" + replaceStrReg + ")" + "([^a-zA-Z])";
		String endStr = sourceStr;
		while (matcheStr(endStr, replaceKey)) {
			endStr = endStr.replaceAll(startReg, replaceValue + "$1");
			endStr = endStr.replaceAll(reg, "$1" + replaceValue + "$3");
			endStr = endStr.replaceAll(endReg, "$1" + replaceValue);
		}
		return endStr;
	}

	public static Boolean matcheStr(String sourceStr, String matchStr) {
		String replaceStrReg = "";
		for (char str_char : matchStr.toCharArray()) {
			replaceStrReg += "[";
			replaceStrReg += str_char;
			replaceStrReg += "]";
		}
		String startReg = "^" + replaceStrReg + "([\\+\\-\\*/,)])[\\s\\S]*";
		String endReg = "[\\s\\S]*([\\+\\-\\*/,(])" + replaceStrReg + "$";
		String reg = "[\\s\\S]*([^A-Za-z])(" + replaceStrReg + ")" + "([^A-Za-z])[\\s\\S]*";
		if (sourceStr.matches(startReg) || sourceStr.matches(reg) || sourceStr.matches(endReg)) {
			return true;
		} else {
			return false;
		}
	}

	
}
