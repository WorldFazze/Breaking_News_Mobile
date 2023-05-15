package com.example.breakingnews.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}