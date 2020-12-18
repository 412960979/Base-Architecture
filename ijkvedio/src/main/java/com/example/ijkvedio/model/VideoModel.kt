package com.example.ijkvedio.model

import android.graphics.Color

data class VideoModel(
    val bg: Int,
    val title: String,
    val url: String = ""
)

fun getVideoList(): List<VideoModel>{
    return mutableListOf(
        VideoModel(Color.BLACK, "BLACK"),
        VideoModel(Color.BLUE, "BLUE"),
        VideoModel(Color.RED, "RED"),
        VideoModel(Color.DKGRAY, "DKGRAY")
    )
}

fun getLiveList(): List<VideoModel>{
    return mutableListOf(
        // cctv2
        VideoModel(Color.BLACK, "BLACK", "rtmp://58.200.131.2:1935/livetv/cctv2"),
        // CCTV1
        VideoModel(Color.BLUE, "BLUE", "rtmp://58.200.131.2:1935/livetv/cctv1"),
        // CCTV6
        VideoModel(Color.RED, "RED", "rtmp://58.200.131.2:1935/livetv/cctv6"),
    )
}
