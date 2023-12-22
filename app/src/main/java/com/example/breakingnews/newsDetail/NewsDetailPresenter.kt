package com.example.breakingnews.newsDetail

import android.content.Intent
import android.widget.ImageView
import com.example.breakingnews.db.News
import com.example.breakingnews.db.NewsDatabase
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.models.NewsItem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDetailPresenter(
    private var viewInterface: NewsDetailContract.ViewInterface,
    private var db: NewsDatabase
) : NewsDetailContract.PresenterInterface{
    override fun loadImage(imageUrl: String?, image: ImageView) {
        Picasso.get().load(imageUrl).into(image)
    }

    override fun loadExtra(intent: Intent): News? {
        return intent.getParcelableExtra("newsItem")
    }

    override fun onAddToFavoritesClick(intent: Intent) {
        GlobalScope.launch {
            val newsItem = loadExtra(intent)
            val news = News(
                creator = (newsItem?.creator?.get(0) ?: "Автор не указан").toString(),
                content = newsItem?.content,
                pubDate = newsItem?.pubDate,
                source_id = newsItem?.source_id,
                title = newsItem?.title,
                image_url = newsItem?.image_url
            )
            db.newsDao().insertNews(news)
            viewInterface.showToast("Новость добавлена в избранные!")
        }
    }

}
