package com.example.breakingnews.domain.repository

import com.example.breakingnews.domain.models.NewsResponse

interface NewsRepository {
    suspend fun getNews(): NewsResponse

    suspend fun searchNews(query: String): NewsResponse
}