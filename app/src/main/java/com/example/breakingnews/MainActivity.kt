package com.example.breakingnews


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingnews.adapters.NewsAdapter
import com.example.breakingnews.api.Instance
import com.example.breakingnews.databinding.ActivityMainBinding
import com.example.breakingnews.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

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

        GlobalScope.launch {
            val response: NewsResponse = apiService.getNews()
            val news = response.results
            withContext(Dispatchers.Main) {
                newsAdapter = NewsAdapter(context, news)
                binding.NewsList.adapter = newsAdapter
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
                    val news = response.results
                    withContext(Dispatchers.Main) {
                        newsAdapter = NewsAdapter(context,news)
                        binding.NewsList.adapter = newsAdapter
                    }
                }
            }

        })
    }
}