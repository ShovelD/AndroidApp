package com.example.newsApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID
data class NewsArticle(
    val articleTitle: String,
    val articleAuthor: String,
    val articleDescription: String,
    val articlePublishingDate: Date,
    val isDraft: Boolean,
    val tags: List<String>,
    val id: UUID = UUID.randomUUID()
)

class HomeViewModel : ViewModel() {
    val state: StateFlow<HomeState> =
        NewsRepositoryImpl.getNewsArticles()
            .map{data->
                when{
                    data.isEmpty()->HomeState.Empty
                    else->HomeState.DisplayingNewsArticles(data)
                }
            }.stateIn(viewModelScope,SharingStarted.Lazily,HomeState.Loading)

    suspend fun onClickRemoveArticle(newsArticle: NewsArticle) = NewsRepositoryImpl.delete(newsArticle.id)

     companion object {
         val DefaultNewsArticles = listOf(
            NewsArticle(
                "article1",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            ),
            NewsArticle(
                "article3",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            ),
             NewsArticle(
                 "article",
                 "author",
                 "description",
                 Date(),
                 true,
                 listOf("")
             ),
             NewsArticle(
                 "article",
                 "author",
                 "description",
                 Date(),
                 true,
                 listOf("")
             ),
             NewsArticle(
                 "article",
                 "author",
                 "description",
                 Date(),
                 true,
                 listOf("")
             )
        )
    }
}