package com.example.breakingnews.presentation.main

import android.os.Bundle
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.data.repository.ModelRepositoryImpl
import com.example.breakingnews.data.repository.NewsRepositoryImpl
import com.example.breakingnews.domain.interactors.ClearFavoritesNewsUseCase
import com.example.breakingnews.domain.interactors.LoadNewsUseCase
import com.example.breakingnews.domain.interactors.SearchNewsUseCase
import com.example.breakingnews.domain.models.NewsItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var db: NewsDatabase
) : MainContract.PresenterInterface {
    private val TAG = "MainPresenter"
    private val newsRepository = NewsRepositoryImpl()
    private val modelRepository = ModelRepositoryImpl()
    private val loadNewsUseCase = LoadNewsUseCase(newsRepository,viewInterface)
    private val searchNewsUseCase = SearchNewsUseCase(newsRepository, viewInterface)
    private val clearFavoritesNewsUseCase = ClearFavoritesNewsUseCase(db, modelRepository, viewInterface)

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
            searchNewsUseCase.execute(query)
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