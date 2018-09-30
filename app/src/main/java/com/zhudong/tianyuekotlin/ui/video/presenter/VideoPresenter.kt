package com.zhudong.tianyuekotlin.ui.video.presenter

import com.zhudong.tianyuekotlin.bean.VideoChannelBean
import com.zhudong.tianyuekotlin.bean.VideoDetailBean
import com.zhudong.tianyuekotlin.net.BaseObserver
import com.zhudong.tianyuekotlin.net.NewsApi
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.ui.video.contract.VideoContract
import com.zhudong.tianyuekotlin.utils.applySchedulers
import javax.inject.Inject

/**
 * Created by zhudong on 2018/6/7
 */
class VideoPresenter @Inject
constructor(private var mNewsApi: NewsApi) : BasePresenter<VideoContract.View>(), VideoContract.Presenter {

    /**
     * 获取视频频道列表
     *
     */
    override fun getVideoChannel() {
        mNewsApi.videoChannel
                .applySchedulers()
                .compose(mView!!.bindToLife<List<VideoChannelBean>>())
                .subscribe(object : BaseObserver<List<VideoChannelBean>>() {
                    override fun onSuccess(t: List<VideoChannelBean>?) {
                        mView?.loadVideoChannel(t)
                    }

                    override fun onFail(e: Throwable) {
                        mView?.showError()
                    }

                })

    }

    /**
     * 获取视频列表
     *
     * @param page     页码
     * @param listType 默认list
     * @param typeId   频道id
     */
    override fun getVideoDetails(page: Int, listType: String, typeId: String) {
        mNewsApi.getVideoDetail(page, listType, typeId)
                .applySchedulers()
                .compose(mView!!.bindToLife<List<VideoDetailBean>>())
                .subscribe(object : BaseObserver<List<VideoDetailBean>>() {
                    override fun onSuccess(t: List<VideoDetailBean>?) {
                        when {
                            page > 1 -> mView!!.loadMoreVideoDetails(t)
                            else -> mView!!.loadVideoDetails(t)
                        }
                    }

                    override fun onFail(e: Throwable) {
                        mView?.showError()
                    }
                })
    }
}
