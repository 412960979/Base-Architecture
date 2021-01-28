package com.base.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RecyclerView
    private lateinit var a: LinearLayout
//    lateinit var textView: TextView
//    lateinit var linear: LinearLayout
    lateinit var relative: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        customView.setOnClickListener {
//            customView.end()
//            customView.start()
//
//            endAnim()
//        }
//
//        endAnim()

//         属性动画
//        customView.animate().translationX(500f)
//        customView.translationX = 500f // 不是属性动画

        thread {  }
    }

    private fun endAnim() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            customView.end()
//            customView.setDegree(0f)
//        }, 10_000)
    }
}