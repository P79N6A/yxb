package com.yxbkj.yxb.util.zrbx;

public class DESPlusTest {
	/**
	 * 将16进制数转换为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String deUnicode(String content) {
		String enUnicode = null;
		String deUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (enUnicode == null) {
				enUnicode = String.valueOf(content.charAt(i));
			} else {
				enUnicode = enUnicode + content.charAt(i);
			}
			if (i % 4 == 3) {
				if (enUnicode != null) {
					if (deUnicode == null) {
						deUnicode = String.valueOf((char) Integer.valueOf(
								enUnicode, 16).intValue());
					} else {
						deUnicode = deUnicode
								+ String.valueOf((char) Integer.valueOf(
										enUnicode, 16).intValue());
					}
				}
				enUnicode = null;
			}
		}
		return deUnicode;
	}

	/**
	 * 将字符串转换为16进制数
	 * 
	 * @param content
	 * @return
	 */
	public static String enUnicode(String content) {//
		String enUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (i == 0) {
				enUnicode = getHexString(Integer.toHexString(content.charAt(i))
						.toUpperCase());
			} else {
				enUnicode = enUnicode
						+ getHexString(Integer.toHexString(content.charAt(i))
								.toUpperCase());
			}
		}
		return enUnicode;
	}

	private static String getHexString(String hexString) {
		String hexStr = "";
		for (int i = hexString.length(); i < 4; i++) {
			if (i == hexString.length())
				hexStr = "0";
			else
				hexStr = hexStr + "0";
		}
		return hexStr + hexString;
	}
}
