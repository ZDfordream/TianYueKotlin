package com.zhudong.tianyuekotlin.ui.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import com.zhudong.tianyuekotlin.bean.VideoChannelBean
import com.zhudong.tianyuekotlin.ui.base.BaseFragment
import com.zhudong.tianyuekotlin.ui.video.DetailFragment

/**
 * Created by zhudong on 2018/6/7
 */
class VideoPagerAdapter(fm: FragmentManager, private val videoChannelBean: VideoChannelBean?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment<*>
            = DetailFragment.newInstance("clientvideo_" + videoChannelBean!!.types!![position].id)

    override fun getPageTitle(position: Int): CharSequence = videoChannelBean!!.types!![position].name!!

    override fun getCount(): Int = videoChannelBean?.types?.size ?: 0

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

}
