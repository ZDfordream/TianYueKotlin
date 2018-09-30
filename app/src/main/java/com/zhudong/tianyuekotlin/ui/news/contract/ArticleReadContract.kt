package com.zhudong.tianyuekotlin.ui.news.contract


import com.zhudong.tianyuekotlin.bean.NewsArticleBean
import com.zhudong.tianyuekotlin.ui.base.BaseContract

/**
 * Created by zhudong on 2018/6/7
 */
interface ArticleReadContract {

    interface View : BaseContract.BaseView {

        /**
         * 加载新闻内容
         *
         * @param articleBean 新闻内容
         */
        fun loadData(articleBean: NewsArticleBean?)
    }

    interface Presenter : BaseContract.BasePresenter {

        /**
         * 获取新闻内容
         *
         * @param aid 新闻id
         */
        fun getData(aid: String)

    }

}
