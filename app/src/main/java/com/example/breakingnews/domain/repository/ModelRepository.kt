package com.example.breakingnews.domain.repository

import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase

interface ModelRepository {

    fun getAllNews(db: NewsDatabase): List<News>

    fun insertNews(db: NewsDatabase,news: News)

    fun deleteNews(db: NewsDatabase)
}