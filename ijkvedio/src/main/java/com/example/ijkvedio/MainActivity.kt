package com.example.ijkvedio

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // 直播
            start<IjkLiveActivity>()
        }

        button2.setOnClickListener {
            // 短视频
            start<IjkVideoActivity>()
        }
    }
}

inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}){
    startActivity(Intent(this, A::class.java).apply {
        configIntent()
    })
}