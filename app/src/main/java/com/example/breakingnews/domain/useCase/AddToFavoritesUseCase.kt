package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.newsDetail.NewsDetailContract

class AddToFavoritesUseCase(private val db: NewsDatabase, private val viewInterface: NewsDetailContract.ViewInterface) {

    suspend fun execute(newsItem: News?) {
        try {
            val news = News(
                creator = (newsItem?.creator?.get(0) ?: "Автор не указан").toString(),
                content = newsItem?.content,
                pubDate = newsItem?.pubDate,
                source_id = newsItem?.source_id,
                title = newsItem?.title,
                image_url = newsItem?.image_url
            )
            db.newsDao().insertNews(news)
        } catch (e: Exception) {
            viewInterface.showToast(e.toString())
        }
    }
}