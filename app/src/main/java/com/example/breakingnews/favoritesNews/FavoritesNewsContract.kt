package com.example.breakingnews.favoritesNews

import com.example.breakingnews.db.News
import com.example.breakingnews.models.NewsItem

class FavoritesNewsContract {

    interface PresenterInterface {
        fun loadFavoritesNews()
    }

    interface ViewInterface {
        fun setNewsList(news: List<News>)
    }
}