package com.example.newsApp.MVIFramework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MVIViewModel<S,I,E>(initial:S):ViewModel(),Container<S,I,E> {
    private val _states: MutableStateFlow<S> = MutableStateFlow(initial)
    private val _events = Channel<E>()
    private val events = _events.receiveAsFlow()

    final override val states = _states.asStateFlow()
    final override fun intent(intent: I) {
        viewModelScope.launch { reduce(intent) }
    }
    final override fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit) {
        events.onEach{onEvent(it)}.launchIn(this)
        onSubscribe()
    }

    protected fun event(event: E) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
    protected fun state(block: S.() -> S){
        viewModelScope.launch{
            _states.update(block)
        }
    }
    protected abstract fun CoroutineScope.onSubscribe()
    protected abstract suspend fun reduce(intent: I)

}