package com.base.customview.neteasecloudmusic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.base.customview.R
import kotlin.math.abs
import kotlin.math.atan2


class HeadView : View {
    private lateinit var mOvalPaint: Paint
    private lateinit var startPoint: Point
    private lateinit var endPoint: Point
    private lateinit var controlPoint: Point
    private var centerX: Int = 0
    private var centerY: Int = 0
    private lateinit var mPath: Path
    private lateinit var mBitmap: Bitmap
    private lateinit var pathMeasure: PathMeasure
    private var currentValue = 0.0
    private var pos = FloatArray(2)
    private var tan = FloatArray(2)
    private lateinit var mMatrix: Matrix
    private var isSetPath = false

//    private var mTouchSlop = 0

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
        mOvalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOvalPaint.apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        startPoint = Point()
        endPoint = Point()
        controlPoint = Point()

        mPath = Path()
        mMatrix = Matrix()
        val options = BitmapFactory.Options()
        options.inSampleSize = 2 // 缩放图片
        mBitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_home_address, options)
        pathMeasure = PathMeasure()

//        val vc = ViewConfiguration.get(context)
//        mTouchSlop = vc.scaledTouchSlop
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // view大小改变之后获取view的宽高
        centerX = w / 2
        centerY = h / 2

        // 初始化控制点
        startPoint.x = 300
        startPoint.y = h+200
        endPoint.x = w
        endPoint.y = h - 600
        controlPoint.x = centerX
        controlPoint.y = h / 2 + 400
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setLayerType(LAYER_TYPE_HARDWARE, null)

        mPath.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())
        mPath.quadTo(
            controlPoint.x.toFloat(),
            controlPoint.y.toFloat(),
            endPoint.x.toFloat(),
            endPoint.y.toFloat()
        )
        canvas.drawPath(mPath, mOvalPaint)

        if (!isSetPath) {
            pathMeasure.setPath(mPath, false)
            isSetPath = true
        }

        if (isReverse) {
            currentValue += dx / pathMeasure.length
        } else {
            currentValue -= dx / pathMeasure.length
        }
//        if (currentValue >= 1f) {
//            currentValue = 0.0
//        }
        pathMeasure.getPosTan(pathMeasure.length * currentValue.toFloat(), pos, tan)

        mMatrix.reset()
        val degrees = atan2(tan[1], tan[0]) * 180.0 / Math.PI
        mMatrix.postRotate(degrees.toFloat(), mBitmap.width / 2f, mBitmap.height / 2f)
        mMatrix.postTranslate(pos[0] - mBitmap.width / 2, pos[1] - mBitmap.height / 2)
        canvas.drawBitmap(mBitmap, mMatrix, mOvalPaint)
    }

    private var isReverse = true
    var startX = 0f
    var dx = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                val nowX = event.x

                dx = abs(nowX - startX)
                isReverse = nowX > startX
                invalidate()
                startX = nowX
            }
        }

        return true
    }
}