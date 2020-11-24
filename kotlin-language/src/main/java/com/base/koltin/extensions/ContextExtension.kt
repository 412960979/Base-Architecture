package com.base.koltin.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import kotlin.jvm.Throws

/**
 * 显示打开Activity [A], 允许通过 [Intent] 配置跳转参数 [configIntent]
 * return [Unit]
 */
inline fun <reified A : Activity>Context.start(configIntent: Intent.() -> Unit){
    startActivity(Intent(this, A::class.java).apply {
        configIntent()
    })
}

/**
 * 隐式打开Activity,允许通过 [Intent] 配置跳转参数 [configIntent],
 * 如果Activity找不到则抛出异常 [ActivityNotFoundException]
 * @return [Unit]
 */
@Throws(ActivityNotFoundException::class)
inline fun Context.start(action: String, configIntent: Intent.() -> Unit){
    startActivity(Intent(action).apply(configIntent))
}

/**
 * 获取屏幕宽
 * @return [Int]
 */
inline val Context.screenWidth: Int
    get() {
        return resources.displayMetrics.widthPixels
    }

/**
 * 获取屏幕高
 * @return [Int]
 */
inline val Context.screenHeight: Int
    get() {
        return resources.displayMetrics.heightPixels
    }

/**
 * dp转px
 * @return [Int]
 */
inline fun Context.dpToPxInt(dp: Float): Int{
    return (dp.dp + 0.5f).toInt()
}

/**
 * px转dp
 * @return [Float]
 */
inline fun Context.pxToDp(px: Float): Float{
    return px / resources.displayMetrics.density
}

/**
 * px转dp
 * @return [Int]
 */
inline fun Context.pxToDpInt(px: Float): Int{
    return (pxToDp(px) + 0.5f).toInt()
}