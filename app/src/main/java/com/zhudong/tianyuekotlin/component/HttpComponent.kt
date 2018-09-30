package com.zhudong.tianyuekotlin.component


import com.zhudong.tianyuekotlin.ui.jandan.JdDetailFragment
import com.zhudong.tianyuekotlin.ui.news.ArticleReadActivity
import com.zhudong.tianyuekotlin.ui.news.ImageBrowseActivity
import com.zhudong.tianyuekotlin.ui.news.NewsFragment
import com.zhudong.tianyuekotlin.ui.video.DetailFragment
import com.zhudong.tianyuekotlin.ui.video.VideoFragment
import dagger.Component

@Component(dependencies = [(ApplicationComponent::class)])
interface HttpComponent {

    fun inject(videoFragment: VideoFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(imageBrowseActivity: ImageBrowseActivity)

    fun inject(detailFragment: com.zhudong.tianyuekotlin.ui.news.DetailFragment)

    fun inject(articleReadActivity: ArticleReadActivity)

    fun inject(newsFragment: NewsFragment)

    fun inject(jdDetailFragment: JdDetailFragment)

}