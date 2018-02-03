package com.yy.service.impl;

import com.google.common.collect.Lists;
import com.yy.service.IFileService;
import com.yy.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by yy on 2018/2/1.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID() + "." + fileExtensionName;
        logger.info("开始上传文件，文件名为：{}，上传的路径为：{}，新文件名为：{}", fileName, path, uploadFileName);
        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();

        }
        File targetFile=new File(path,uploadFileName);
        try {
            //文件上传到Tomcat容器
            file.transferTo(targetFile);
            //上传文件到FTP服务器
            if(!FTPUtil.uploadFile(Lists.newArrayList(targetFile))){
                return null;
            }
            //上传完成后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
