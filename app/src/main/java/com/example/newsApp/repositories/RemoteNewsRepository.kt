package com.example.newsApp.repositories

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow

interface RemoteNewsRepository {
    fun getNewsArticles(): Flow<List<NewsArticle>>
}