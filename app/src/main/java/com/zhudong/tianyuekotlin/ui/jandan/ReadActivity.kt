package com.zhudong.tianyuekotlin.ui.jandan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.FreshNewsArticleBean
import com.zhudong.tianyuekotlin.bean.FreshNewsBean
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.net.BaseObserver
import com.zhudong.tianyuekotlin.net.JanDanApi
import com.zhudong.tianyuekotlin.ui.base.BaseActivity
import com.zhudong.tianyuekotlin.ui.base.BaseContract
import com.zhudong.tianyuekotlin.utils.*
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import kotlinx.android.synthetic.main.activity_read.*


/**
 * Created by zhudong on 2018/6/7
 */
class ReadActivity : BaseActivity<BaseContract.BasePresenter>() {
    companion object {
        fun launch(context: Context, postsBean: FreshNewsBean.PostsBean) {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra("data", postsBean)
            context.startActivity(intent)
        }
    }

    private lateinit var mJanDanApi: JanDanApi
    private var postsBean: FreshNewsBean.PostsBean? = null
    private var newsArticleBean: FreshNewsArticleBean? = null

    override fun getContentLayout(): Int = R.layout.activity_read

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = null

    override fun initInjector(appComponent: ApplicationComponent) {
        mJanDanApi = appComponent.getJanDanApi()
    }

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucentForImageView(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA, null)
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        intent.extras?.let {
            postsBean = intent.getSerializableExtra("data") as FreshNewsBean.PostsBean
            postsBean?.let {
                tv_title.text = it.title
                tv_author.text = (it.author?.name
                        + "  "
                        + getTimestampString(string2Date(it.date!!, "yyyy-MM-dd HH:mm:ss")))
                tv_excerpt.text = it.excerpt
                it.custom_fields?.thumb_c?.get(0)?.let {
                    ImageLoaderUtil.LoadImage(this, it, iv_logo)
                }
                setWebViewSetting()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewSetting() {
        webView.settings.javaScriptEnabled = true
        webView.settings.setAppCacheEnabled(true)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.loadUrl("file:///android_asset/jd/post_detail.html")
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                postsBean?.let {
                    getData(it.id)
                }
            }
        }
    }

    /**
     * 获取新鲜事正文
     */
    private fun getData(id: Int) {
        mJanDanApi.getFreshNewsArticle(id)
                .applySchedulers()
                .compose(this.bindToLifecycle<FreshNewsArticleBean>())
                .subscribe(object : BaseObserver<FreshNewsArticleBean>() {
                    override fun onSuccess(t: FreshNewsArticleBean?) {
                        when (t) {
                            null -> showError()
                            else -> {
                                newsArticleBean = t
                                webView.post({
                                    progress_wheel.visibility = View.GONE
                                    val content = t.post?.content
                                    val url = "javascript:show_content(\'$content\')"
                                    webView.loadUrl(url)
                                })
                            }
                        }
                    }

                    override fun onFail(e: Throwable) {
                        showError()
                    }
                })
    }

}