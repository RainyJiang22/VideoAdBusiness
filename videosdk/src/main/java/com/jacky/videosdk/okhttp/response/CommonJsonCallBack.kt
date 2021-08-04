package com.jacky.videosdk.okhttp.response

import android.os.Handler
import android.os.Looper
import com.alibaba.fastjson.JSON
import com.jacky.videosdk.adutil.ResponseEntityToModule
import com.jacky.videosdk.okhttp.exception.OkHttpException
import com.jacky.videosdk.okhttp.listener.DisposeDataHandle
import com.jacky.videosdk.okhttp.listener.DisposeDataListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.Response
import java.io.IOException
import org.json.JSONObject
import com.jacky.videosdk.okhttp.listener.DisposeHandleCookieListener


/**
 * @author jacky
 * @date 2021/7/20
 */
class CommonJsonCallBack : Callback {

    /**
     * the logic layer exception, may alter in different app
     */
    protected val RESULT_CODE = "ecode" // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误

    protected val RESULT_CODE_VALUE = 0
    protected val ERROR_MSG = "emsg"
    protected val EMPTY_MSG = ""
    protected val COOKIE_STORE = "Set-Cookie" // decide the server it

    // can has the value of
    // set-cookie2
    /**
     * the java layer exception, do not same to the logic error
     */
    protected val NETWORK_ERROR = -1 // the network relative error

    protected val JSON_ERROR = -2 // the JSON relative error

    protected val OTHER_ERROR = -3 // the unknow error


    /**
     * 将其它线程的数据转发到UI线程
     */
    private var mDeliveryHandler: Handler? = null
    private var mListener: DisposeDataListener? = null
    private var mClass: Class<*>? = null


    constructor(handle: DisposeDataHandle) {
        this.mListener = handle.mListener
        this.mClass = handle.mClass
        this.mDeliveryHandler = Handler(Looper.getMainLooper())
    }

    override fun onFailure(call: Call, e: IOException) {

        /**
         * 此时还在非UI线程，因此要转发
         */
        mDeliveryHandler?.post {
            mListener?.onFailure(OkHttpException(NETWORK_ERROR, e))
        }
    }


    //真正的响应处理函数
    override fun onResponse(call: Call, response: Response) {
        val result = response.body()!!.string()

        mDeliveryHandler?.post {
            handleResponse(result)
        }
    }


    /**
     * 处理服务器返回的响应
     */
    private fun handleResponse(responseObj: Any) {

        //为了保证代码的健壮性
        if (responseObj == null && responseObj.toString().trim() == "") {
            mListener?.onFailure(OkHttpException(NETWORK_ERROR, EMPTY_MSG))
            return
        }

        /**
         * 协议确定好看这里如何修改
         */
        try {
            val result = JSONObject(responseObj.toString())
            if (result.has(RESULT_CODE)) {
                //从json对象中取出我们的响应码，若为0则为正确的响应
                if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null) {
                        mListener?.onSuccess(result)
                    } else {
                        //需要我们将json对象转化为实体对象
                        val obj = ResponseEntityToModule.parseJsonObjectToModule(
                            result, mClass!!
                        )
                        //表明正确的转为实体对象
                        if (obj != null) {
                            mListener?.onSuccess(obj)
                        } else {
                            //返回的不是合法的json
                            mListener?.onFailure(OkHttpException(JSON_ERROR, EMPTY_MSG))
                        }
                    }
                }
            } else {
                //将服务器返回给我们的异常，回调到应用层去处理
                mListener?.onFailure(OkHttpException(OTHER_ERROR,result.get(RESULT_CODE)))
            }

        } catch (e: Exception) {
            mListener?.onFailure(OkHttpException(OTHER_ERROR, e.message))
            e.printStackTrace()
        }

    }


}