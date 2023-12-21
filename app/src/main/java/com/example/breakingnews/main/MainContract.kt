package com.example.breakingnews.main

import com.example.breakingnews.models.NewsItem

class MainContract {
    interface PresenterInterface {
        fun loadNews()
        fun deleteFavorites()
        fun performSearch(query: String)
    }

    interface ViewInterface {
        fun displayNews(news: List<NewsItem>)
        fun setEmptyList()
        fun setNewsList(newsList: List<NewsItem>)
    }

}