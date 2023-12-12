package com.example.newsApp.datasources

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteNewsDataSource {
    fun getArticles(): Flow<List<NewsArticle>>
    fun getSingleArticle(id:UUID?):Flow<NewsArticle?>
}