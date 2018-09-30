package com.zhudong.tianyuekotlin.ui.adapter

import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.NewsDetail
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil

/**
 * Created by zhudong on 2018/6/7
 */
class NewsDetailAdapter(data: List<NewsDetail.ItemBean>?, private var context: FragmentActivity?) : BaseMultiItemQuickAdapter<NewsDetail.ItemBean, BaseViewHolder>(data) {

    init {
        addItemType(NewsDetail.ItemBean.TYPE_DOC_TITLEIMG, R.layout.item_detail_doc)
        addItemType(NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG, R.layout.item_detail_doc_slideimg)
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG, R.layout.item_detail_advert)
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG, R.layout.item_detail_advert_slideimg)
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG, R.layout.item_detail_advert_longimage)
        addItemType(NewsDetail.ItemBean.TYPE_SLIDE, R.layout.item_detail_slide)
        addItemType(NewsDetail.ItemBean.TYPE_PHVIDEO, R.layout.item_detail_phvideo)
    }

    override fun convert(helper: BaseViewHolder?, item: NewsDetail.ItemBean?) {
        when (helper!!.itemViewType) {
            NewsDetail.ItemBean.TYPE_DOC_TITLEIMG, NewsDetail.ItemBean.TYPE_SLIDE, NewsDetail.ItemBean.TYPE_PHVIDEO -> {

                helper.setText(R.id.tv_title, item!!.title)
                        .setText(R.id.tv_source, item.source)
                        .setText(R.id.tv_commnetsize, String.format(context!!.resources.getString(R.string.news_commentsize)
                                , item.commentsall))
                        .addOnClickListener(R.id.iv_close)
                ImageLoaderUtil.LoadImage(context, item.thumbnail, helper.getView(R.id.iv_logo) as ImageView)

            }
            NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG -> {

                helper.setText(R.id.tv_title, item!!.title)
                        .setText(R.id.tv_source, item.source)
                        .setText(R.id.tv_commnetsize, String.format(context!!.resources.getString(R.string.news_commentsize)
                                , item.commentsall))
                        .addOnClickListener(R.id.iv_close)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(0), helper.getView(R.id.iv_1) as ImageView)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(1), helper.getView(R.id.iv_2) as ImageView)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(2), helper.getView(R.id.iv_3) as ImageView)

            }
            NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG, NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG -> {

                helper.setText(R.id.tv_title, item!!.title)
                        .addOnClickListener(R.id.iv_close)
                ImageLoaderUtil.LoadImage(context, item.thumbnail, helper.getView(R.id.iv_logo) as ImageView)

            }
            NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG -> {

                helper.setText(R.id.tv_title, item!!.title)
                        .addOnClickListener(R.id.iv_close)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(0), helper.getView(R.id.iv_1) as ImageView)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(1), helper.getView(R.id.iv_2) as ImageView)
                ImageLoaderUtil.LoadImage(context, item.style?.images?.get(2), helper.getView(R.id.iv_3) as ImageView)

            }
        }
    }
}