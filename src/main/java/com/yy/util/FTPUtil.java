package com.yy.util;


import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.ftp.impl.FtpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
public class FTPUtil {
    private static final Logger logger= LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp=PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser=PropertiesUtil.getProperty("ftp.user");
    private static String ftpPassword=PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String password;
    private FTPClient ftpClient;


    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil=new FTPUtil(FTPUtil.ftpIp,21,FTPUtil.ftpUser,FTPUtil.ftpPassword);
        boolean result = false;
        logger.info("开始上传到FTP服务器");
        result=ftpUtil.uploadFile("image",fileList);
        logger.info("结束上传，上传结果为{}",result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded=true;
        FileInputStream fis=null;
        //连接Ftp服务器
        if(!connectServer(this.getIp(),this.port,this.getUser(),this.getPassword())){
            return false;
        }
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for (File fileItem:fileList) {
                fis=new FileInputStream(fileItem);
                ftpClient.storeFile(fileItem.getName(),fis);
            }
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            uploaded=false;
            e.printStackTrace();
        }finally {
            fis.close();
            ftpClient.disconnect();
        }
        return uploaded;


    }

    private boolean connectServer(String ip,int port,String user,String password){
        boolean isSucess=false;
        ftpClient=new FTPClient();
        try {
            ftpClient.connect(ip);
            isSucess= ftpClient.login(user,password);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常");
            e.printStackTrace();
        }
        return isSucess;
    }

  public FTPUtil(String ip, int port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public FTPUtil setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FTPUtil setPassword(String password) {
        this.password = password;
        return this;
    }
}
