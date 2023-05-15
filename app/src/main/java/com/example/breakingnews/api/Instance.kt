package com.example.breakingnews.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Instance {
    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}