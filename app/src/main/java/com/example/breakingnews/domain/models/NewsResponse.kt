package com.example.breakingnews.domain.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<NewsItem>,
    val nextPage: String?
)