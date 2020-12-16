package com.example.ijkvedio.model

import android.graphics.Color

data class VideoModel(
    val bg: Int,
    val title: String
)

fun getVideoList(): List<VideoModel>{
    return mutableListOf(
        VideoModel(Color.BLACK, "BLACK"),
        VideoModel(Color.BLUE, "BLUE"),
        VideoModel(Color.RED, "RED"),
        VideoModel(Color.DKGRAY, "DKGRAY")
    )
}