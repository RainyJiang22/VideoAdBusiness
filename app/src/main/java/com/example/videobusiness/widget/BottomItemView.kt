package com.example.videobusiness.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.addListener
import com.example.videobusiness.R
import com.example.videobusiness.dp

/**
 * @author jacky
 * @date 2021/7/10
 */
class BottomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private var checkIcon: Drawable? = null
    private var unCheckIcon: Drawable? = null
    private var text = ""
    private var isCheck = false
    private var textColor = Color.parseColor("#2A2A2A")
    private val image by lazy { AppCompatImageView(context) }
    private val textView by lazy { AppCompatTextView(context) }
    private val interpolator = LinearInterpolator()
    private val duration = 350L
    private var scaleMax = 1.2f
    private var scaleMin = 1.0f

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL
        initAttrs(context, attrs)
        initView()
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.BottomItemView)
        checkIcon = array.getDrawable(R.styleable.BottomItemView_checkedIcon)
        unCheckIcon = array.getDrawable(R.styleable.BottomItemView_uncheckedIcon)
        text = array.getString(R.styleable.BottomItemView_buttonText) ?: ""
        isCheck = array.getBoolean(R.styleable.BottomItemView_buttonChecked, false)
        scaleMax = array.getFloat(R.styleable.BottomItemView_iconScaleMax,scaleMax)
        scaleMin = array.getFloat(R.styleable.BottomItemView_iconScaleMin,scaleMin)
        array.recycle()
    }

    private fun initView() {
        textView.text = text
        textView.textSize = 10f
        textView.setTextColor(textColor)

        if (isCheck) {
            image.scaleX = scaleMax
            image.scaleY = scaleMax
        } else {
            image.scaleX = scaleMin
            image.scaleY = scaleMin
        }

        toggle()

        addViewInLayout(image, 0, LayoutParams(35f.dp.toInt(), 35f.dp.toInt()).apply {
            topMargin = 3f.dp.toInt()
        }, false)
        addViewInLayout(
            textView,
            1,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                //topMargin = 3f.dp.toInt()
            },
            false
        )

    }

    private fun toggleDrawable() {
        if (isCheck) {
            image.setImageDrawable(checkIcon)
        } else {
            image.setImageDrawable(unCheckIcon)
        }
    }

    private fun toggleTextView() {
        if (isCheck) {
            textView.alpha = 1f
        } else {
            textView.alpha = 0.6f
        }
    }

    private fun toggle() {
        toggleDrawable()
        toggleTextView()
    }

    private var switchAnimator: AnimatorSet? = null
    private fun toggleAnimator() {
        val scaleStart = if (isCheck) {
            scaleMin
        } else {
            scaleMax
        }
        val scaleEnd = if (isCheck) {
            scaleMax
        } else {
            scaleMin
        }

        if(switchAnimator!=null){
            switchAnimator?.pause()
            switchAnimator?.cancel()
            switchAnimator?.removeAllListeners()
            switchAnimator = null
        }

        switchAnimator = AnimatorSet()

        val alphaAnimatorDown = ObjectAnimator.ofFloat(image, "alpha", 1.0f, 0.7f)
        alphaAnimatorDown?.addListener(onEnd = {
            toggleDrawable()
        })
        val alphaAnimatorUp = ObjectAnimator.ofFloat(image, "alpha", 0.7f, 1.0f)
        val animatorAlphaSet = AnimatorSet()
        animatorAlphaSet.playSequentially(alphaAnimatorDown, alphaAnimatorUp)
        val scaleAnimatorX = ObjectAnimator.ofFloat(image, "scaleX", scaleStart, scaleEnd)
        val scaleAnimatorY = ObjectAnimator.ofFloat(image, "scaleY", scaleStart, scaleEnd)
        switchAnimator?.apply {
            interpolator = this@BottomItemView.interpolator
            duration = this@BottomItemView.duration
            playTogether(animatorAlphaSet, scaleAnimatorX, scaleAnimatorY)
            start()
        }


        val alpha = if (isCheck) {
            1f
        } else {
            0.6f
        }
        textView.animate().alpha(alpha)
            .setDuration(duration)
            .setInterpolator(interpolator)
            .start()
    }

    fun setCheck(boolean: Boolean) {
        if (isCheck != boolean) {
            isCheck = boolean
            toggleAnimator()
        }
    }

    fun isChecked() = isCheck


}