package com.base.koltin.extensions

import android.app.Activity
import android.content.res.Resources

/**
 * 屏幕适配
 *
 * 规则：宽高哪个维度重要就按照哪个维度来设计。 例如设计图上的一个页面，一屏幕高显示不完，是可以滑动的列表，那么就优先
 * 按照宽度来进行适配，保证再页面布局宽度上和设计图保持一致。  如果设计图要求一屏幕高显示完所有内用， 那么就优先按照
 * 高度来进行适配， 保证在页面布局高度上和设计图保持一致，
 *
 * 使用时需要注意以下几点：
 * 1.尽量只在当前页面生效，包括activity，fragment，dialog，view，需要在setContentView()或者inflate之前调用这个方法，
 * 在结束onDestroy,onDismiss,onDetachWindow()的时候调用resetScreen()方法。
 * 2.在页面中需要弹出toast和dialog的时候需要调用resetScreen，不然toast和dialog的页面大小和字体大小会被影响。
 * @param targetDP 目标维度(宽，高)的dp值
 * @param isVertical 是按照宽来适配，还是按照高来适配
 */
inline fun Activity.adapterScreen(targetDP: Float, isVertical: Boolean = false){
    // 系统的屏幕尺寸
    val systemDM = Resources.getSystem().displayMetrics
    // app整体的屏幕尺寸
    val appDM = application.resources.displayMetrics
    // activity的屏幕尺寸
    val activityDM = resources.displayMetrics

    // 修改系统默认密度
    if (isVertical){
        // 按照屏幕高来适配
        activityDM.density = activityDM.heightPixels / targetDP
    } else {
        // 按照屏幕宽来适配
        activityDM.density= activityDM.widthPixels / targetDP
    }

    // 适配相应比例的字体大小
    activityDM.scaledDensity = activityDM.density * (systemDM.scaledDensity / systemDM.density)
    // 适配dpi
    activityDM.densityDpi = (160 * activityDM.density).toInt()
}

/**
 * 重置系统默认的密度值
 */
inline fun Activity.resetScreen(){
    // 系统的屏幕尺寸
    val systemDM = Resources.getSystem().displayMetrics
    // app整体的屏幕尺寸
    val appDM = application.resources.displayMetrics
    //activity的屏幕尺寸
    val activityDM = resources.displayMetrics

    activityDM.density = systemDM.density
    activityDM.scaledDensity = systemDM.scaledDensity
    activityDM.densityDpi = systemDM.densityDpi

    appDM.density = systemDM.density
    appDM.scaledDensity = systemDM.scaledDensity
    appDM.densityDpi = systemDM.densityDpi
}

