package com.example.breakingnews.db

import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News)

    @Query("DELETE FROM news")
    fun deleteNews()
}