package com.yxbkj.yxb.util.pic;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


public class ImgUtil {





    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String filePath = "/usr/yxb/pic.jpg";//目标输出文件名称



        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538223559355&di=58a10ebee12eac61c647bb1b9a15119f&imgtype=0&src=http%3A%2F%2Fpic3.nipic.com%2F20090608%2F215933_101900087_2.jpg";
        String qr_url = "http://www.baidu.com";
        int pix = 50;//两行文字的间隔 50
        int fontPix = 0;
        int picPix = 0;
        int fontSize = 20;
        String qrImg = "/usr/yxb/qrcode.jpg";//目标输出二维码名称
        InputStream picInputStream = getPicInputStream(url);//目标文件流
        writeFile(picInputStream,filePath);//目标文件流写入文件
        ByteArrayOutputStream qrCodeUrl = getQrCodeUrl(qr_url,qrImg);//目标二维码写入文件
        String bigImg = filePath;
        String smallImg = qrImg;
        String content = "测试一下测试一下测试一下测试一下测试一下测试一下";
        String content2 = "测试一下测试一下测试一下测试一下测试一下测试一下";
        String outPath = "/usr/yxb/3.jpg";//输出的最终文件
        int imgWidth = getImgWidth(new File(bigImg));
        int imgHeight = getImgHeight(new File(bigImg));
        int imgWidth_small = getImgWidth(new File((smallImg)));
        int imgHeight_small = getImgHeight(new File(smallImg));
        //File file = new File(qrCodeUrl);
        try {
            String file_path  = bigImgAddSmallImgAndText(bigImg, smallImg, imgWidth - imgWidth_small+picPix, imgHeight - imgHeight_small-picPix, content, content2,fontSize, pix,0+fontPix, imgHeight - imgHeight_small+fontPix, outPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("合计耗时"+(start-System.currentTimeMillis()));
    }




    /**
     * 获取图片地址
     * @param pix
     * @param fontPix
     * @param picPix
     * @param fontSize
     * @param firstStr
     * @param secondStr
     * @param url
     * @param qr_url
     * @return
     * @throws Exception
     */
    public static String  getPic(int pix,int fontPix,int picPix,int fontSize,String firstStr, String secondStr, String url, String qr_url
    ) throws Exception {
        long start = System.currentTimeMillis();
       // String basePath = "/usr/yxb/qrImage/";
         String basePath = "C:/";
        long currentTimeMillis = System.currentTimeMillis();
        String filePath = basePath+currentTimeMillis+"pic.jpg";//目标输出文件名称
//        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538223559355&di=58a10ebee12eac61c647bb1b9a15119f&imgtype=0&src=http%3A%2F%2Fpic3.nipic.com%2F20090608%2F215933_101900087_2.jpg";
//        String qr_url = "http://www.baidu.com";
//        int pix = 50;//两行文字的间隔 50
//        int fontPix = 0;//文字偏移 距离左侧
//        int picPix = 0;//图片偏移  距离右侧
//        int fontSize = 20;//文字大小
        String qrImg = basePath+currentTimeMillis+"qrcode.jpg";//目标输出二维码名称
        File file = new File(qrImg);
        //file.mkdirs();
        InputStream picInputStream = getPicInputStream(url);//目标文件流
        writeFile(picInputStream,filePath);//目标文件流写入文件
        ByteArrayOutputStream qrCodeUrl = getQrCodeUrl(qr_url,qrImg);//目标二维码写入文件
        String bigImg = filePath;
        String smallImg = qrImg;
        String content = firstStr;//第一行文字
        String content2 = secondStr;//第二行文字
        String outPath = basePath+currentTimeMillis+"out.jpg";//输出的最终文件
        int imgWidth = getImgWidth(new File(bigImg));
        int imgHeight = getImgHeight(new File(bigImg));
        int imgWidth_small = getImgWidth(new File((smallImg)));
        int imgHeight_small = getImgHeight(new File(smallImg));
        //File file = new File(qrCodeUrl);
        try {
            String file_path  = bigImgAddSmallImgAndText(bigImg, smallImg, imgWidth - imgWidth_small+picPix, imgHeight - imgHeight_small-picPix, content, content2,fontSize, pix,0+fontPix, imgHeight - imgHeight_small+fontPix, outPath);
            return file_path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("合计耗时"+(start-System.currentTimeMillis()));
        return null;
    }


    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }


    /**
     * 作者:    李明
     * 描述:    生成二维码
     * 备注:
     *
     * @param url
     * @return
     */
    public static ByteArrayOutputStream getQrCodeUrl(String url,String filePath) {
        String url_address = "";
        try {
            BufferedImage image = QRCodeUtil.createImage(url, "", false);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            writeFile(is,filePath);
            os.close();
            is.close();
            return os;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void writeFile(InputStream inputStream,String filePath) throws IOException {
        //读取文件
        FileOutputStream fos = new FileOutputStream(filePath);//保存文件
        int len;
        Byte[] b = new Byte[1024];
        while ((len = inputStream.read()) != -1) {//判读文件内容是否存在
            fos.write(len);//写入文件
        }
        inputStream.close();
        fos.close();
    }


    public static InputStream getPicInputStream(String url_address) throws IOException {
        //new一个URL对象   
        URL url = new URL(url_address);
        //打开链接 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"      
        //     conn.setRequestMethod("GET");        
        //   //超时响应时间为5秒        
        conn.setConnectTimeout(5 * 1000);
        //   //通过输入流获取图片数据         
        InputStream inStream = conn.getInputStream();
        return inStream;
    }


    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getWidth(null);// 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static int getImgWidth(InputStream is) {
        BufferedImage src = null;
        int ret = -1;
        try {
            //is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getWidth(null);// 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static int getImgHeight(InputStream is) {
        BufferedImage src = null;
        int ret = -1;
        try {
            src = ImageIO.read(is);
            ret = src.getHeight(null);// 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getHeight(null);// 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /***
     * 在一张大图张添加小图和文字
     * @param bigImgPath 大图的路径
     * @param smallImgPath 小图的路径
     * @param sx    小图在大图上x抽位置
     * @param sy    小图在大图上y抽位置
     * @param content   文字内容
     * @param cx    文字在大图上y抽位置
     * @param cy    文字在大图上y抽位置
     * @param outPathWithFileName 结果输出路径
     */
    public static String bigImgAddSmallImgAndText(String bigImgPath
            , String smallImgPath, int sx, int sy
            , String content,String content2,int fontSize, int pix,int cx, int cy
            , String outPathWithFileName) throws IOException {
        //主图片的路径
        InputStream is = new FileInputStream(bigImgPath);
        //通过JPEG图象流创建JPEG数据流解码器
       // JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
        //解码当前JPEG数据流，返回BufferedImage对象
        //BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
        BufferedImage buffImg  = ImageIO.read(is);
        //得到画笔对象
        Graphics g = buffImg.getGraphics();
        //小图片的路径
        ImageIcon imgIcon = new ImageIcon(smallImgPath);
        //得到Image对象。
        Image img = imgIcon.getImage();
        //将小图片绘到大图片上,5,300 .表示你的小图片在大图片上的位置。
        g.drawImage(img, sx, sy, null);
        //设置颜色。
        g.setColor(Color.WHITE);
        //最后一个参数用来设置字体的大小
        if (content != null) {
            Font f = new Font("宋体", Font.PLAIN, fontSize);
            Color mycolor = Color.RED;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(f);
            g.drawString(content, cx, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
        }
        //最后一个参数用来设置字体的大小
        if (content2 != null) {
            Font f = new Font("宋体", Font.PLAIN, fontSize);
            Color mycolor = Color.RED;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(f);
            g.drawString(content2, cx, cy+pix); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
        }
        g.dispose();
//        OutputStream os = new FileOutputStream(outPathWithFileName);
//        //创键编码器，用于编码内存中的图象数据。
//        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
//        en.encode(buffImg);
        saveImage(buffImg,outPathWithFileName);
        is.close();
        //os.close();
        return outPathWithFileName;
    }
   public static void saveImage(BufferedImage dstImage, String dstName) throws IOException {
        String formatName = dstName.substring(dstName.lastIndexOf(".") + 1);
        //FileOutputStream out = new FileOutputStream(dstName);
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //encoder.encode(dstImage);
        ImageIO.write(dstImage, /*"GIF"*/ formatName /* format desired */ , new File(dstName) /* target */ );
    }

    /***
     * 在一张大图张添加小图和文字
     * @param is 输入流
     * @param smallImgPath 小图的路径
     * @param sx    小图在大图上x抽位置
     * @param sy    小图在大图上y抽位置
     * @param content   文字内容
     * @param cx    文字在大图上y抽位置
     * @param cy    文字在大图上y抽位置
     * @param outPathWithFileName 结果输出路径
     */
    public static void bigImgAddSmallImgAndText(InputStream is
            , String smallImgPath, int sx, int sy
            , String content, int cx, int cy
            , String outPathWithFileName) throws IOException {
        //主图片的路径
        //通过JPEG图象流创建JPEG数据流解码器
        //JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);

        BufferedImage buffImg  = ImageIO.read(is);

        //解码当前JPEG数据流，返回BufferedImage对象
        //BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();

        //ImageIO.write(tag, "jpeg", destImage);

        //得到画笔对象
        Graphics g = buffImg.getGraphics();
        //小图片的路径
        ImageIcon imgIcon = new ImageIcon(smallImgPath);
        //得到Image对象。
        Image img = imgIcon.getImage();
        //将小图片绘到大图片上,5,300 .表示你的小图片在大图片上的位置。
        g.drawImage(img, sx, sy, null);
        //设置颜色。
        g.setColor(Color.WHITE);
        //最后一个参数用来设置字体的大小
        if (content != null) {
            Font f = new Font("宋体", Font.PLAIN, 10);
            Color mycolor = Color.RED;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(f);
            g.drawString(content, cx, cy); //表示这段文字在图片上的位置(cx,cy) .第一个是你设置的内容。
        }
        g.dispose();
//        OutputStream os = new FileOutputStream(outPathWithFileName);
//        //创键编码器，用于编码内存中的图象数据。
//        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
//        en.encode(buffImg);

        saveImage(buffImg,outPathWithFileName);

        is.close();
       // os.close();
    }


}
