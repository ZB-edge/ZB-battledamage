package cn.edu.bjtu.battledamage.service;

import cn.edu.bjtu.battledamage.entity.FileDescriptor;
import cn.edu.bjtu.battledamage.entity.FileSavingMsg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/7/7
 */
public interface FileService {
    String getThisJarPath();
    List<FileSavingMsg> saveFiles(MultipartFile[] multipartFiles, String path);
    List<FileDescriptor> getFileList(String path, String[] extensions);
    List<FileSavingMsg> sendFiles(String url, String path, String[] names);
    void execJar(String name);
    void killProcessByPort(int port);
}
