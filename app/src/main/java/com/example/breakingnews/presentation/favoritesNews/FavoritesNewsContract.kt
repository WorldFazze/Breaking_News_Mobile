package com.example.breakingnews.presentation.favoritesNews

import com.example.breakingnews.data.model.News

class FavoritesNewsContract {

    interface PresenterInterface {
        fun loadFavoritesNews()
    }

    interface ViewInterface {
        fun setNewsList(news: List<News>)
        fun showToast(string: String)
    }
}