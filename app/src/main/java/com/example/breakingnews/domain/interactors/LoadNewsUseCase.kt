package com.example.breakingnews.domain.interactors

import com.example.breakingnews.presentation.main.MainContract
import com.example.breakingnews.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadNewsUseCase(private val newsRepository: NewsRepository, private val viewInterface: MainContract.ViewInterface) {

    suspend fun execute() {
        try {
            val response = newsRepository.getNews()
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