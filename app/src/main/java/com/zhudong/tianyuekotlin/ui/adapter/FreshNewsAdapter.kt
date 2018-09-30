package com.zhudong.tianyuekotlin.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.FreshNewsBean
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil

/**
 * Created by zhudong on 2018/6/7
 */
class FreshNewsAdapter(private val context: Context, data: List<FreshNewsBean.PostsBean>?) : BaseQuickAdapter<FreshNewsBean.PostsBean, BaseViewHolder>(R.layout.item_freshnews, data), BaseQuickAdapter.OnItemClickListener {

    override fun convert(viewHolder: BaseViewHolder, postsBean: FreshNewsBean.PostsBean) {
        viewHolder.setText(R.id.tv_title, postsBean.title)
        viewHolder.setText(R.id.tv_info, postsBean.author!!.name)
        viewHolder.setText(R.id.tv_commnetsize, postsBean.comment_count.toString() + "评论")
        ImageLoaderUtil.LoadImage(context, postsBean.custom_fields!!.thumb_c!![0], viewHolder.getView<View>(R.id.iv_logo) as ImageView)
    }

    override fun onItemClick(baseQuickAdapter: BaseQuickAdapter<*, *>, view: View, i: Int) {
    }
}
