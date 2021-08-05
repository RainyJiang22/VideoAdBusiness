package com.jacky.videosdk.okhttp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jacky.videosdk.R
import com.jacky.videosdk.okhttp.listener.DisposeDataHandle
import com.jacky.videosdk.okhttp.listener.DisposeDataListener
import com.jacky.videosdk.okhttp.response.CommonJsonCallBack
import com.jacky.videosdk.okhttp.response.CommonRequest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * @author jacky
 * @date 2021/7/20
 */
class BaseOkHttpTest : AppCompatActivity() {

    companion object {
        const val TAG = "CommonRequest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }


    private fun test() {
        CommonOkHttpClient.sendRequest(CommonRequest.createGetRequest(
            "https://wanandroid.com/article/listproject/0/json",
            null
        ), CommonJsonCallBack(DisposeDataHandle(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any) {

                Log.e(TAG, "onSuccess: $responseObj")
            }

            override fun onFailure(reasonObj: Any) {

                Log.e(TAG, "onFailure: $reasonObj")
            }

        })))
    }


    private fun testApi() {
        OkHttpRepository.getRequest("https://wanandroid.com/article/listproject/0/json",
        null,null)
    }
}