package com.example.breakingnews.favoritesNews

import com.example.breakingnews.api.Instance
import com.example.breakingnews.db.NewsDatabase
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesNewsPresenter (
    private var viewInterface: FavoritesNewsContract.ViewInterface,
    private var db: NewsDatabase
) : FavoritesNewsContract.PresenterInterface {
    private val TAG = "FavoritesNewsPresenter"

    override fun loadFavoritesNews() {
        GlobalScope.launch(Dispatchers.IO) {
            val news = db.newsDao().getAllNews()
            withContext(Dispatchers.Main) {
                viewInterface.setNewsList(news)
            }
        }
    }
}