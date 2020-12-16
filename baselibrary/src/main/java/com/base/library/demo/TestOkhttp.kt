package com.base.library.demo

import okhttp3.OkHttpClient
import okhttp3.Request

fun testOkHttp(){
    val okHttpClient = OkHttpClient()

    val request = Request.Builder()
        .url("www.baidu.com")
        .build()

    val response = okHttpClient.newCall(request).execute()
    response.code
}