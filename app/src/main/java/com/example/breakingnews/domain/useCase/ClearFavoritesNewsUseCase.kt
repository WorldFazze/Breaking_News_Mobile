package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.data.repository.ModelRepositoryImpl
import com.example.breakingnews.domain.repository.ModelRepository
import com.example.breakingnews.presentation.main.MainContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClearFavoritesNewsUseCase(
    private val db: NewsDatabase,
    private val modelRepository: ModelRepository,
    private val viewInterface: MainContract.ViewInterface
) {
    suspend fun execute() {
        try {
            modelRepository.deleteNews(db)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                viewInterface.showToast(e.toString())
            }
        }
    }
}