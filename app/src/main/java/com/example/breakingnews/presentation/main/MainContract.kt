package com.example.breakingnews.presentation.main

import android.os.Bundle
import com.example.breakingnews.domain.models.NewsItem

class MainContract {
    interface PresenterInterface {
        fun loadNews()
        fun deleteFavorites()
        fun performSearch(query: String)
        fun restoreInstanceState(savedInstanceState: Bundle)
    }

    interface ViewInterface {
        fun displayNews(news: List<NewsItem>)
        fun setEmptyList()
        fun setNewsList(newsList: List<NewsItem>)
        fun showToast(string: String)
    }

}