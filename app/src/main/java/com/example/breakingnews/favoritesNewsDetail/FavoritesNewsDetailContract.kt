package com.example.breakingnews.favoritesNewsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.db.News

class FavoritesNewsDetailContract {

    interface PresenterInterface {
        fun loadImage(imageUrl: String?, image: ImageView)
        fun loadExtra(intent: Intent)
    }

    interface ViewInterface {
        fun displayNewsDetails(newsItem: News?)
    }
}