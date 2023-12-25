package com.example.breakingnews.domain.interactors

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.repository.ModelRepository
import com.example.breakingnews.presentation.favoritesNews.FavoritesNewsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadFavoritesNewsUseCase(
    private val db: NewsDatabase,
    private val modelRepository: ModelRepository,
    private val viewInterface: FavoritesNewsContract.ViewInterface
) {
    suspend fun execute() {
        try {
            val news = modelRepository.getAllNews(db)
            withContext(Dispatchers.Main) {
                viewInterface.setNewsList(news)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                viewInterface.showToast(e.toString())
            }
        }
    }
}