package com.zhudong.tianyuekotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.JdDetailBean
import com.zhudong.tianyuekotlin.utils.getTimestampString
import com.zhudong.tianyuekotlin.utils.string2Date

/**
 * Created by zhudong on 2018/6/7
 */
class JokesAdapter(data: List<JdDetailBean.CommentsBean>?) : BaseQuickAdapter<JdDetailBean.CommentsBean, BaseViewHolder>(R.layout.item_joke, data) {

    override fun convert(viewHolder: BaseViewHolder, commentsBean: JdDetailBean.CommentsBean) {
        viewHolder.setText(R.id.tv_author, commentsBean.comment_author)
                .setText(R.id.tv_time, getTimestampString(string2Date(commentsBean.comment_date!!, "yyyy-MM-dd HH:mm:ss")))
                .setText(R.id.tv_content, commentsBean.text_content)
                .setText(R.id.tv_like, commentsBean.vote_negative)
                .setText(R.id.tv_unlike, commentsBean.vote_positive)
                .setText(R.id.tv_comment_count, commentsBean.sub_comment_count)
    }
}
