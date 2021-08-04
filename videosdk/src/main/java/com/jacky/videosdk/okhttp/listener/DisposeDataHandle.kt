package com.jacky.videosdk.okhttp.listener

/**
 * @author jacky
 * @date 2021/7/20
 */
class DisposeDataHandle {
    var mListener: DisposeDataListener? = null
    var mClass: Class<*>? = null

    constructor(listener: DisposeDataListener) {
        this.mListener = listener
    }

    constructor(mListener: DisposeDataListener, mClass: Class<*>?) {
        this.mListener = mListener
        this.mClass = mClass
    }
}