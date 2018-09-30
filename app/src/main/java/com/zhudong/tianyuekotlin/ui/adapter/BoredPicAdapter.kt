package com.zhudong.tianyuekotlin.ui.adapter

import android.app.Activity
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.MyApp
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.JdDetailBean
import com.zhudong.tianyuekotlin.ui.jandan.ImageBrowseActivity
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil
import com.zhudong.tianyuekotlin.utils.getTimestampString
import com.zhudong.tianyuekotlin.utils.string2Date
import com.zhudong.tianyuekotlin.widget.ContextUtils
import com.zhudong.tianyuekotlin.widget.MultiImageView
import com.zhudong.tianyuekotlin.widget.ShowMaxImageView

/**
 * Created by zhudong on 2018/6/7
 */
class BoredPicAdapter(private val context: Activity, data: List<JdDetailBean.CommentsBean>?) : BaseMultiItemQuickAdapter<JdDetailBean.CommentsBean, BaseViewHolder>(data) {

    init {
        addItemType(JdDetailBean.CommentsBean.TYPE_MULTIPLE, R.layout.item_jandan_pic)
        addItemType(JdDetailBean.CommentsBean.TYPE_SINGLE, R.layout.item_jandan_pic_single)
    }

    override fun convert(viewHolder: BaseViewHolder, commentsBean: JdDetailBean.CommentsBean) {
        viewHolder.setText(R.id.tv_author, commentsBean.comment_author)

        if (!TextUtils.isEmpty(commentsBean.comment_agent)) {
            if (commentsBean.comment_agent!!.contains("Android")) {
                viewHolder.setText(R.id.tv_from, "来自 Android 客户端")
                viewHolder.setVisible(R.id.tv_from, true)
            } else {
                viewHolder.setVisible(R.id.tv_from, false)
            }
        } else {
            viewHolder.setVisible(R.id.tv_from, false)
        }

        viewHolder.setText(R.id.tv_time, getTimestampString(string2Date(commentsBean.comment_date!!, "yyyy-MM-dd HH:mm:ss")))

        if (TextUtils.isEmpty(commentsBean.text_content)) {
            viewHolder.setVisible(R.id.tv_content, false)
        } else {
            viewHolder.setVisible(R.id.tv_content, true)
            val content = commentsBean.text_content!!.replace(" ", "").replace("\r", "").replace("\n", "")
            viewHolder.setText(R.id.tv_content, content)
        }

        viewHolder.setVisible(R.id.img_gif, commentsBean.pics!![0].contains("gif"))
        viewHolder.setVisible(R.id.progress, commentsBean.pics!![0].contains("gif"))
        viewHolder.setText(R.id.tv_like, commentsBean.vote_negative)
        viewHolder.setText(R.id.tv_unlike, commentsBean.vote_positive)
        viewHolder.setText(R.id.tv_comment_count, commentsBean.sub_comment_count)
        viewHolder.addOnClickListener(R.id.img_share)

        when (viewHolder.itemViewType) {
            JdDetailBean.CommentsBean.TYPE_MULTIPLE -> {
                val multiImageView = viewHolder.getView<MultiImageView>(R.id.img)
                viewHolder.setVisible(R.id.img_gif, false)
                multiImageView.setList(commentsBean.pics)
                multiImageView.setOnItemClickListener { _, position ->

                    var imgUrls: List<String> = emptyList()
                    for (i in 0 until commentsBean.pics!!.size) {
                        imgUrls += commentsBean.pics!![i]
                    }
                    ImageBrowseActivity.launch(context, imgUrls, position)
                }
            }
            JdDetailBean.CommentsBean.TYPE_SINGLE -> {
                val imageView = viewHolder.getView<ShowMaxImageView>(R.id.img)
                imageView.layoutParams.height = ContextUtils.dip2px(MyApp.instance, 250f)

                imageView.setOnClickListener({
                    var imgUrls: List<String> = emptyList()
                    imgUrls += commentsBean.pics!![0]
                    ImageBrowseActivity.launch(context, imgUrls, 0)
                })
                ImageLoaderUtil.LoadImage(context, commentsBean.pics!![0],
                        object : DrawableImageViewTarget(viewHolder.getView<View>(R.id.img) as ImageView) {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                super.onResourceReady(resource, transition)
                                val pmWidth = ContextUtils.getSreenWidth(MyApp.instance)
                                val pmHeight = ContextUtils.getSreenHeight(MyApp.instance)
                                val sal = pmHeight.toFloat() / pmWidth
                                val actualHeight = Math.ceil((sal * resource.intrinsicWidth).toDouble()).toInt()
                                val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actualHeight)
                                viewHolder.getView<View>(R.id.img).layoutParams = params
                                viewHolder.setVisible(R.id.img_gif, false)
                            }
                        })
            }
        }

    }
}
