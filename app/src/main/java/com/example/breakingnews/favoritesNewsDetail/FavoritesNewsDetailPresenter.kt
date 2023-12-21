package com.example.breakingnews.favoritesNewsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.db.News
import com.squareup.picasso.Picasso

class FavoritesNewsDetailPresenter(
    private var viewInterface: FavoritesNewsDetailContract.ViewInterface,
) : FavoritesNewsDetailContract.PresenterInterface {

    override fun loadImage(imageUrl: String?, image: ImageView) {
        Picasso.get().load(imageUrl).into(image)
    }

    override fun loadExtra(intent: Intent) {
        val newsItem = intent.getParcelableExtra<News>("newsItem")
        viewInterface.displayNewsDetails(newsItem)
    }
}