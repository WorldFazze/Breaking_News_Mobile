package com.example.breakingnews.data.repository

import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.repository.ModelRepository

class ModelRepositoryImpl : ModelRepository {

    override fun getAllNews(db: NewsDatabase): List<News> {
        return db.newsDao().getAllNews()
    }

    override fun insertNews(db: NewsDatabase, news: News) {
        db.newsDao().insertNews(news = news)
    }

    override fun deleteNews(db: NewsDatabase) {
        db.newsDao().deleteNews()
    }

}