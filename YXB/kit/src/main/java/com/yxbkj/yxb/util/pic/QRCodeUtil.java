package com.yxbkj.yxb.util.pic;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class QRCodeUtil {

	
	  
	    private static final String CHARSET = "utf-8";  
	    private static final String FORMAT_NAME = "JPG";  
	    // 二维码尺寸  
	    private static final int QRCODE_SIZE = 300;
	    // LOGO宽度  
	    private static final int WIDTH = 60;
	    // LOGO高度  
	    private static final int HEIGHT = 60;
	      
	  
	    /** 
	     * 生成二维码 
	     * @param content   源内容 
	     * @param imgPath   生成二维码保存的路径 
	     * @param needCompress  是否要压缩 
	     * @return      返回二维码图片 
	     * @throws Exception 
	     */  
	    public static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
	        Hashtable hints = new Hashtable();  
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
	        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
	        hints.put(EncodeHintType.MARGIN, 1);  
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
	                hints);  
	        int width = bitMatrix.getWidth();  
	        int height = bitMatrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);  
	            }  
	        }  
	        if (imgPath == null || "".equals(imgPath)) {  
	            return image;  
	        }  
	        // 插入图片  
	        //QRCodeUtil.insertImage(image, imgPath, needCompress);  
	        return image;   
	    }
}
