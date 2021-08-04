package com.jacky.videosdk.okhttp.listener

/**
 * @author jacky
 * @date 2021/7/20
 *
 * @function 专门处理cookie时候创建此接口
 */
interface DisposeHandleCookieListener : DisposeDataListener {

    fun onCookie(cookieStrLists: ArrayList<String>)
}