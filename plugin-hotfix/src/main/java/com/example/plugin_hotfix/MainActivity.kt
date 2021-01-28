package com.example.plugin_hotfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.com.example.plugin_hotfix.R
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            Log.i("com.example.test", "start")

            Looper.prepare()

            mHandler = Handler() {
                Log.i("com.example.test", "middle${it.what}")

                false
            }

            Looper.loop()

            Log.i("com.example.test", "end")
        }

        findViewById<TextView>(R.id.textView).setOnClickListener {
            if (::mHandler.isInitialized)
                mHandler.sendEmptyMessage(100)
        }
    }
}