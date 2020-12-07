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
package com.base.customview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class CustomView : View {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()

    private lateinit var launchBitmap: Bitmap
    private lateinit var camera: Camera
    private var degree = 0f
    @SuppressLint("ObjectAnimatorBinding")
    private var animator = ObjectAnimator.ofFloat(this, "degree", 0f, 180f)

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

        launchBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launch_test)
        camera = Camera()

        animator.duration = 2500
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
    }

    fun start() {
        animator.start()
    }

    fun end() {
        animator.end()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.end()
    }

    fun setDegree(degree: Float) {
        this.degree = degree
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.parseColor("#FFCCCC"))
//        canvas.drawCircle(400f, 400f, 180f, mPaint)
        canvas.drawPath(mPath, mPaint)

//        canvas.save()
        // 这里有个顺序，先旋转再平移
        /*canvas.rotate(-30f)
        canvas.translate(-200f, -100f)*/

//        camera.save()
//         camera的旋转是以原点（左上角）为轴心的
//        camera.rotateX(-45f)
//        camera.rotateY(-30f)
//        canvas.translate(width / 2.0f, height / 2.0f)
//        camera.applyToCanvas(canvas)
//        canvas.translate(-width / 2.0f, -height / 2.0f)
//        camera.restore()
//
//        canvas.drawBitmap(launchBitmap, width / 2.0f - 161, height / 2.0f - 116, null)
//        canvas.restore()

        val bitmapWidth: Int = launchBitmap.width
        val bitmapHeight: Int = launchBitmap.height
        val centerX = width / 2
        val centerY = height / 2
        val x = centerX - bitmapWidth / 2.0f
        val y = centerY - bitmapHeight / 2.0f

        // 第一遍绘制：上半部分

        // 第一遍绘制：上半部分
        canvas.save()
        canvas.clipRect(0, 0, width, centerY)
        canvas.drawBitmap(launchBitmap, x, y, mPaint)
        canvas.restore()

        // 第二遍绘制：下半部分

        // 第二遍绘制：下半部分
        canvas.save()

        if (degree < 90) {
            canvas.clipRect(0, centerY, width, height)
        } else {
            canvas.clipRect(0, 0, width, centerY)
        }
        camera.save()
        camera.rotateX(degree)
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        camera.applyToCanvas(canvas)
        canvas.translate((-centerX).toFloat(), (-centerY).toFloat())
        camera.restore()

        canvas.drawBitmap(launchBitmap, x, y, mPaint)
        canvas.restore()
    }
}