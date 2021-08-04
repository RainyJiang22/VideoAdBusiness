package com.example.videobusiness.widget

import com.example.videobusiness.dp
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children

/**
 * @author jacky
 * @date 2021/7/10
 * 自定义BottomNavigationBar 仿美团外卖底部导航栏
 *
 * 绘制一个圆滑线条滑动视图FrameLayout
 */
class BottomNavigationBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mInterpolator = LinearInterpolator()
    private val mDuration = 500L
    private var animator: ValueAnimator? = null
    //当前位置
    private var mCheckPosition = 0
    //底部自定义BottomView放入arrayList中add进来
    private var checkGroup = arrayListOf<BottomItemView>()
    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val topCircle = 10f.dp

    //边界圆滑线条
    private val lineCircle = 6f.dp

    private val backGroundPath = Path()

    private val circlePath = Path()

    private val circleRadius = 23f.dp

    private var itemWidth = 0

    private var offsetX = 0f

    private var layoutOnce = false

    init {
        setWillNotDraw(false)
    }


    //绘制
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        backGroundPath.addRect(
            0f,
            topCircle,
            measuredWidth.toFloat(),
            measuredHeight.toFloat(),
            Path.Direction.CCW
        )
        canvas.drawPath(backGroundPath, paint)

        circlePath.reset()
        circlePath.moveTo(getItemLeft() - lineCircle, topCircle)
        circlePath.cubicTo(
            getItemLeft(),
            topCircle,
            getItemLeft() + circleRadius,
            -topCircle,
            getItemLeft() + circleRadius * 2,
            topCircle
        )
        canvas.drawPath(circlePath, paint)

        canvas.save()
        circlePath.reset()
        circlePath.moveTo(
            getItemLeft() + circleRadius * 2 + lineCircle,
            topCircle
        )
        circlePath.cubicTo(
            getItemLeft() + circleRadius * 2,
            topCircle,
            getItemLeft() + circleRadius,
            -topCircle,
            getItemLeft(),
            topCircle
        )

        canvas.restore()
        canvas.drawPath(circlePath, paint)
    }


    private fun getItemLeft() = offsetX

    private fun getItemMiddle(position: Int) = itemWidth * position + itemWidth / 2f

    private fun getFirstMiddle() = getItemMiddle(mCheckPosition)

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (view in children) {
            if (view is BottomItemView) {
                checkGroup.add(view)
            }
        }

        //循环选择底部导航进行圆弧动画移动
        for (i in 0 until checkGroup.size) {
            if (checkGroup[i].isChecked()) {
                mCheckPosition = i
            }
            checkGroup[i].setOnClickListener {
                if (i != mCheckPosition) {
                    val lastPosition = mCheckPosition
                    mCheckPosition = i
                    animatorToPosition(lastPosition, i)
                }
                for (index in 0 until checkGroup.size) {
                    val boolean = index == mCheckPosition
                    if(boolean){
                        val view = checkGroup[index]
                        onItemSelect?.invoke(view.id,view)
                    }
                    checkGroup[index].setCheck(boolean)
                }
            }
        }
    }

    private var onItemSelect: ((itemId: Int, view: View) -> Unit)? = null


    //点击设置
    fun setOnItemSelectListener(onItemSelect: ((itemId: Int, view: View) -> Unit)){
        this.onItemSelect = onItemSelect
    }



    //滑动属性平移动画
    private fun animatorToPosition(startPosition: Int, endPosition: Int) {
        if (animator != null) {
            animator?.pause()
            animator?.cancel()
            animator?.removeAllListeners()
            animator = null
        }
        val offsetStart = getItemMiddle(startPosition) - circleRadius
        val offsetEnd = getItemMiddle(endPosition) - circleRadius

        animator = ValueAnimator.ofFloat(offsetStart, offsetEnd)
        animator?.apply {
            duration = mDuration
            interpolator = mInterpolator
            addUpdateListener {
                val value = it.animatedValue as Float
                offsetX = value
                postInvalidate()
            }
            start()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if(layoutOnce.not()){
            layoutOnce = true

            itemWidth = (measuredWidthAndState / childCount.toFloat()).toInt()
            var start = 0
            for (view in children) {
                view.layout(start, 0, start + itemWidth, measuredHeight)
                start += itemWidth
            }

            offsetX = getFirstMiddle() - circleRadius
        }
    }


}