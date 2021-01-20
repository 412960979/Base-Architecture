package com.base.koltin.demo.proxy
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose

data class Bean(
    /**
     * 这两个注解作用是，不用在混淆文件里面添加keep这个类，不然网络请求找不到字段
     */
    @SerializedName("a")
    @Expose
    val a: Int
)