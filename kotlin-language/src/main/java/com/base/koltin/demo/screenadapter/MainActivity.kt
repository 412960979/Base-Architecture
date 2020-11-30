package com.base.koltin.demo.screenadapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.base.koltin.R
import com.base.koltin.extensions.adapterScreen
import com.base.koltin.extensions.resetScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* 按照1080*2220px设计UI ---->> 440dpi  density=2.75  392.7*807.2dp
        *  测试分辨率：1080*2220px,440dpi;  1080*2160px,400dpi */
//        adapterScreen(392.7f, false)// 按照宽度来适配
        adapterScreen(807.2f, true)// 按照高度来适配

        setContentView(R.layout.activity_main)

        //沉浸状态栏
        setStatusBar()
        //状态栏字体颜色默认都给它显示为“灰色”
        setAndroidNativeLightStatusBar(true)
//        2f.pow(10)

        rl_center.setOnClickListener {
            Toast.makeText(this, "test toast 测试", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        resetScreen()
    }

    /**
     * 沉浸状态栏
     */
    private fun setStatusBar() {
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT //也可以设置成灰色透明的，比较符合Material Design的风格
    }

    /**
     * 设置状态栏的字体颜色是否高亮
     * @param dark true:会显示灰色 : false :会显示白色（亮色）
     */
    fun setAndroidNativeLightStatusBar(dark: Boolean) {
        val decor = window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}
