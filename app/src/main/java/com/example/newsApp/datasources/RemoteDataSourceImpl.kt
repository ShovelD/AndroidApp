package com.example.newsApp.datasources

import com.example.newsApp.viewmodels.NewsArticle
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Date


@Serializable
data class ArticlesList(
    val status:String,
    val totalResults: Int,
    val articles:List<Article>
)
@Serializable
data class Article(
    val source:Source?,
    val author: String?,
    val title:String?,
    val description:String?,
    val url:String?,
    val urlToImage:String?,
    val publishedAt:String?,
    val content:String?
)
@Serializable
data class Source(
    val id:String?,
    val name:String?
)
internal class RemoteDataSourceImpl(private val client:HttpClient):RemoteNewsDataSource {
    override fun getArticles(): Flow<List<NewsArticle>> = flow{
        val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=f482bb95f0b540b6955a9ce07862a55a"
        val response = client.get{
            url(url)
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization,"Bearer token")
        }
        val newsList = Json.decodeFromString<ArticlesList>(String(response.readBytes()))
        emit(newsList.articles.map {
            val title = it.title ?: ""
            val author = it.author ?: ""
            val description =it.description?:""
            NewsArticle(
                title,
                author,
                description,
                Date(),
                false,
                listOf("")
            )
        })
    }
    internal companion object{
        const val baseUri = "GET https://newsapi.org/v2/top-headlines?country=us"
        const val ApiKey = "f482bb95f0b540b6955a9ce07862a55a"
    }
}
