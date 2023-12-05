package com.example.newsApp.repositories

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NewsRepository {
    fun getNewsArticle(id: UUID?): Flow<NewsArticle?>
    fun getNewsArticles(): Flow<List<NewsArticle>>
    suspend fun upsert(newsArticle: NewsArticle)
    suspend fun delete(id: UUID)
}