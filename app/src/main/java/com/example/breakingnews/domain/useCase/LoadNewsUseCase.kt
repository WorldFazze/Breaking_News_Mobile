package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.api.ApiService
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadNewsUseCase(private val apiService: ApiService, private val viewInterface: MainContract.ViewInterface) {

    suspend fun execute() {
        try {
            val response: NewsResponse = apiService.getNews()
            val news = response.results
            viewInterface.setNewsList(news)
            withContext(Dispatchers.Main) {
                viewInterface.displayNews(news)
            }
        } catch (e: Exception) {
            viewInterface.showToast(e.toString())
        }
    }
}