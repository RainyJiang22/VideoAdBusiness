package com.jacky.videosdk.okhttp.listener

/**
 * @author jacky
 * @date 2021/7/20
 * 监听下载进度
 */
interface DisposeDownloadListener : DisposeDataListener {

    fun onProgress(progress: Int)
}