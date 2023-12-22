package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.favoritesNews.FavoritesNewsContract
import com.example.breakingnews.main.MainContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadFavoritesNewsUseCase(private val db: NewsDatabase,private val viewInterface: FavoritesNewsContract.ViewInterface) {
    suspend fun execute() {
        try {
            val news = db.newsDao().getAllNews()
            withContext(Dispatchers.Main) {
                viewInterface.setNewsList(news)
            }
        } catch (e: Exception) {
            viewInterface.showToast(e.toString())
        }
    }
}