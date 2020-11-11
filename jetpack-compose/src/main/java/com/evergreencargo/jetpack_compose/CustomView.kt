package com.evergreencargo.jetpack_compose

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview

@Composable
private fun Greeting(name: String) {
    Text(name)
}

@Preview
@Composable
private fun GreetingPreview() {
    Greeting("Android Greeting")
}