package com.jacky.videosdk.okhttp

import com.jacky.videosdk.okhttp.https.HttpsUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

/**
 * @author jacky
 * @date 2021/7/17
 * @function 请求的发送，请求参数的配置，https支持
 */
object CommonOkHttpClient {

    //超时参数
    private const val TIME_OUT = 30
    private var mOkHttpClient: OkHttpClient? = null


    init {
        //创建我们client对象的构建者
        val okHttpBuilder = OkHttpClient.Builder()

        //为构建者填充超时时间
        okHttpBuilder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)


        //允许请求重定向
        okHttpBuilder.followRedirects(true)

        //https支持
        okHttpBuilder.hostnameVerifier { hostname, session -> true }

        @Suppress("DEPRECATION")
        okHttpBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory())


        //生成我们的client对象
        mOkHttpClient = okHttpBuilder.build()
    }


    /**
     * 发送具体的http/https请求
     * @param request
     * @param commonCallback
     * @return Call
     */
    fun sendRequest(request: Request, commonCallback: Callback): Call {
        val call = mOkHttpClient?.newCall(request)
        call?.enqueue(commonCallback)

        return call!!
    }

}