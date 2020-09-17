/**
 * 一切的开始
 * 自定义View的4个级别：
 * 1.Canvas的drawXXX()系列方法以及Paint最常见使用
 * 2.Paint的完全攻略--贝塞尔曲线
 * 3.Canvas对绘制的辅助--裁剪范围和几何变换
 * 4.使用不同的绘制方法来控制绘制顺序--控制UI性能 :
 *   控制绘制顺序解决的并不是「做不到」的问题，而是性能问题。同样的一种效果，
 *   你不用绘制顺序的控制往往也能做到，但需要用多个 View 甚至是多层 View 才能拼凑出来，
 *   因此代价是 UI 的性能；而使用绘制顺序的控制的话，一个 View 就全部搞定了
 * ps：https://hencoder.com/ui-1-1/
 */
package com.wn.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class CustomView : View {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.FILL

        mPath.addArc(200f, 200f, 400f, 400f, -225f, 225f);
        mPath.arcTo(400f, 200f, 600f, 400f, -180f, 225f, false);
        mPath.lineTo(400f, 542f);
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.parseColor("#FFCCCC"))
//        canvas.drawCircle(400f, 400f, 180f, mPaint)

        canvas.drawPath(mPath, mPaint);
    }
}