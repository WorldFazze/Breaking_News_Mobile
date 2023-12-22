package com.example.breakingnews.favoritesNews

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.useCase.LoadFavoritesNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesNewsPresenter (
    private var viewInterface: FavoritesNewsContract.ViewInterface,
    private var db: NewsDatabase
) : FavoritesNewsContract.PresenterInterface {
    private val TAG = "FavoritesNewsPresenter"
    private val loadFavoritesNewsUseCase = LoadFavoritesNewsUseCase(db, viewInterface)

    override fun loadFavoritesNews() {
        GlobalScope.launch(Dispatchers.IO) {
            loadFavoritesNewsUseCase.execute()
        }
    }
}