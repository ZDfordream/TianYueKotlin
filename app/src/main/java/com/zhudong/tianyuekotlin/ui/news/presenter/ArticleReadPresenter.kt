package com.zhudong.tianyuekotlin.ui.news.presenter


import com.zhudong.tianyuekotlin.bean.NewsArticleBean
import com.zhudong.tianyuekotlin.net.BaseObserver
import com.zhudong.tianyuekotlin.net.NewsApi
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.ui.news.contract.ArticleReadContract
import com.zhudong.tianyuekotlin.utils.applySchedulers
import javax.inject.Inject


/**
 * Created by zhudong on 2018/6/7
 */
class ArticleReadPresenter @Inject
constructor(private var mNewsApi: NewsApi) : BasePresenter<ArticleReadContract.View>(), ArticleReadContract.Presenter {

    /**
     * 获取新闻内容
     *
     * @param aid 新闻id
     */
    override fun getData(aid: String) {
        mNewsApi.getNewsArticle(aid)
                .applySchedulers()
                .compose(mView?.bindToLife<NewsArticleBean>())
                .subscribe(object : BaseObserver<NewsArticleBean>(){
                    override fun onSuccess(t: NewsArticleBean?) {
                        mView?.loadData(t)
                    }

                    override fun onFail(e: Throwable) {
                        mView?.loadData(null)
                    }
                })

    }
}
