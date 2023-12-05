package com.example.newsApp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsApp.NewsRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Date
class HomeViewModel : ViewModel() {
    val state: StateFlow<HomeState> =
        NewsRepositoryImpl.getNewsArticles()
            .map { data ->
                when {
                    data.isEmpty() -> HomeState.Empty
                    else -> HomeState.DisplayingNewsArticles(data)
                }
            }.stateIn(viewModelScope, SharingStarted.Lazily, HomeState.Loading)

    suspend fun onClickRemoveArticle(newsArticle: NewsArticle) =
        NewsRepositoryImpl.delete(newsArticle.id)

    companion object {
        val DefaultNewsArticles = listOf(
            NewsArticle(
                "article1",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            )
        )
    }
}
sealed interface HomeState {
    data object Loading : HomeState
    data object Empty : HomeState
    data class DisplayingNewsArticles(val newsArticles: List<NewsArticle>) : HomeState
    data class Error(val e: Exception) : HomeState
}