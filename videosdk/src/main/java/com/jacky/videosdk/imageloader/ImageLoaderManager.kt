package com.jacky.videosdk.imageloader

import android.content.Context
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.jacky.videosdk.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.download.BaseImageDownloader

/**
 * @author jacky
 * @date 2021/7/25
 */
class ImageLoaderManager private constructor(context: Context) {

    /**
     * 实现我们默认的options
     *
     * @return
     */
    private val defaultOptions: DisplayImageOptions
        get() = DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.xadsdk_img_error) //图片地址为空的时候
            .showImageOnFail(R.drawable.xadsdk_img_error) //图片加载失败
            .cacheInMemory(true) //缓存在内存中
            .cacheOnDisk(true) // 缓存在硬盘中
            .bitmapConfig(Bitmap.Config.RGB_565) //使用的解码类型
            .decodingOptions(BitmapFactory.Options()) //图片解码配置
            .build()

    /**
     * 加载图片API
     *
     * @param imageView
     * @param url
     * @param options
     * @param loadingListener
     */
    fun displayImage(
        imageView: ImageView?, url: String?,
        options: DisplayImageOptions?,
        loadingListener: ImageLoadingListener?
    ) {
        mImageLoader?.displayImage(url, imageView, options, loadingListener)
    }

    fun display(imageView: ImageView?, url: String?, listener: ImageLoadingListener?) {
        displayImage(imageView, url, null, listener)
    }

    fun display(imageView: ImageView?, url: String?) {
        displayImage(imageView, url, null, null)
    }

    companion object {
        private const val THREAD_COUNT = 4 //标明我们UIL最多可以有多少条线程
        private const val PROPRITY = 2 //标明我们图片加载的一个优先级
        private const val DISK_CHCHE_SIZE = 50 * 1024 //标明UIL最多有多少缓存多少图片
        private const val CONNECTION_TIME_OUT = 5 * 1000 //连接超时时间
        private const val READ_TIME_OUT = 30 * 1000 //读取的超时时间
        private val mImageLoader: ImageLoader? = null
        private var mInstance: ImageLoaderManager? = null

        //双重校验
        fun getInstance(context: Context): ImageLoaderManager? {
            if (mInstance == null) {
                synchronized(ImageLoaderManager::class.java) {
                    if (mInstance == null) {
                        mInstance = ImageLoaderManager(context)
                    }
                }
            }
            return mInstance
        }
    }

    /**
     * 单例模式的私有构造方法
     *
     * @param context context
     */
    init {
        val configuration = ImageLoaderConfiguration.Builder(context)
            .threadPoolSize(THREAD_COUNT) //配置图片中最大的数量
            .threadPriority(Thread.NORM_PRIORITY - PROPRITY)
            .denyCacheImageMultipleSizesInMemory() //防止多套尺寸的图片存到我们的内存中
            .memoryCache(WeakMemoryCache()) //使用内存缓存
            .diskCacheSize(DISK_CHCHE_SIZE) //分配硬盘缓存大小
            .diskCacheFileNameGenerator(Md5FileNameGenerator()) //使用MD5命名文件
            .tasksProcessingOrder(QueueProcessingType.LIFO) //图片下载顺序
            .defaultDisplayImageOptions(defaultOptions) //图片加载options
            .imageDownloader(
                BaseImageDownloader(
                    context,
                    CONNECTION_TIME_OUT,
                    READ_TIME_OUT
                )
            ) //设置图片下载器
            .writeDebugLogs() //debug环境下输出日志
            .build()
    }
}