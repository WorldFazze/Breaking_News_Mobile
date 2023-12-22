package com.example.breakingnews.newsDetail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.example.breakingnews.db.News
import com.example.breakingnews.models.NewsItem

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