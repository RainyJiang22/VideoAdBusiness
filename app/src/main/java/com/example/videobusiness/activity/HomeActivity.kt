package com.example.videobusiness.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.videobusiness.R
import com.example.videobusiness.activity.base.BaseActivity
import com.example.videobusiness.replaceFragment
import com.example.videobusiness.view.fragment.home.HomeFragment
import com.example.videobusiness.view.fragment.home.MessageFragment
import com.example.videobusiness.view.fragment.home.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author jacky
 * @date 2021/7/10
 * 创建首页
 */
class HomeActivity : BaseActivity(), View.OnClickListener {

    companion object {

        val HOME_TAG = "HOME"
        val MESSAGE_TAG = "MESSAGE_TAGSSAGE"
        val MINE_TAG = "MINE"

    }

    private val fragments by lazy { arrayOf(HomeFragment(), MessageFragment(), MineFragment()) }

    override fun getViewId(): Int {
        return R.layout.activity_main
    }

    override fun load() {


        replaceFragment(fragments[0], HOME_TAG)

        bottomBar.setOnItemSelectListener { itemId, view ->
            when(itemId) {
                R.id.tabHome -> {
                    replaceFragment(fragments[0], HOME_TAG)
                }
                R.id.tabMessage -> {
                    replaceFragment(fragments[1], MESSAGE_TAG)
                }
                R.id.tabMine -> {
                    replaceFragment(fragments[2], MINE_TAG)
                }
            }
        }

    }

    override fun onClick(v: View?) {

    }

}