package com.example.breakingnews.main

import com.example.breakingnews.api.Instance
import com.example.breakingnews.db.NewsDatabase
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

    override fun loadNews() {
        GlobalScope.launch {
            val apiService = Instance.api
            val response: NewsResponse = apiService.getNews()
            val news = response.results
            withContext(Dispatchers.Main) {
                viewInterface.displayNews(news)
            }
        }
    }

    override fun deleteFavorites() {
        GlobalScope.launch {
            db.newsDao().deleteNews()
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



}