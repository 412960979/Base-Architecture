package com.base.koltin.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * dp值转换成像素值
 */
inline val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )

/**
 * sp值转换成像素值
 */
inline val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * in值转换成像素值
 */
inline val Float.IN
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_IN,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * mm值转换成像素值
 */
inline val Float.mm
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_MM,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * pt值转换成像素值
 */
inline val Float.pt
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PT,
        this,
        Resources.getSystem().displayMetrics
    )