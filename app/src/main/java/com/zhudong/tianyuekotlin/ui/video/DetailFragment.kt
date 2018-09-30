package com.zhudong.tianyuekotlin.ui.video

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.VideoChannelBean
import com.zhudong.tianyuekotlin.bean.VideoDetailBean
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.component.DaggerHttpComponent
import com.zhudong.tianyuekotlin.ui.adapter.VideoDetailAdapter
import com.zhudong.tianyuekotlin.ui.base.BaseFragment
import com.zhudong.tianyuekotlin.ui.video.contract.VideoContract
import com.zhudong.tianyuekotlin.ui.video.presenter.VideoPresenter
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import com.zhudong.tianyuekotlin.widget.channelDialog.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * Created by zhudong on 2018/6/7
 */
class DetailFragment : BaseFragment<VideoPresenter>(), VideoContract.View {
    private var videoDetailBean: VideoDetailBean? = null
    private lateinit var detailAdapter: VideoDetailAdapter
    private var pageNum = 1
    private var typeId: String? = null

    companion object {
        fun newInstance(typeId: String): DetailFragment {
            val args = Bundle()
            args.putCharSequence("typeId", typeId)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_detail

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = mSimpleMultiStateView

    override fun initInjector(appComponent: ApplicationComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        mPtrFrameLayout.disableWhenHorizontalMove(true)
        mPtrFrameLayout.setPtrHandler(object : PtrHandler {
            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header)
            }

            override fun onRefreshBegin(frame: PtrFrameLayout) {
                pageNum = 1
                mPresenter?.getVideoDetails(pageNum, "list", typeId!!)
            }
        })
        videoDetailBean = VideoDetailBean()
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        detailAdapter = VideoDetailAdapter(activity, R.layout.item_detail_video, videoDetailBean?.item)
        mRecyclerView.adapter = detailAdapter
        detailAdapter.setEnableLoadMore(true)
        detailAdapter.setLoadMoreView(CustomLoadMoreView())
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        detailAdapter.setOnLoadMoreListener({ mPresenter?.getVideoDetails(pageNum, "list", typeId!!) }, mRecyclerView)
    }

    override fun initData() {
        when (arguments) {
            null -> return
            else -> {
                typeId = arguments!!.getString("typeId")
                mPresenter?.getVideoDetails(pageNum, "list", typeId!!)
            }
        }
    }

    /**
     * 加载频道视频列表
     *
     * @param detailBean 视频列表
     */
    override fun loadVideoDetails(detailBean: List<VideoDetailBean>?) {
        when (detailBean) {
            null -> {
                showError()
                return
            }
            else -> {
                pageNum++
                detailAdapter.setNewData(detailBean[0].item)
                mPtrFrameLayout.refreshComplete()
                showSuccess()
            }
        }
    }

    /**
     * 加载更多频道视频列表
     *
     * @param detailBean 视频列表
     */
    override fun loadMoreVideoDetails(detailBean: List<VideoDetailBean>?) {
        when (detailBean) {
            null -> {
                detailAdapter.loadMoreEnd()
                return
            }
            else -> {
                pageNum++
                detailBean[0].item?.let {
                    detailAdapter.addData(it)
                    detailAdapter.loadMoreComplete()
                }
            }
        }
    }

    override fun loadVideoChannel(channelBean: List<VideoChannelBean>?) {
    }

}