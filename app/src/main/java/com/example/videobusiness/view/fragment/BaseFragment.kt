package com.example.videobusiness.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

/**
 * @author jacky
 * @date 2021/7/10
 * 基类fragment
 */
abstract class BaseFragment : Fragment() {

    protected var mContext: Activity? = null

    private var rootView: View? = null


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = setContentView(inflater,container, getViewId())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (rootView != null) {
            load()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    private fun setContentView(inflater: LayoutInflater, container: ViewGroup?, resId: Int): View? {
        if (rootView == null) {
            rootView = inflater.inflate(resId, container, false)
        } else {
            container?.removeView(rootView)
        }
        mContext = requireActivity()
        return rootView
    }

    protected abstract fun getViewId() : Int

    protected abstract fun load()

}