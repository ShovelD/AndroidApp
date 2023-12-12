package com.example.newsApp.repositories

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteNewsRepository {
    fun getNewsArticles(): Flow<List<NewsArticle>>
    fun getSingleArticle(id:UUID?): Flow<NewsArticle?>
}