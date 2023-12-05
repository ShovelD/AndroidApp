package com.example.newsApp.datasources

import androidx.room.TypeConverter
import com.example.newsApp.database.daos.NewsDao
import com.example.newsApp.database.entities.NewsEntity
import com.example.newsApp.viewmodels.HomeViewModel.Companion.DefaultNewsArticles
import com.example.newsApp.viewmodels.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import kotlin.text.split

@TypeConverter
fun fromTimeStamp(value:Long):Date{
    return Date(value)
}


fun transformToNews(article: NewsEntity?): NewsArticle {
    return if (article != null) NewsArticle(
        article.articleTitle,
        article.articleAuthor,
        article.articleDescription,
        fromTimeStamp(article.articlePublishingDate),
        article.isDraft,
        article.tags.split("<-->"),
        article.id
    ) else NewsArticle.getNotFoundArticle()
}

fun transformToNewsEntity(article: NewsArticle): NewsEntity {
    return NewsEntity(
        article.id,
        article.articleTitle,
        article.articleAuthor,
        article.articleDescription,
        article.articlePublishingDate.time,
        article.isDraft,
        article.tags.joinToString { "<-->" }
    )
}
internal class NewsDataSourceImpl(private val dao: NewsDao): NewsDataSource {

    private val news = DefaultNewsArticles.associateBy { it.id }.toMutableMap()
    private val _newsFlow = MutableSharedFlow<Map<UUID, NewsArticle>>(1)
    override fun getNews(): Flow<List<NewsArticle>> {
        return dao.getNews().map {
            it.map { newsEntity ->
                transformToNews(newsEntity)
            }
        }
    }

    override fun getArticle(id: UUID): Flow<NewsArticle?> {
        return dao.getNews(id).map { newsEntity -> transformToNews(newsEntity) }
    }
    override suspend fun upsert(newsArticle: NewsArticle) {
        dao.save(transformToNewsEntity(newsArticle))
    }

    override suspend fun delete(id: UUID) {
        dao.delete(id)
    }
}