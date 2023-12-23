package com.example.breakingnews.data.repository

import com.example.breakingnews.data.api.Instance
import com.example.breakingnews.domain.models.NewsResponse
import com.example.breakingnews.domain.repository.NewsRepository

class NewsRepositoryImpl : NewsRepository {

    private val apiService = Instance.api

    override suspend fun getNews(): NewsResponse {
        return apiService.getNews()
    }

    override suspend fun searchNews(query: String): NewsResponse {
        return apiService.searchNews(query)
    }
}