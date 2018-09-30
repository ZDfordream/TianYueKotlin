package com.zhudong.tianyuekotlin.module

import android.content.Context
import com.zhudong.tianyuekotlin.MyApp
import dagger.Module
import dagger.Provides

/**
 * Created by zhudong on 2018/5/31
 */
@Module
class ApplicationModule(private val mContext: Context) {

    @Provides
    internal fun provideApplication(): MyApp {
        return mContext.applicationContext as MyApp
    }

    @Provides
    internal fun provideContext(): Context {
        return mContext
    }
}