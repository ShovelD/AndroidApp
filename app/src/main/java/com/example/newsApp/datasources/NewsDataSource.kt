package com.example.newsApp.datasources

import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NewsDataSource {
    fun getNewsArticles(): Flow<List<NewsArticle>>
    fun getNewsArticle(id:UUID): Flow<NewsArticle?>

    suspend fun upsert(newsArticle: NewsArticle)
    suspend fun delete(id: UUID)
}