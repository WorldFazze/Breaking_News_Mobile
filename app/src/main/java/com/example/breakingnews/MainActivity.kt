package com.example.breakingnews


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
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
    }
    lateinit var news: List<NewsItem>
    lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsList = binding.NewsList
        newsAdapter = NewsAdapter(context, emptyList())
        val apiService = Instance.api
        newsList.adapter =newsAdapter
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

}