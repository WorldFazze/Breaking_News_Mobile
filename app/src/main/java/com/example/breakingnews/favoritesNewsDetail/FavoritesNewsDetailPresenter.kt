package com.example.breakingnews.favoritesNewsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.data.model.News
import com.example.breakingnews.domain.useCase.LoadImageUseCase
import com.squareup.picasso.Picasso

class FavoritesNewsDetailPresenter(
    private var viewInterface: FavoritesNewsDetailContract.ViewInterface,
) : FavoritesNewsDetailContract.PresenterInterface {
    private val loadImageUseCase = LoadImageUseCase()

    override fun loadImage(imageUrl: String?, image: ImageView) {
        loadImageUseCase.execute(imageUrl,image)
    }

    override fun loadExtra(intent: Intent): News? {
        return intent.getParcelableExtra("newsItem")
    }
}