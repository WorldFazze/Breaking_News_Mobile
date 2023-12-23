package com.example.breakingnews.presentation.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.breakingnews.presentation.favoritesNews.FavoritesNewsActivity
import com.example.breakingnews.R
import com.example.breakingnews.presentation.adapters.NewsAdapter
import com.example.breakingnews.databinding.ActivityMainBinding
import com.example.breakingnews.data.model.NewsDatabase
import com.example.breakingnews.domain.models.NewsItem

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    companion object {
        lateinit var db: NewsDatabase
        lateinit var binding: ActivityMainBinding
    }

    private lateinit var news: List<NewsItem>
    private lateinit var orientationEventListener: OrientationEventListener
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsList: RecyclerView
    private lateinit var mainPresenter: MainContract.PresenterInterface

    private fun setupPresenter() {
        mainPresenter = MainPresenter(this, db)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        initializeDatabase()
        setupPresenter()

        savedInstanceState?.let { mainPresenter.restoreInstanceState(it) } ?: mainPresenter.loadNews()

        setListeners()
    }

    private fun initializeViews() {
        newsList = binding.NewsList
        newsAdapter = NewsAdapter(this, emptyList())
        newsList.adapter = newsAdapter
        newsList.layoutManager = LinearLayoutManager(this)
    }

    private fun initializeDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java, "my-db"
        ).build()
    }

    private fun setListeners() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainPresenter.performSearch(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    mainPresenter.loadNews()
                }
                return true
            }
        })

        orientationEventListener = createOrientationEventListener()
        orientationEventListener.enable()
    }

    private fun createOrientationEventListener(): OrientationEventListener {
        return object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }
                val newOrientation = when (windowManager.defaultDisplay.rotation) {
                    Surface.ROTATION_0, Surface.ROTATION_180 -> Configuration.ORIENTATION_PORTRAIT
                    Surface.ROTATION_90, Surface.ROTATION_270 -> Configuration.ORIENTATION_LANDSCAPE
                    else -> Configuration.ORIENTATION_UNDEFINED
                }
                handleOrientationChange(newOrientation)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fvButton -> {
                startFavoritesActivity()
            }
            R.id.deleteFavorites -> {
                mainPresenter.deleteFavorites()
            }
        }
        return true
    }

    private fun startFavoritesActivity() {
        val intent = Intent(this, FavoritesNewsActivity::class.java)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::news.isInitialized) {
            outState.putParcelableArrayList("newsList", ArrayList(news))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orientationEventListener.disable()
    }

    private fun handleOrientationChange(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.detail.visibility = View.GONE
        }
    }

    override fun displayNews(news: List<NewsItem>) {
        newsAdapter = NewsAdapter(this, news)
        binding.NewsList.adapter = newsAdapter
    }

    override fun setEmptyList() {
        newsAdapter = NewsAdapter(this, emptyList())
    }

    override fun setNewsList(newsList: List<NewsItem>) {
        news = newsList
    }

    override fun showToast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }
}
