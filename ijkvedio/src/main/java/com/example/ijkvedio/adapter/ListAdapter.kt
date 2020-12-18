package com.example.ijkvedio.adapter

import android.util.ArrayMap
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dou361.ijkplayer.widget.IjkVideoView
import com.example.ijkvedio.IjkPlayer
import com.example.ijkvedio.R
import com.example.ijkvedio.model.VideoModel

class ListAdapter(data: List<VideoModel>) :
    BaseQuickAdapter<VideoModel, BaseViewHolder>(R.layout.item_video, data.toMutableList()) {

    private var palyPos: Int = 0

    // 记录每个视频的位置对应的VideoView对象
    private val map = ArrayMap<Int, IjkVideoView>()

    override fun convert(holder: BaseViewHolder, item: VideoModel) {
        val rlVideoRoot = holder.getView<RelativeLayout>(R.id.rl_video_root)
        val tvTitle = holder.getView<TextView>(R.id.tv_title)

        val ijkPlayerView = holder.getView<IjkVideoView>(R.id.ijkplayerView)
        ijkPlayerView.setMediaController(IjkPlayer.mMediaController)

        with(item) {
            ijkPlayerView.setBackgroundColor(bg)
//            rlVideoRoot.setBackgroundColor(bg)
//            tvTitle.text = title
//
            ijkPlayerView.setVideoPath(url)
        }

        if (holder.layoutPosition == 0)
            ijkPlayerView.start()

        map[holder.layoutPosition] = ijkPlayerView
    }

    fun play(pos: Int) {
        map.forEach{
            if (map[it.key]!!.isPlaying){
                map[it.key]!!.stopPlayback()
                map[it.key]!!.release(true)
            }
        }

        map[pos]!!.start()
        palyPos = pos
    }

    fun stop (){
        map.forEach{
            if (map[it.key]!!.isPlaying){
                map[it.key]!!.stopPlayback()
                map[it.key]!!.release(true)
            }
        }
    }

    fun getPlayPos() = palyPos
}