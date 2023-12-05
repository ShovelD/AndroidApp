package com.example.newsApp.repositories

import com.example.newsApp.datasources.NewsDataSourceImpl
import com.example.newsApp.datasources.NewsDataSource
import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID


internal class NewsRepositoryImpl(private val dataSource : NewsDataSource): NewsRepository {

    override fun getNewsArticle(id: UUID?): Flow<NewsArticle?> {
        if (id == null) return flowOf(null)
        return dataSource.getArticle(id)
    }

    override fun getNewsArticles(): Flow<List<NewsArticle>> {
        return dataSource.getNews()
    }

    override suspend fun upsert(newsArticle: NewsArticle) {
        dataSource.upsert(newsArticle)
    }

    override suspend fun delete(id: UUID) {
            dataSource.delete(id)
    }
}
