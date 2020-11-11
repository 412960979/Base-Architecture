package com.evergreencargo.jetpack_compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent {
            Greeting("Android Greeting")
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Text(name)
}

@Preview
@Composable
private fun GreetingPreview() {
    Greeting("Android Greeting")
}