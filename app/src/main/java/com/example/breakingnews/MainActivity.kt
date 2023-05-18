package com.example.breakingnews


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.breakingnews.adapters.NewsAdapter
import com.example.breakingnews.api.Instance
import com.example.breakingnews.databinding.ActivityMainBinding
import com.example.breakingnews.db.NewsDatabase
import com.example.breakingnews.models.NewsItem
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var db: NewsDatabase
        lateinit var binding: ActivityMainBinding
    }
    lateinit var news: List<NewsItem>
    private lateinit var orientationEventListener: OrientationEventListener
    private lateinit var newsAdapter: NewsAdapter
    lateinit var newsList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsList = binding.NewsList
        newsAdapter = NewsAdapter(context, emptyList())
        val apiService = Instance.api
        newsList.adapter = newsAdapter
        newsList.layoutManager = LinearLayoutManager(this)
        db = Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java, "my-db"
        ).build()
        GlobalScope.launch {
            if (savedInstanceState != null) {
                val restoredList = savedInstanceState.getParcelableArrayList<NewsItem>("newsList")
                if (restoredList != null) {
                    val newsList = restoredList.toList()
                    newsAdapter = NewsAdapter(applicationContext, newsList)
                    binding.NewsList.adapter = newsAdapter
                }

            } else {
                val response: NewsResponse = apiService.getNews()
                news = response.results
                withContext(Dispatchers.Main) {
                    newsAdapter = NewsAdapter(context, news)
                    binding.NewsList.adapter = newsAdapter
                }
            }
        }


        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                newsAdapter = NewsAdapter(context,emptyList())
                GlobalScope.launch {
                    val response: NewsResponse = apiService.searchNews(p0.toString())
                    news = response.results
                    withContext(Dispatchers.Main) {
                        newsAdapter = NewsAdapter(context,news)
                        binding.NewsList.adapter = newsAdapter
                    }
                }
            }

        })

        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                // Обрабатываем изменение ориентации экрана
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = windowManager.defaultDisplay.rotation
                val newOrientation = when (rotation) {
                    Surface.ROTATION_0 -> Configuration.ORIENTATION_PORTRAIT
                    Surface.ROTATION_90 -> Configuration.ORIENTATION_LANDSCAPE
                    Surface.ROTATION_180 -> Configuration.ORIENTATION_PORTRAIT
                    Surface.ROTATION_270 -> Configuration.ORIENTATION_LANDSCAPE
                    else -> Configuration.ORIENTATION_UNDEFINED
                }

                // Выполняем действия при изменении ориентации экрана
                handleOrientationChange(newOrientation)
            }
        }

        // Запускаем слушатель событий
        orientationEventListener.enable()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fvButton -> {
                val intent = Intent(this, FavoritesNewsActivity::class.java)
                this.startActivity(intent)
            }
            R.id.deleteFavorites -> {
                GlobalScope.launch {
                    db.newsDao().deleteNews()
                }
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList("newsList", ArrayList(news))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Отключаем слушатель событий при уничтожении активности
        orientationEventListener.disable()
    }

    private fun handleOrientationChange(orientation: Int) {
        // Пример: Блокируем ориентацию на портретную или ландшафтную, в зависимости от текущей ориентации
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.detail.visibility = View.GONE
        }
    }

}
