package com.example.breakingnews.domain.useCase

import com.example.breakingnews.data.repository.NewsRepositoryImpl
import com.example.breakingnews.presentation.main.MainContract
import com.example.breakingnews.domain.models.NewsResponse
import com.example.breakingnews.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchNewsUseCase(private val newsRepository: NewsRepository, private val viewInterface: MainContract.ViewInterface) {

    suspend fun execute(query: String) {
        try {
            viewInterface.setEmptyList()
            val response = newsRepository.searchNews(query)
            val news = response.results
            viewInterface.setNewsList(news)
            withContext(Dispatchers.Main) {
                viewInterface.displayNews(news)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                viewInterface.showToast(e.toString())
            }
        }
    }
}