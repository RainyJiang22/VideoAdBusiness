package com.example.videobusiness.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author jacky
 * @date 2021/7/10
 * 基类activity
 */
abstract class BaseActivity : AppCompatActivity() {


    /**
     * 输出日志，所需tag
     */
    companion object {
        var TAG: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = componentName.className
        setContentView(getViewId())
        load()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    protected abstract fun getViewId() : Int

    protected abstract fun load()
}