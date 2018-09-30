package com.zhudong.tianyuekotlin.net

import com.zhudong.tianyuekotlin.bean.FreshNewsArticleBean
import com.zhudong.tianyuekotlin.bean.FreshNewsBean
import com.zhudong.tianyuekotlin.bean.JdDetailBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by zhudong on 2018/6/4
 */
interface JanDanApiService {
    @GET
    fun getFreshNews(@Url url: String, @Query("oxwlxojflwblxbsapi") oxwlxojflwblxbsapi: String,
                     @Query("include") include: String,
                     @Query("page") page: Int,
                     @Query("custom_fields") custom_fields: String,
                     @Query("dev") dev: String
    ): Observable<FreshNewsBean>


    @GET
    fun getDetailData(@Url url: String, @Query("oxwlxojflwblxbsapi") oxwlxojflwblxbsapi: String,
                      @Query("page") page: Int
    ): Observable<JdDetailBean>

    @GET
    fun getFreshNewsArticle(@Url url: String, @Query("oxwlxojflwblxbsapi") oxwlxojflwblxbsapi: String,
                            @Query("include") include: String,
                            @Query("id") id: Int
    ): Observable<FreshNewsArticleBean>
}