package com.base.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var a: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customView.setOnClickListener {
            customView.end()
            customView.start()

            endAnim()
        }

        endAnim()

    }

    private fun endAnim() {
        Handler(Looper.getMainLooper()).postDelayed({
            customView.end()
            customView.setDegree(0f)
        }, 10_000)
    }
}