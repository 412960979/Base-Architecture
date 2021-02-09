package com.wn.parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User("wei", "ning", 16)
        Intent().putExtra("user", user)
        val myClass = MyClass(ExternalClass(5))
        Intent().putExtra("myClass", myClass)
    }
}