package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClearFavoritesNewsUseCase(private val db: NewsDatabase, private val viewInterface: MainContract.ViewInterface) {
    suspend fun execute() {
        try {
            db.newsDao().deleteNews()
        } catch (e: Exception) {
            viewInterface.showToast(e.toString())
        }
    }
}