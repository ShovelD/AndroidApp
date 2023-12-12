package com.example.newsApp.MVIFramework

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface Container<S,I,E> {
    fun CoroutineScope.subscribe(onEvent:suspend (E)->Unit)
    val states:StateFlow<S>
    fun intent(intent: I)
}