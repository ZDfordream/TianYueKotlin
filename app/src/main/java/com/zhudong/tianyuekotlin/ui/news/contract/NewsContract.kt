package com.zhudong.tianyuekotlin.ui.news.contract


import com.zhudong.tianyuekotlin.bean.Channel
import com.zhudong.tianyuekotlin.ui.base.BaseContract

/**
 * Created by zhudong on 2018/6/7
 */
interface NewsContract {

    interface View : BaseContract.BaseView {

        /**
         * 初始化频道
         */
        fun loadData(channels: List<Channel>, otherChannels: List<Channel>)

    }

    interface Presenter : BaseContract.BasePresenter {
        /**
         * 获取频道列表
         */
        fun getChannel()

    }

}
