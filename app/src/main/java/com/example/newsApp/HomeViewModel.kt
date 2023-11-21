package com.example.newsApp

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    suspend fun onClickAddArticle(newsArticle: NewsArticle) = NewsRepositoryImpl.upsert(newsArticle)

     companion object {
         val DefaultNewsArticles = listOf(
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