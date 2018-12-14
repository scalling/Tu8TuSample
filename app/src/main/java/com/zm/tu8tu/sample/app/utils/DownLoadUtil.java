package com.zm.tu8tu.sample.app.utils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/26
 * @description : 下载
 */
public class DownLoadUtil {

    /**
     * 多任务下载
     *
     * @param imageHttpUrl     图片服务器地址
     * @param urls             文件下载多个网络地址
     * @param downloadListener 下载监听
     */
    public static void downMultit(String imageHttpUrl, Map<String, String> urls, FileDownloadListener downloadListener) {
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);
        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for (Map.Entry<String, String> entry : urls.entrySet()) {
            tasks.add(FileDownloader.getImpl().create(imageHttpUrl + entry.getKey()).setPath(entry.getValue()));
        }
        queueSet.disableCallbackProgressTimes(); // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadListener#progress`, 我们只关系每个任务是否完成, 所以这里这样设置可以很有效的减少ipc.

// 所有任务在下载失败的时候都自动重试一次
        queueSet.setAutoRetryTimes(1);
        queueSet.downloadTogether(tasks);

// 最后你需要主动调用start方法来启动该Queue
        queueSet.start();

// 串行任务动态管理也可以使用FileDownloadSerialQueue。
    }


    /**
     * 单任务下载
     *
     * @param imageHttpUrl     图片服务器地址
     * @param boxImg           文件下载网络地址
     * @param filePath         本地地址
     * @param downloadListener
     */
    public static void downSingle(String imageHttpUrl, String boxImg, String filePath, FileDownloadListener downloadListener) {
        FileDownloader.getImpl().create(imageHttpUrl + boxImg)
                .setPath(filePath)
                .setListener(downloadListener).start();
    }
}
