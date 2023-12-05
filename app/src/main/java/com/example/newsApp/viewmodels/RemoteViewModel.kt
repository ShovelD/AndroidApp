package com.example.newsApp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsApp.repositories.NewsRepository
import com.example.newsApp.repositories.RemoteNewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RemoteViewModel(remoteNewsRepository: RemoteNewsRepository,private val newsRepository: NewsRepository):ViewModel() {
    fun onClickSave(newsArticle: NewsArticle) {
        viewModelScope.launch {
            newsRepository.upsert(newsArticle)
        }
    }

    val state: StateFlow<RemoteState> = remoteNewsRepository.getNewsArticles().map(RemoteState::DisplayingNewsArticles).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RemoteState.Loading
    )
}
    sealed interface RemoteState{
        data object Loading : RemoteState
        data class DisplayingNewsArticles(val newsArticles: List<NewsArticle>) : RemoteState
    }