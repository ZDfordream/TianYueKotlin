package com.zhudong.tianyuekotlin.ui.jandan.presenter

import com.zhudong.tianyuekotlin.bean.FreshNewsBean
import com.zhudong.tianyuekotlin.bean.JdDetailBean
import com.zhudong.tianyuekotlin.net.BaseObserver
import com.zhudong.tianyuekotlin.net.JanDanApi
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.ui.jandan.contract.JanDanContract
import com.zhudong.tianyuekotlin.utils.applySchedulers
import javax.inject.Inject

/**
 * Created by zhudong on 2018/6/7
 */
class JanDanPresenter @Inject
constructor(private var mJanDanApi: JanDanApi) : BasePresenter<JanDanContract.View>(), JanDanContract.Presenter {

    /**
     * 获取新鲜事,无聊图、妹子图、段子列表
     *
     * @param type  [com.zhudong.tianyuekotlin.net.JanDanApi.Type]
     * @param page 获取数据数量
     */
    override fun getData(type: String, page: Int) {
        when (type) {
            JanDanApi.TYPE_FRESH -> getFreshNews(page)
            else -> getDetailData(type, page)
        }
    }

    /**
     * 获取新鲜事列表
     *
     * @param page 获取数据数量
     */
    override fun getFreshNews(page: Int) {
        mJanDanApi.getFreshNews(page)
                .applySchedulers()
                .compose(mView?.bindToLife<FreshNewsBean>())
                .subscribe(object : BaseObserver<FreshNewsBean>() {
                    override fun onSuccess(t: FreshNewsBean?) {
                        when {
                            page > 1 -> mView?.loadMoreFreshNews(t)
                            else -> mView?.loadFreshNews(t)
                        }
                    }

                    override fun onFail(e: Throwable) {
                        when {
                            page > 1 -> mView?.loadMoreFreshNews(null)
                            else -> mView?.loadFreshNews(null)
                        }
                    }
                })

    }

    /**
     * 获取无聊图、妹子图、段子列表
     *
     * @param type  [com.zhudong.tianyuekotlin.net.JanDanApi.Type]
     * @param page 获取数据数量
     */
    override fun getDetailData(type: String, page: Int) {
        mJanDanApi.getJdDetails(type, page)
                .applySchedulers()
                .compose(mView?.bindToLife<JdDetailBean>())
                .subscribe(object : BaseObserver<JdDetailBean>() {
                    override fun onSuccess(t: JdDetailBean?) {
                        when {
                            page > 1 -> mView?.loadMoreDetailData(type, t)
                            else -> mView?.loadDetailData(type, t)
                        }
                    }

                    override fun onFail(e: Throwable) {
                        when {
                            page > 1 -> mView?.loadMoreDetailData(type, null)
                            else -> mView?.loadDetailData(type, null)
                        }
                    }
                })
    }
}
