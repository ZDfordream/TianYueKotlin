package com.zhudong.tianyuekotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.LifecycleTransformer
import com.zhudong.tianyuekotlin.MyApp
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.ui.listener.IBase
import com.zhudong.tianyuekotlin.utils.toast
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import javax.inject.Inject

/**
 * Created by zhudong on 2018/6/7
 */
abstract class BaseFragment<T1 : BaseContract.BasePresenter> : SupportFragment(), IBase, BaseContract.BaseView {

    private lateinit var mContext: Context
    private var mRootView: View? = null

    @Nullable
    private var mSimpleMultiStateView: SimpleMultiStateView? = null

    @Nullable
    @Inject
    @JvmField
    var mPresenter: T1? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView != null) {
            val parent = mRootView!!.parent as ViewGroup
            parent.removeView(mRootView)
        } else {
            mRootView = inflater!!.inflate(getContentLayout(), container, false)
        }
        mContext = mRootView!!.context
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInjector(MyApp.mApplicationComponent)
        attachView()
        mSimpleMultiStateView = getSimpleMultiStateView()
        bindView(view!!, savedInstanceState)
        initStateView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initData()
    }

    private fun attachView() {
        mPresenter?.attachView(this)
    }

    override fun onRetry() {

    }

    private fun initStateView() {
        mSimpleMultiStateView?.setEmptyResource(R.layout.view_empty)
                ?.setRetryResource(R.layout.view_retry)
                ?.setLoadingResource(R.layout.view_loading)
                ?.setNoNetResource(R.layout.view_nonet)
                ?.build()
                ?.setOnReLoadListener { onRetry() }
    }

    override fun showLoading() {
        mSimpleMultiStateView?.showLoadingView()
    }

    override fun showSuccess() {
        mSimpleMultiStateView?.showContent()
    }

    override fun showError() {
        mSimpleMultiStateView?.showErrorView()
    }

    override fun showNoNet() {
        mSimpleMultiStateView?.showNoNetView()
    }

    protected fun toast(string: String) {
        toast(activity, string)
    }

    override fun <T> bindToLife(): LifecycleTransformer<T> {
        return this.bindToLifecycle()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}
