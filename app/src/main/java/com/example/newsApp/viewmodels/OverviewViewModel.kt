package com.example.newsApp.viewmodels

import com.example.newsApp.MVIFramework.MVIViewModel
import com.example.newsApp.repositories.NewsRepository
import com.example.newsApp.repositories.RemoteNewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID

sealed interface OverviewState {
    data object Loading : OverviewState
    data class DisplayingRemoteArticle(val article: NewsArticle?) : OverviewState
    data class DisplayingPrivateArticle(val article: NewsArticle?) : OverviewState
    data class Error(val text: String?) : OverviewState
}
sealed interface OverviewEvent {
    data object Exit : OverviewEvent
}

enum class OverviewMode {
    FromRemote,
    FromLocal
}

sealed interface OverviewIntent {
    data object ExitClicked : OverviewIntent
    data class AddToFavoritesClicked(val article: NewsArticle) : OverviewIntent
    data class RemoveFromFavoritesClicked(val id: UUID) : OverviewIntent
}

class OverviewViewModel(
    private val id:UUID?,
    private val mode:OverviewMode,
    private val remoteNewsRepository: RemoteNewsRepository,
    private val newsRepository: NewsRepository
):MVIViewModel<OverviewState,OverviewIntent,OverviewEvent>(OverviewState.Loading){
    override fun CoroutineScope.onSubscribe() {
        when(mode){
            OverviewMode.FromLocal->{
                newsRepository.getNewsArticle(id).onEach {
                    state {
                        if (it != null){
                            OverviewState.DisplayingPrivateArticle(it)
                        }else{
                            OverviewState.Error("Error")
                        }
                    }
                }.flowOn(Dispatchers.Default)
                    .launchIn(this)
            }
            OverviewMode.FromRemote -> {
                remoteNewsRepository.getSingleArticle(id).onEach {
                    state{
                        if(it != null){
                            OverviewState.DisplayingRemoteArticle(it)
                        }else{
                            OverviewState.Error("Error")
                        }
                    }
                }.flowOn(Dispatchers.Default)
                    .launchIn(this)
            }
        }
    }

    override suspend fun reduce(intent: OverviewIntent) {
        when(intent){
            is OverviewIntent.ExitClicked ->{
                event(OverviewEvent.Exit)
            }
            is OverviewIntent.AddToFavoritesClicked ->{
                newsRepository.upsert(intent.article)
            }
            is OverviewIntent.RemoveFromFavoritesClicked->{
                newsRepository.delete(intent.id)
            }
        }
    }
}
