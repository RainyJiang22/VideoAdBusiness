package com.example.videobusiness.application

import android.app.Application

/**
 * @author jacky
 * @date 2021/7/10
 * 1.程序入口
 * 2.初始化工作
 * 3.整个应用其他模块进行初始化
 */
class VideoApplication : Application() {

    companion object {
        private var instance: VideoApplication? = null

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}