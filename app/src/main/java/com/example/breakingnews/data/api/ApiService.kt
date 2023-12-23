package com.example.breakingnews.data.api

import com.example.breakingnews.domain.models.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("news?apikey=pub_22135719321fe21bb31bff8dfacd35f416823&language=ru")
    suspend fun getNews(): NewsResponse

    @GET("news?apikey=pub_22135719321fe21bb31bff8dfacd35f416823&language=ru")
    suspend fun searchNews(@Query("q") query: String): NewsResponse

}