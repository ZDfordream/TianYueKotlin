package com.zhudong.tianyuekotlin.ui.news.presenter

import com.zhudong.tianyuekotlin.MyApp
import com.zhudong.tianyuekotlin.R
import com.zhudong.tianyuekotlin.bean.Channel
import com.zhudong.tianyuekotlin.database.ChannelDao
import com.zhudong.tianyuekotlin.ui.base.BasePresenter
import com.zhudong.tianyuekotlin.ui.news.contract.NewsContract
import org.litepal.crud.DataSupport
import java.util.*
import javax.inject.Inject


/**
 * Created by zhudong on 2018/6/7
 */
class NewsPresenter @Inject
constructor() : BasePresenter<NewsContract.View>(), NewsContract.Presenter {

    /**
     * 获取频道列表
     */
    override fun getChannel() {
        var channelList: MutableList<Channel>
        val myChannels = ArrayList<Channel>()
        val otherChannels = ArrayList<Channel>()
        channelList = ChannelDao.channels as MutableList<Channel>
        when {
            channelList.size < 1 -> {
                val channelName = MyApp.instance.resources.getStringArray(R.array.news_channel)
                val channelId = MyApp.instance.resources.getStringArray(R.array.news_channel_id)
                val channels = ArrayList<Channel>()

                for (i in channelName.indices) {
                    val channel = Channel()
                    channel.channelId = channelId[i].toString()
                    channel.channelName = channelName[i].toString()
                    channel.channelType = if (i < 1) 1 else 0   //默认第一个频道不可移除
                    channel.isChannelSelect = i < channelName.size - 3  //默认后三个频道为未选中状态
                    when {
                        i < channelId.size - 3 -> myChannels.add(channel)
                        else -> otherChannels.add(channel)
                    }
                    channels.add(channel)
                }
                //存入数据库
                DataSupport.saveAllAsync(channels)
                channelList = ArrayList()
                channelList.addAll(channels)
            }
            else -> {
                channelList = ChannelDao.channels as MutableList<Channel>
                val iterator = channelList.iterator()
                while (iterator.hasNext()) {
                    val channel = iterator.next()
                    if (!channel.isChannelSelect) {
                        otherChannels.add(channel)
                        iterator.remove()
                    }
                }
                myChannels.addAll(channelList)
            }
        }

        mView?.loadData(myChannels, otherChannels)
    }
}
