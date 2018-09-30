package com.zhudong.tianyuekotlin.ui.adapter

import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.VideoDetailBean
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil
import com.zhudong.tianyuekotlin.utils.conversionPlayTime
import com.zhudong.tianyuekotlin.utils.conversionTime
import fm.jiecao.jcvideoplayer_lib.JCUserAction
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 * Created by zhudong on 2018/6/7
 */
class VideoDetailAdapter(private var context: FragmentActivity?, @LayoutRes layoutResId: Int, data: List<VideoDetailBean.ItemBean>?)
    : BaseQuickAdapter<VideoDetailBean.ItemBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(viewHolder: BaseViewHolder, itemBean: VideoDetailBean.ItemBean) {
        // viewHolder.setText(R.id.tv_title, itemBean.getTitle());
        val jcVideoPlayerStandard = viewHolder.getView<JCVideoPlayerStandard>(R.id.videoplayer)
        jcVideoPlayerStandard.setUp(itemBean.video_url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, itemBean.title)
        JCVideoPlayer.setJcUserAction({ type, _, _, _ ->
            when (type) {
                JCUserAction.ON_CLICK_START_ICON -> {
                    viewHolder.getView<View>(R.id.tv_videoduration).visibility = View.GONE
                }
            }
        })
        ImageLoaderUtil.LoadImage(context, itemBean.image, jcVideoPlayerStandard.thumbImageView)
        viewHolder.setText(R.id.tv_videoduration, conversionTime(itemBean.duration))
        itemBean.playTime?.let {
            viewHolder.setText(R.id.tv_playtime, conversionPlayTime(Integer.valueOf(it)!!))
        }

    }

}
