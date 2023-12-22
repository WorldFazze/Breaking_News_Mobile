package com.example.breakingnews.newsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.useCase.AddToFavoritesUseCase
import com.example.breakingnews.domain.useCase.LoadImageUseCase
import com.example.breakingnews.domain.useCase.LoadNewsUseCase
import com.example.breakingnews.domain.useCase.SearchNewsUseCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDetailPresenter(
    private var viewInterface: NewsDetailContract.ViewInterface,
    private var db: NewsDatabase,
) : NewsDetailContract.PresenterInterface{
    private val addToFavoritesUseCase = AddToFavoritesUseCase(db, viewInterface)
    private val loadImageUseCase = LoadImageUseCase()

    override fun loadImage(imageUrl: String?, image: ImageView) {
        loadImageUseCase.execute(imageUrl, image)
    }

    override fun loadExtra(intent: Intent): News? {
        return intent.getParcelableExtra("newsItem")
    }

    override fun onAddToFavoritesClick(intent: Intent) {
        GlobalScope.launch {
            addToFavoritesUseCase.execute(loadExtra(intent))
        }
    }
}
