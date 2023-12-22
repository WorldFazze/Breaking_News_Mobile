package com.example.breakingnews.domain.useCase

import android.widget.ImageView
import com.squareup.picasso.Picasso

class LoadImageUseCase() {

    fun execute(imageUrl: String?, image: ImageView) {
        Picasso.get().load(imageUrl).into(image)
    }
}