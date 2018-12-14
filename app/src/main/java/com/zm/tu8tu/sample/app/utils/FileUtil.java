package com.zm.tu8tu.sample.app.utils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/26
 * @description : 描述...
 */
public class FileUtil {
    public static String getFilePath(String boxImg) {
        return FileDownloadUtils.getDefaultSaveRootPath() + File.separator + boxImg;
    }

}
