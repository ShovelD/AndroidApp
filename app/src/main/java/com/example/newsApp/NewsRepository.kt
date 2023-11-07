package com.example.newsApp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

interface NewsRepository {
    fun getNewsArticle(id:UUID?): Flow<NewsArticle?>
    fun getNewsArticles():Flow<List<NewsArticle?>>
    suspend fun upsert(newsArticle: NewsArticle)
    suspend fun delete(id: UUID)
}
object NewsRepositoryImpl: NewsRepository {

    private val dataSource: NewsDataSource = InMemoryNewsDataSource
    override fun getNewsArticle(id: UUID?): Flow<NewsArticle?> {
        if (id == null) return flowOf(null)
        return dataSource.getNewsArticle(id)
    }

    override fun getNewsArticles(): Flow<List<NewsArticle?>> {
        return dataSource.getNewsArticles()
    }

    override suspend fun upsert(newsArticle: NewsArticle) {
        dataSource.upsert(newsArticle)
    }

    override suspend fun delete(id: UUID) {
            dataSource.delete(id)
    }
}
sealed interface HomeState{
    data object Loading: HomeState
    data object Empty: HomeState
    data class DisplayingNewsArticles(val newsArticles: List<NewsArticle?>): HomeState
    data class Error(val e:Exception): HomeState
}