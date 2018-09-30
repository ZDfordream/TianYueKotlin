package com.zhudong.tianyuekotlin

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager
import com.zhudong.tianyuekotlin.component.ApplicationComponent
import com.zhudong.tianyuekotlin.component.DaggerApplicationComponent
import com.zhudong.tianyuekotlin.module.ApplicationModule
import com.zhudong.tianyuekotlin.module.HttpModule
import org.litepal.LitePal
import org.litepal.LitePalApplication
import kotlin.properties.Delegates

/**
 * Created by zhudong on 2018/5/31
 */
class MyApp : LitePalApplication() {
    companion object {
        var instance: MyApp by Delegates.notNull()
        var mApplicationComponent: ApplicationComponent by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .httpModule(HttpModule())
                .build()
        //初始化数据库
        LitePal.initialize(this)
        //初始化侧滑返回组件
        BGASwipeBackManager.getInstance().init(this)
    }
}