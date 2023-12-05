package com.example.newsApp.datasources

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow

interface RemoteNewsDataSource {
    fun getArticles(): Flow<List<NewsArticle>>
}