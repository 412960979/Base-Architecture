package com.example.ijkvedio

import android.content.Context
import com.dou361.ijkplayer.widget.AndroidMediaController
import tv.danmaku.ijk.media.player.IjkMediaPlayer

object IjkPlayer {
    lateinit var mMediaController: AndroidMediaController

    // 单例模式对象
    @get:JvmName("ijkMediaPlayer")
    val ijkMediaPlayer: IjkMediaPlayer by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        IjkMediaPlayer()
    }

    /**
     * 初始化播放库
     */
    fun init(context: Context) {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0)
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0)

        // 黑屏
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec_mpeg4", 1)
        // 降低 播放 rtmp 播放的延迟
        ijkMediaPlayer.setOption(1, "analyzemaxduration", 100L)
        ijkMediaPlayer.setOption(1, "probesize", 10240L)
        ijkMediaPlayer.setOption(1, "flush_packets", 1L)
        ijkMediaPlayer.setOption(4, "packet-buffering", 0L)
        //丢帧
        ijkMediaPlayer.setOption(4, "framedrop", 1L)

        //硬解码造成黑屏无声
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1)
        ijkMediaPlayer.setOption(
            IjkMediaPlayer.OPT_CATEGORY_PLAYER,
            "mediacodec-hanle-resolution-change",
            1
        )

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 1)

        // 清空DNS，因为DNS的问题报10000
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1)

        //使用AndroidMediaController类控制播放相关操作
        mMediaController = AndroidMediaController(context, false)
//        mMediaController.setSupportActionBar(actionBar);
        mMediaController.hide()
    }
}