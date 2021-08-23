package com.example.videobusiness.activity

import com.example.videobusiness.R
import com.example.videobusiness.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author jacky
 * @date 2021/8/23
 */
class TestActivity : BaseActivity() {
    override fun getViewId(): Int {
        return R.layout.activity_test
    }

    override fun load() {

        text_button.setOnClickListener {
            txt_test.text = "Robolectric Rocks!"
        }
    }
}