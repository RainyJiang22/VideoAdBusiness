package com.jacky.videosdk.okhttp

import android.util.Log
import androidx.lifecycle.liveData
import com.alibaba.fastjson.JSON
import com.jacky.videosdk.okhttp.NetWork.getJson
import kotlinx.coroutines.Dispatchers
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author jacky
 * @date 2021/8/4
 */
object OkHttpRepository {

    /**
     * 发起get请求
     * @param url String 请求url
     * @param headers Map<String, Any>? 请求头键值对
     * @param params Map<String, Any>? 参数键值对
     * @return LiveData<Result<String>>
     */
    fun getRequest(
        url: String,
        headers: Map<String, Any>?,
        params: Map<String, Any>?
    ) = liveData(Dispatchers.IO) {
        val result = try {
            val client = OkHttpClient()
            //参数键值对
            val query = params?.entries?.joinToString("&") {
                "${it.key}=${it.value}"
            }
            val getUrl = "$url?$query"
            //请求Builder
            val requestBuilder = Request.Builder()
            //添加请求头
            headers?.forEach {
                requestBuilder.addHeader(it.key, it.value.toString())
            }
            val request = requestBuilder.url(getUrl).build()
            val call = client.newCall(request)
            //通过suspend函数获取Json字符串
            val json = getJson(call)
            Result.success(json)
        } catch (e: Exception) {
            Log.d("EasyOkHttp", "Repository getRequest failed")
            Result.failure(e)
        }
        emit(result)
    }


    /**
     * POST
     * @param url String 请求url
     * @param headers Map<String,Any>? 请求头
     * @param fromFormBody FormBody? 请求体
     * @param LiveData<Result<String?>>
     */
    fun postRequest(
        url: String,
        headers: Map<String, Any>?,
        fromFormBody: FormBody?,
    ) = liveData(Dispatchers.IO) {
        val result = try {
            val client = OkHttpClient()
            val requestBuilder = Request.Builder()
            headers?.forEach {
                requestBuilder.addHeader(it.key, it.value.toString())
            }
            val request = fromFormBody?.let {
                requestBuilder.url(url).post(it).build()
            }
            val call = request?.let { client.newCall(it) }
            //通过suspend函数获取Json字符串
            val json = call?.let { getJson(it) }

            Result.success(json)
        } catch (e: Exception) {
            Log.d("EasyOkHttp", "Repository postRequest failed")
            Result.failure(e)
        }
        emit(result)
    }

}