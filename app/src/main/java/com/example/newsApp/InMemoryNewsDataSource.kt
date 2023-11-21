package com.example.newsApp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID
import com.example.newsApp.HomeViewModel.Companion.DefaultNewsArticles
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

object InMemoryNewsDataSource:NewsDataSource {

    private val news = DefaultNewsArticles.associateBy { it.id }.toMutableMap()
    private val _newsFlow = MutableSharedFlow<Map<UUID,NewsArticle>>(1)
    override fun getNewsArticles(): Flow<List<NewsArticle>> = _newsFlow
        .asSharedFlow()
        .map{it.values.toList()}
        .onStart {
            delay(1000L)
            emit(news.values.toList())
        }

    override fun getNewsArticle(id: UUID): Flow<NewsArticle?> = _newsFlow
        .asSharedFlow()
        .map {it[id] }
        .onStart {
            delay(1000L)
            emit(news[id])
        }
    override suspend fun upsert(newsArticle: NewsArticle) {
        news.values.add(newsArticle)
        _newsFlow.emit(news)
    }

    override suspend fun delete(id: UUID) {
        news.remove(id)
        _newsFlow.emit(news)
    }
}