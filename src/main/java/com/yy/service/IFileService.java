package com.yy.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yy on 2018/2/1.
 */
public interface IFileService {
    String upload(MultipartFile file,String path);
}
