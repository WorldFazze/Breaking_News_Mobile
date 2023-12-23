package com.example.breakingnews.presentation.newsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.data.model.News
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.data.repository.ModelRepositoryImpl
import com.example.breakingnews.domain.useCase.AddToFavoritesUseCase
import com.example.breakingnews.domain.useCase.LoadImageUseCase
import com.example.breakingnews.domain.models.NewsItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDetailPresenter(
    private var viewInterface: NewsDetailContract.ViewInterface,
    private var db: NewsDatabase,
) : NewsDetailContract.PresenterInterface {
    private val modelRepository = ModelRepositoryImpl()

    private val addToFavoritesUseCase = AddToFavoritesUseCase(db,modelRepository, viewInterface)
    private val loadImageUseCase = LoadImageUseCase()

    override fun loadImage(imageUrl: String?, image: ImageView) {
        loadImageUseCase.execute(imageUrl, image)
    }

    override fun loadExtra(intent: Intent): News {
        val newsItem = intent.getParcelableExtra<NewsItem>("newsItem")
        return News(
            creator = (newsItem?.creator?.get(0) ?: "Автор не указан").toString(),
            content = newsItem?.content,
            pubDate = newsItem?.pubDate,
            source_id = newsItem?.source_id,
            title = newsItem?.title,
            image_url = newsItem?.image_url
        )
    }

    override fun onAddToFavoritesClick(intent: Intent) {
        GlobalScope.launch {
            addToFavoritesUseCase.execute(loadExtra(intent))
        }
    }
}

