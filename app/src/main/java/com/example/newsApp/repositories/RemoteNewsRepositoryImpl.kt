package com.example.newsApp.repositories

import com.example.newsApp.datasources.RemoteNewsDataSource
import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow

internal class RemoteNewsRepositoryImpl(private val dataSource: RemoteNewsDataSource): RemoteNewsRepository {
    override fun getNewsArticles(): Flow<List<NewsArticle>> {
        return dataSource.getArticles()
    }
}