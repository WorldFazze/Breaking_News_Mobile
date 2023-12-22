package com.example.breakingnews.main

import android.os.Bundle
import com.example.breakingnews.data.api.Instance
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.useCase.ClearFavoritesNewsUseCase
import com.example.breakingnews.domain.useCase.LoadNewsUseCase
import com.example.breakingnews.domain.useCase.SearchNewsUseCase
import com.example.breakingnews.models.NewsItem
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var db: NewsDatabase
) : MainContract.PresenterInterface {
    private val TAG = "MainPresenter"
    private val apiService = Instance.api
    private val loadNewsUseCase = LoadNewsUseCase(apiService,viewInterface)
    private val searchNewsUseCase = SearchNewsUseCase(apiService, viewInterface)
    private val clearFavoritesNewsUseCase = ClearFavoritesNewsUseCase(db, viewInterface)

    override fun loadNews() {
        GlobalScope.launch {
            loadNewsUseCase.execute()
        }
    }

    override fun deleteFavorites() {
        GlobalScope.launch {
            clearFavoritesNewsUseCase.execute()
        }
    }

    override fun performSearch(query: String) {
        viewInterface.setEmptyList()
        GlobalScope.launch {
            val apiService = Instance.api
            val response: NewsResponse = apiService.searchNews(query)
            val news = response.results
            viewInterface.setNewsList(news)
            withContext(Dispatchers.Main) {
                viewInterface.displayNews(news)
            }
        }
    }

    override fun restoreInstanceState(savedInstanceState: Bundle) {
        val restoredList = savedInstanceState.getParcelableArrayList<NewsItem>("newsList")
        restoredList?.let {
            val news = it.toList()
            viewInterface.setNewsList(news)
            viewInterface.displayNews(news)
        }
    }
}