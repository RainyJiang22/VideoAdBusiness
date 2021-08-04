package com.jacky.videosdk.okhttp

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author jacky
 * @date 2021/8/4
 */
object NetWork {

    /**
     * 通过call对象获取json字符
     */
    suspend fun getJson(call: Call) = call.await()

    private suspend fun Call.await() : String {
        return suspendCoroutine { continuation ->
           enqueue(object : Callback {
               override fun onFailure(call: Call, e: IOException) {
                   continuation.resumeWithException(e)
               }

               override fun onResponse(call: Call, response: Response) {
                   val json = response.body().toString()
                   continuation.resume(json)
               }

           })
        }
    }


}