package com.example.newsApp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsApp.NewsRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface EditState{
    data object Loading : EditState
    data class  DisplayNewsArticle(val newsArticle: NewsArticle?) : EditState
}

class EditViewModel : ViewModel() {
    val state = MutableStateFlow<EditState>(EditState.Loading)
    suspend fun onClickSave(newsArticle: NewsArticle) = NewsRepositoryImpl.upsert(newsArticle)

    fun setStateFlow(id:UUID?){
        viewModelScope.launch{
            NewsRepositoryImpl.getNewsArticle(id).collect{ newsArticle->
                state.value = EditState.DisplayNewsArticle(newsArticle)

            }
        }
    }
}