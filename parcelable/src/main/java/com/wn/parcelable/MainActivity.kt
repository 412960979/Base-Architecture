package com.wn.parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wn.parcelable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView

        val user = User("wei", "ning", 16)
        Intent().putExtra("user", user)
        val myClass = MyClass(ExternalClass(5))
        Intent().putExtra("myClass", myClass)

    }
}