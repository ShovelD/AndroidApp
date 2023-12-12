package com.example.newsApp.repositories

import com.example.newsApp.datasources.RemoteNewsDataSource
import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class RemoteNewsRepositoryImpl(private val dataSource: RemoteNewsDataSource): RemoteNewsRepository {
    override fun getNewsArticles(): Flow<List<NewsArticle>> {
        return dataSource.getArticles()
    }

    override fun getSingleArticle(id: UUID?): Flow<NewsArticle?> {
        return dataSource.getSingleArticle(id)
    }
}