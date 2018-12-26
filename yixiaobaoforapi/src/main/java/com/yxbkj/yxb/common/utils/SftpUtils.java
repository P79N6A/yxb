/**  
* 
*/ 
package com.yxbkj.yxb.common.utils;



import com.jcraft.jsch.*;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @ClassName: SftpUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lingy
 * @date 2018年6月13日 上午10:42:12
 * @version V1.0
 */
public class SftpUtils implements AutoCloseable {
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SftpUtils.class); 

    private Session session = null;
    private ChannelSftp channel = null;


    /**
     * 连接sftp服务器
     *
     * @param serverIP 服务IP
     * @param port     端口
     * @param userName 用户名
     * @param password 密码
     * @throws SocketException SocketException
     * @throws IOException     IOException
     * @throws JSchException   JSchException
     */
    public void connectServer(String serverIP, int port, String userName, String password) throws SocketException, IOException, JSchException {
        JSch jsch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession(userName, serverIP, port);
        // 设置密码
        session.setPassword(password);
        // 为Session对象设置properties
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        // 通过Session建立链接
        session.connect();
        // 打开SFTP通道
        channel = (ChannelSftp) session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();

    }

    /**
     * 自动关闭资源
     */
    public void close() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    public List<ChannelSftp.LsEntry> getDirList(String path) throws SftpException {
        List<ChannelSftp.LsEntry> list = new ArrayList<>();
        if (channel != null) {
            Vector vv = channel.ls(path);
            if (vv == null && vv.size() == 0) {
                return list;
            } else {
                Object[] aa = vv.toArray();
                for (int i = 0; i < aa.length; i++) {
                    ChannelSftp.LsEntry temp = (ChannelSftp.LsEntry) aa[i];
                    list.add(temp);

                }
            }
        }
        return list;
    }

    /**
     * 下载文件
     *
     * @param remotePathFile 远程文件
     * @param localPathFile  本地文件[绝对路径]
     * @throws SftpException SftpException
     * @throws IOException   IOException
     */
    public void downloadFile(String remotePathFile, String localPathFile) throws SftpException, IOException {
        try (FileOutputStream os = new FileOutputStream(new File(localPathFile))) {
            if (channel == null)
                throw new IOException("sftp server not login");
            channel.get(remotePathFile, os);
        }
    }
    
    /** 
     * 上传文件到SFTP服务器 
     *  
     * @param remotePath 
     *            sftp服务器路径 
     * @param fileName 
     *            sftp服务器文件名 
     * @param localFile 
     *            本地文件路径和名称字符串 
     * @param closeFlag 
     *            是否要关闭本次SFTP连接: true-关闭, false-不关闭 
     * @param filePathFlag 
     *            是否要创建远程的指定目录: true-创建, false-不创建 
     * @throws IOException 
     */ 
    public boolean uploadFile(String remotePath, String fileName, String localFile,  
            boolean closeFlag, boolean filePathFlag) throws Exception {  
    	if (channel == null)
            throw new IOException("sftp server not login");
        boolean flag = false;  
        InputStream input = null;
        try{
	        input = new FileInputStream(localFile);  
	  
	        // 判断是否要在远程目录上创建对应的目录  
	        if (filePathFlag) {  
	            String[] dirs = remotePath.split("\\/");  
	            if (dirs == null || dirs.length < 1) {  
	                dirs = remotePath.split("\\\\");  
	                }  
	  
	                String now = this.channel.pwd();  
	                for (int i = 0; i < dirs.length; i++) {  
	                    if (dirs[i] != null && !"".equals(dirs[i])) {  
	                    boolean dirExists = this.openDirs(dirs[i]);  
	                    if (!dirExists) {  
	                        this.channel.mkdir(dirs[i]);  
	                        this.channel.cd(dirs[i]);  
	                    }  
	                }  
	            }  
	            this.channel.cd(now);  
	        }  
	          
	        this.channel.cd("/root/"+remotePath);
	        this.channel.put(input, fileName);  
	        flag = true;  
	        return flag;  
    } catch (SftpException e) {  
        LOGGER.error("文件上传失败！", e);  
        throw new RuntimeException("文件上传失败！");  
    } catch (FileNotFoundException e) {  
        LOGGER.error("FileNotFoundException 上传文件找不到！", e);  
        throw new RuntimeException("上传文件路径有误！");  
    } finally {  
        if (input != null) {  
            try {  
                input.close();  
            } catch (Exception localException1) {  
                LOGGER.error("输入流关闭失败", localException1);  
            }  
        }  
        // 判断是否要关闭本次SFTP连接  
        if (closeFlag) {  
        	this.channel.disconnect(); 
        }  
    }  
    }

    /**
     * 上传文件
     *
     * @param remoteFile 远程文件
     * @param localFile
     * @throws SftpException
     * @throws IOException
     */
    public void uploadFile(String remoteFile, String localFile) throws SftpException, IOException {
    	try (FileInputStream in = new FileInputStream(new File(localFile))) {
            if (channel == null)
                throw new IOException("sftp server not login");
            channel.put(in, remoteFile);
        }
    }
    
    /** 
     * 打开指定目录 
     *  
     * @param sftp 
     *            ChannelSftp 
     * @param directory 
     *            directory 
     * @return 是否打开目录 
     */  
    public boolean openDirs(String directory) {  
        try {  
            this.channel.cd(directory);  
            return true;  
        } catch (SftpException e) {  
            return false;  
        }  
    } 

}