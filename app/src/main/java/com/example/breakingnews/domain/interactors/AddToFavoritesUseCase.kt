package com.example.breakingnews.domain.interactors

import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.repository.ModelRepository
import com.example.breakingnews.presentation.newsDetail.NewsDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddToFavoritesUseCase(
    private val db: NewsDatabase,
    private val modelRepository: ModelRepository,
    private val viewInterface: NewsDetailContract.ViewInterface,
) {

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
            modelRepository.insertNews(db, news)
            withContext(Dispatchers.Main) {
                viewInterface.showToast("Новость успешно добавлена в избранные")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                viewInterface.showToast(e.toString())
            }
        }
    }
}