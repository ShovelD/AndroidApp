package com.example.newsApp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsApp.repositories.NewsRepository
import com.example.newsApp.repositories.NewsRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface EditState{
    data object Loading : EditState
    data class  DisplayNewsArticle(val newsArticle: NewsArticle?) : EditState
}

class EditViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val state = MutableStateFlow<EditState>(EditState.Loading)
    suspend fun onClickSave(newsArticle: NewsArticle) {
        viewModelScope.launch {
            newsRepository.upsert(newsArticle)
        }
    }

    fun setStateFlow(id:UUID?){
        viewModelScope.launch{
            newsRepository.getNewsArticle(id).collect{ newsArticle->
                state.value = EditState.DisplayNewsArticle(newsArticle)
            }
        }
    }
}