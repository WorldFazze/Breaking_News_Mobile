package com.example.breakingnews.newsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.data.model.News

class NewsDetailContract {
    interface PresenterInterface {
        fun loadImage(imageUrl: String?, image: ImageView)
        fun loadExtra(intent: Intent): News?
        fun onAddToFavoritesClick(intent: Intent)
    }

    interface ViewInterface {
        fun showToast(string: String)
    }
}