package com.zhudong.tianyuekotlin.ui.news

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.NewsArticleBean
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.component.DaggerHttpComponent
import com.zhudong.tianyuekotlin.ui.base.BaseActivity
import com.zhudong.tianyuekotlin.ui.news.contract.ArticleReadContract
import com.zhudong.tianyuekotlin.ui.news.presenter.ArticleReadPresenter
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil
import com.zhudong.tianyuekotlin.utils.getTimestampString
import com.zhudong.tianyuekotlin.utils.string2Date
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import kotlinx.android.synthetic.main.activity_artcleread.*


/**
 * Created by zhudong on 2018/6/7
 */

class ArticleReadActivity : BaseActivity<ArticleReadPresenter>(), ArticleReadContract.View {

    companion object {
        fun launch(context: Context, aid: String) {
            val intent = Intent(context, ArticleReadActivity::class.java)
            intent.putExtra("aid", aid)
            context.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_artcleread

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = simpleMultiStateView

    override fun initInjector(appComponent: ApplicationComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        setWebViewSetting()
        setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar), 30)
        scrollView.setScrollViewListener { _, _, y, _, _ ->
            when {
                y > constraintLayout.height -> rl_top.visibility = View.VISIBLE
                else -> rl_top.visibility = View.GONE
            }
        }

        iv_back.setOnClickListener { finish() }
        //修改了点代码
    }

    override fun initData() {}

    /**
     * 加载新闻内容
     *
     * @param articleBean 新闻内容
     */
    override fun loadData(articleBean: NewsArticleBean?) {
        articleBean?.let({
            tv_title.text = it.body?.title
            tv_TopUpdateTime.text = it.body?.author
            tv_updateTime.text = getTimestampString(string2Date(it.body?.updateTime!!, "yyyy/MM/dd HH:mm:ss"))

            if (it.body?.subscribe != null) {
                ImageLoaderUtil.LoadImage(this, it.body?.subscribe!!.logo, iv_logo, RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                ImageLoaderUtil.LoadImage(this, it.body?.subscribe!!.logo, iv_topLogo, RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                tv_topname.text = it.body?.subscribe?.cateSource
                tv_name.text = it.body?.subscribe?.cateSource
                tv_TopUpdateTime.text = it.body?.subscribe?.catename
            } else {
                tv_topname.text = it.body?.source
                tv_name.text = it.body?.source
                tv_TopUpdateTime.text = if (!TextUtils.isEmpty(articleBean.body?.author)) articleBean.body?.author else articleBean.body?.editorcode
            }

            webView.post({
                val content = articleBean.body?.text
                val url = "javascript:show_content(\'$content\')"
                webView.loadUrl(url)
                showSuccess()
            })
        })

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewSetting() {
        webView.settings.javaScriptEnabled = true
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.settings.setAppCacheEnabled(true)
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.loadUrl("file:///android_asset/ifeng/post_detail.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                val aid = intent.getStringExtra("aid")
                mPresenter?.getData(aid)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.hasNestedScrollingParent()
            webView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
        }
//        webView.startSafeBrowsing(this, ValueCallback<Boolean> { success ->
//            mSafeBrowsingIsInitialized = true
//            if (!success) {
//                Log.e("MY_APP_TAG", "Unable to initialize Safe Browsing!")
//            }
//        })
    }

}