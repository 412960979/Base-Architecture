package com.base.android

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

        btnGlobalDialog.setOnClickListener {
//            startActivity(Intent(this, GlobalDialogActivity::class.java))
            start<GlobalDialogActivity>()
        }
    }
}

inline  fun <reified A : Activity> Context.start(extra: Intent.() -> Unit = {}){
    this.startActivity(Intent(this, A::class.java).apply(extra))
}