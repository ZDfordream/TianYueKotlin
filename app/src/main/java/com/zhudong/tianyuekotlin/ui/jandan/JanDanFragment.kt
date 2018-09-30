package com.zhudong.tianyuekotlin.ui.jandan

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.JdBaseBean
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.net.JanDanApi
import com.zhudong.tianyuekotlin.ui.adapter.BoredPicAdapter
import com.zhudong.tianyuekotlin.ui.adapter.FreshNewsAdapter
import com.zhudong.tianyuekotlin.ui.adapter.JokesAdapter
import com.zhudong.tianyuekotlin.ui.base.BaseContract
import com.zhudong.tianyuekotlin.ui.base.BaseFragment
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import kotlinx.android.synthetic.main.fragment_jiandan.*
import java.util.*


/**
 * Created by zhudong on 2018/6/7
 */
class JanDanFragment : BaseFragment<BasePresenter<BaseContract.BaseView>>() {
    private var mJanDanPagerAdapter: JanDanPagerAdapter? = null

    companion object {
        fun newInstance(): JanDanFragment {
            val args = Bundle()
            val fragment = JanDanFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = null

    override fun initInjector(appComponent: ApplicationComponent) {}

    override fun getContentLayout(): Int = R.layout.fragment_jiandan

    override fun bindView(view: View, savedInstanceState: Bundle?) {}

    override fun initData() {
        val strings = ArrayList<String>()
        strings.add("新鲜事")
        strings.add("无聊图")
        strings.add("妹子图")
        strings.add("段子")
        mJanDanPagerAdapter = JanDanPagerAdapter(childFragmentManager, strings)
        viewpager.adapter = mJanDanPagerAdapter
        viewpager.offscreenPageLimit = 1
        viewpager.setCurrentItem(0, false)
        tablayout.setupWithViewPager(viewpager, true)
    }

    inner class JanDanPagerAdapter(fm: FragmentManager, private val titles: List<String>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): JdDetailFragment? {
            when (position) {
                0 -> return JdDetailFragment.newInstance(JanDanApi.TYPE_FRESH, activity?.let { FreshNewsAdapter(it, null) } as BaseQuickAdapter<in JdBaseBean
                        , BaseViewHolder>)
                1 -> return JdDetailFragment.newInstance(JanDanApi.TYPE_BORED, activity?.let { BoredPicAdapter(it, null) } as BaseQuickAdapter<in JdBaseBean
                        , BaseViewHolder>)
                2 -> return JdDetailFragment.newInstance(JanDanApi.TYPE_GIRLS, activity?.let { BoredPicAdapter(it, null) } as BaseQuickAdapter<in JdBaseBean
                        , BaseViewHolder>)
                3 -> return JdDetailFragment.newInstance(JanDanApi.TYPE_Duan, JokesAdapter(null) as BaseQuickAdapter<in JdBaseBean
                        , BaseViewHolder>)
            }
            return null
        }

        override fun getPageTitle(position: Int): CharSequence = titles[position]

        override fun getCount(): Int = titles.size

        override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    }
}