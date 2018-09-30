package com.zhudong.tianyuekotlin.ui.jandan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.ui.base.BaseContract
import com.zhudong.tianyuekotlin.ui.base.BaseImageDrowseActivity
import com.zhudong.tianyuekotlin.utils.StatusBarUtil
import com.zhudong.tianyuekotlin.widget.SimpleMultiStateView
import kotlinx.android.synthetic.main.activity_image_browse.*
import java.io.Serializable

/**
 * Created by zhudong on 2018/6/7
 */
class ImageBrowseActivity : BaseImageDrowseActivity<BaseContract.BasePresenter>() {
    private var imageUrls: List<String>? = null
    private var selectedIndex: Int = 0

    companion object {
        fun launch(context: Activity, urls: List<String>, selectedIndex: Int) {
            val intent = Intent(context, ImageBrowseActivity::class.java)
            intent.putExtra("urls", urls as Serializable)
            intent.putExtra("selectedIndex", selectedIndex)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_image_browse

    override fun getSimpleMultiStateView(): SimpleMultiStateView? = null

    override fun initInjector(appComponent: ApplicationComponent) {}

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, android.R.color.black))
        initView(rl_ImageBrowse, swipeLayout, viewPager)
    }

    override fun initData() {
        intent.extras?.let {
            imageUrls = intent.extras.getSerializable("urls") as List<String>
            selectedIndex = intent.extras.getInt("selectedIndex")
            imageUrls?.let {
                initViewPager(this, it)
                viewPager.currentItem = selectedIndex
                tvPage.text = (selectedIndex + 1).toString() + " / " + it.size
            }
        }
    }

    /**
     * 图片点击回调
     */
    override fun onPhotoTap() {
        super.onPhotoTap()
        finish()
    }

    /**
     * ViewPager 滑动时更新文本内容
     *
     * @param position 下标
     */
    override fun setTvInfo(position: Int) {
        super.setTvInfo(position)
        tvPage.text = (position + 1).toString() + " / " + imageUrls?.size
    }

}