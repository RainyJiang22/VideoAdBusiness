package com.jacky.videosdk.okhttp.listener

/**
 * 自定义事件监听
 * @author jacky
 * @date 2021/7/20
 */
interface DisposeDataListener {


    /**
     * 请求成功回调事件
     */
    fun onSuccess(responseObj: Any)

    /**
     * 请求失败回调事件
     */
    fun onFailure(reasonObj: Any)
}