package com.zhudong.tianyuekotlin.ui.personal

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.ui.base.BaseContract
import com.zhudong.tianyuekotlin.ui.base.BaseFragment
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.utils.ImageLoaderUtil
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by zhudong on 2018/6/7
 */
class PersonalFragment : BaseFragment<BasePresenter<BaseContract.BaseView>>() {
    companion object {
        fun newInstance(): PersonalFragment {
            val args = Bundle()
            val fragment = PersonalFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = null

    override fun initInjector(appComponent: ApplicationComponent) {}

    override fun getContentLayout(): Int = R.layout.fragment_personal

    override fun initData() {}

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        ImageLoaderUtil.LoadImage(this, "http://ovuc1rhny.bkt.clouddn.com/15833.jpg", ivAuthor,
                RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
        val mTypeFace = Typeface.createFromAsset(activity!!.assets, "font/consolab.ttf")
        tvContacts.typeface = mTypeFace
        setFont(tvName, tvGithub, tvEmail, tvGithubUrl, tvUrl, tvEmailUrl)

        tvUrl.setOnClickListener {
            toWeb(resources.getString(R.string.githubUrl))
        }
        tvGithubUrl.setOnClickListener {
            toWeb(resources.getString(R.string.githubUrl))
        }
    }

    private fun setFont(vararg view: TextView) {
        val typeface = Typeface.createFromAsset(activity!!.assets, "font/consola.ttf")
        view.forEach {
            it.typeface = typeface
        }
    }

    private fun toWeb(url: String) {
        val webUrl = Uri.parse(url)
        val webIntent = Intent(Intent.ACTION_VIEW, webUrl)
        activity!!.startActivity(webIntent)
    }

}