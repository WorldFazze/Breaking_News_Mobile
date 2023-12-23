package com.example.breakingnews.presentation.favoritesNewsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.data.model.News

class FavoritesNewsDetailContract {

    interface PresenterInterface {
        fun loadImage(imageUrl: String?, image: ImageView)
        fun loadExtra(intent: Intent): News?
    }

    interface ViewInterface {
        fun displayNewsDetails(newsItem: News?)
    }
}