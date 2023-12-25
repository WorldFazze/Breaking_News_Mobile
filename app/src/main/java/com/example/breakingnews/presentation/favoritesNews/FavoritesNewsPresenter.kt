package com.example.breakingnews.presentation.favoritesNews

import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.data.repository.ModelRepositoryImpl
import com.example.breakingnews.domain.interactors.LoadFavoritesNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesNewsPresenter (
    private var viewInterface: FavoritesNewsContract.ViewInterface,
    private var db: NewsDatabase
) : FavoritesNewsContract.PresenterInterface {
    private val TAG = "FavoritesNewsPresenter"
    private val modelRepository = ModelRepositoryImpl()
    private val loadFavoritesNewsUseCase = LoadFavoritesNewsUseCase(db,modelRepository, viewInterface)

    override fun loadFavoritesNews() {
        GlobalScope.launch(Dispatchers.IO) {
            loadFavoritesNewsUseCase.execute()
        }
    }
}