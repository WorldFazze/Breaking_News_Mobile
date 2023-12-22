package com.example.breakingnews.favoritesNews

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