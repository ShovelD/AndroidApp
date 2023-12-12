package com.example.newsApp.MVIFramework

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition
@Composable
fun <S, A> Container<S, *, A>.subscribe(onEvent: suspend (A) -> Unit): State<S> {
    val state = states.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                subscribe(onEvent)
            }
        }
    }
    return state
}

@Composable
inline fun <reified T, S, I, E> container(
    noinline params: ParametersDefinition? = null,
): Container<S, I, E> where T : Container<S, I, E>, T : ViewModel = koinInject<T>(parameters = params)