package com.jacky.videosdk.imageloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

/**
 * @author jacky
 * @date 2021/7/25
 */
class ImageLoaderTest:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun testApi() {

        /**
         * 获取imageloader的一个实例
         */
        val imageLoader = ImageLoader.getInstance()

        //为我们imageloader配置参数
      //  val imageLoaderConfiguration = ImageLoaderConfiguration.Builder(context).build()


        //为我们显示图片的时候进行一个配置
        val options  = DisplayImageOptions.Builder().build()
        /**
         * 使用displayImage去加载图片
         */
//        imageLoader.displayImage("url",imageView,options,object : SimpleImageLoadingListener(){
//
//        })


     //   ImageLoaderManager.getInstance(this).display()

    }

}