package com.jacky.videosdk.okhttp.exception

import java.lang.Exception

/**
 * @author jacky
 * @date 2021/7/20
 * 自定义异常
 */
class OkHttpException(
    /**
     * the server return code
     */
    private var ecode: Int,
    /**
     * the server return error message
     */
    private var emsg: Any?
) : Exception() {

    private val serialVersionUID = 1L

    fun getEcode(): Int {
        return ecode
    }

    fun getEmsg(): Any? {
        return emsg
    }
}