package com.zhudong.tianyuekotlin.net
import android.support.annotation.StringDef
import com.zhudong.tianyuekotlin.bean.NewsArticleBean
import com.zhudong.tianyuekotlin.bean.NewsDetail
import com.zhudong.tianyuekotlin.bean.VideoChannelBean
import com.zhudong.tianyuekotlin.bean.VideoDetailBean
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import io.reactivex.Observable

/**
 * Created by zhudong on 2018/5/31
 */
class NewsApi(private val mService: NewsApiService) {
    companion object {
        const val ACTION_DEFAULT = "default"
        const val ACTION_DOWN = "down"
        const val ACTION_UP = "up"

        private var sInstance: NewsApi? = null

        fun getInstance(newsApiService: NewsApiService): NewsApi {
            if (sInstance == null)
                sInstance = NewsApi(newsApiService)
            return sInstance as NewsApi
        }

        @StringDef(ACTION_DEFAULT, ACTION_DOWN, ACTION_UP)
        @kotlin.annotation.Retention
        annotation class Actions
    }

    /**
     * 获取视频频道列表
     *
     * @return
     */
    val videoChannel: Observable<List<VideoChannelBean>>
        get() = mService.getVideoChannel(1)

    /**
     * 获取新闻详情
     *
     * @param id      频道ID值
     * @param action  用户操作方式
     *                1：下拉 down
     *                2：上拉 up
     *                3：默认 default
     *
     * @param pullNum 操作次数 累加
     * @return
     */
    fun getNewsDetail(id: String, @Actions action: String, pullNum: Int): Observable<List<NewsDetail>>
            = mService.getNewsDetail(id, action, pullNum)

    /**
     * 获取新闻文章详情
     *
     * @param aid 文章aid  此处baseurl可能不同，需要特殊处理
     *            aid 以 cmpp 开头则调用 getNewsArticleWithCmpp
     * @return
     */
    fun getNewsArticle(aid: String): Observable<NewsArticleBean> = when {
        aid.startsWith("sub") -> mService.getNewsArticleWithSub(aid)
        else -> mService.getNewsArticleWithCmpp(ApiConstants.sGetNewsArticleCmppApi + ApiConstants.sGetNewsArticleDocCmppApi, aid)
    }

    /**
     * 获取
     *
     * @param page
     * @param listType
     * @param typeId
     * @return
     */
    fun getVideoDetail(page: Int, listType: String, typeId: String): Observable<List<VideoDetailBean>>
            = mService.getVideoDetail(page, listType, typeId)
}