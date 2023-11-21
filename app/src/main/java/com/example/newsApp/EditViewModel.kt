package com.example.newsApp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed interface EditState{
    data object Loading : EditState
    data class  DisplayNewsArticle(val newsArticle: NewsArticle?) : EditState
}

class EditViewModel : ViewModel() {
    val state = MutableStateFlow<EditState>(EditState.Loading)
    suspend fun onClickSave(newsArticle: NewsArticle) = NewsRepositoryImpl.upsert(newsArticle)

    fun setStateFlow(id:UUID?){
        viewModelScope.launch{
            NewsRepositoryImpl.getNewsArticle(id).collect{newsArticle->
                state.value = EditState.DisplayNewsArticle(newsArticle)

            }
        }
    }
}