package com.example.newsApp.database.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsApp.database.daos.NewsDao
import com.example.newsApp.database.entities.NewsEntity

@Database(entities = [NewsEntity::class],version = 1)
internal abstract class AppDataBase: RoomDatabase(){
    abstract fun newsDao():NewsDao
}

internal fun AppDataBase(context:Context) = Room.databaseBuilder(
    context,
    AppDataBase::class.java,
    "news_database"
).fallbackToDestructiveMigration()
    .build()