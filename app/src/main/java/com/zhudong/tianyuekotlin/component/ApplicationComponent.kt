package com.zhudong.tianyuekotlin.component

import android.content.Context
import com.zhudong.tianyuekotlin.MyApp
import com.zhudong.tianyuekotlin.module.ApplicationModule
import com.zhudong.tianyuekotlin.module.HttpModule
import com.zhudong.tianyuekotlin.net.JanDanApi
import com.zhudong.tianyuekotlin.net.NewsApi
import dagger.Component

/**
 * Created by zhudong on 2018/5/31
 */
@Component(modules = [(ApplicationModule::class),(HttpModule::class)])
interface ApplicationComponent {
    val application: MyApp

    val context: Context

    fun getNetEaseApi(): NewsApi

    fun getJanDanApi(): JanDanApi
}