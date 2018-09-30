package com.zhudong.tianyuekotlin.widget.channelDialog

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.zhudong.tianyuekotlin.R


/**
 * Created by zhudong on 2018/6/1
 */
class CustomLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.view_load_more
    }

    /**
     * If you return to true, the data will be loaded more after all the data is loaded.
     * If you return to false, the data will be displayed after all the getLoadEndViewId () layout
     */
    override fun isLoadEndGone(): Boolean {
        return true
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    /**
     * IsLoadEndGone () for true, you can return 0
     * IsLoadEndGone () for false, can not return 0
     */
    override fun getLoadEndViewId(): Int {
        return 0
    }
}