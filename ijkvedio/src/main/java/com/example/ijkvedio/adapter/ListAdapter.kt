package com.example.ijkvedio.adapter

import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.ijkvedio.R
import com.example.ijkvedio.model.VideoModel

class ListAdapter(data: List<VideoModel>) :
    BaseQuickAdapter<VideoModel, BaseViewHolder>(R.layout.item_video, data.toMutableList()) {

    override fun convert(holder: BaseViewHolder, item: VideoModel) {
        val rlVideoRoot = holder.getView<RelativeLayout>(R.id.rl_video_root)
        val tvTitle = holder.getView<TextView>(R.id.tv_title)

        val a = with(item){
            rlVideoRoot.setBackgroundColor(bg)
            tvTitle.text = title
        }
    }

}