package com.zhudong.tianyuekotlin.net

import com.zhudong.tianyuekotlin.bean.NewsDetail


/**
 * Created by zhudong on 2018/5/31
 */
object NewsUtils {

    //顶部banner新闻
    val TYPE_BANNER = "focus"
    //置顶新闻
    val TYPE_TOP = "top"
    //常规新闻
    val TYPE_List = "list"

    //文章类型
    val TYPE_DOC = "doc"
    //广告类型
    val TYPE_ADVERT = "advert"
    //图片类型
    val TYPE_SLIDE = "slide"
    //视频类型
    val TYPE_PHVIDEO = "phvideo"

    //显示形式单图
    val VIEW_TITLEIMG = "titleimg"
    //显示形式多图
    val VIEW_SLIDEIMG = "slideimg"
    //显示形式多图
    val VIEW_LONGIMG = "longimg"

    fun isBannerNews(detail: NewsDetail): Boolean = detail.type == TYPE_BANNER

    fun isTopNews(detail: NewsDetail): Boolean = detail.type == TYPE_TOP

    fun isListNews(detail: NewsDetail): Boolean = detail.type == TYPE_List

    fun isAvertNews(bean: NewsDetail.ItemBean): Boolean = bean.type == TYPE_ADVERT

}
