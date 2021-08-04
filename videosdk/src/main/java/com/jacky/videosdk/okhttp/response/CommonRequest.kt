package com.jacky.videosdk.okhttp.response

import okhttp3.FormBody
import okhttp3.Request
import java.lang.StringBuilder

/**
 * @author jacky
 * @date 2021/7/17
 * @function 接受请求参数，为我们生成Request对象
 */
object CommonRequest {


    /**
     * @param url
     * @param params
     * @return 返回一个创建好的Request对象
     */
    fun createPostRequest(url: String, params: RequestParams?): Request {
        val mFormBodyBuilder = FormBody.Builder()
        params?.urlParams?.forEach { (key, value) ->
            //将请求参数遍历添加到请求构建类中
            mFormBodyBuilder.add(key, value)
        }

        //通过请求构建类的build方法获取真正的请求体
        val mFormBody = mFormBodyBuilder.build();

        return Request.Builder().url(url).post(mFormBody).build()
    }


    /**
     * @param url
     * @param params
     * @return 通过传入的参数返回一个Get类型请求
     */
    fun createGetRequest(url: String, params: RequestParams?): Request {
        val urlBuilder = StringBuilder(url).append("?")

        params?.urlParams?.forEach { (key, value) ->
            //将请求参数遍历添加到请求构建类中
            urlBuilder.append(key).append("=")
                .append(value).append("&")
        }
        return Request.Builder().url(urlBuilder.substring(0, (urlBuilder.length - 1))).get().build()
    }
}