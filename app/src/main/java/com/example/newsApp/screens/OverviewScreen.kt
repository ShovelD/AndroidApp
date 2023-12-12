package com.example.newsApp.screens

import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.newsApp.MVIFramework.container
import com.example.newsApp.MVIFramework.subscribe
import com.example.newsApp.viewmodels.NewsArticle
import com.example.newsApp.viewmodels.OverviewEvent
import com.example.newsApp.viewmodels.OverviewIntent
import com.example.newsApp.viewmodels.OverviewMode
import com.example.newsApp.viewmodels.OverviewState
import com.example.newsApp.viewmodels.OverviewViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

@Composable
fun Overview(navController: NavController,id: UUID?,mode:OverviewMode){
    val viewModel = container<OverviewViewModel,_,_,_>{ parametersOf(id,mode)}
    val state by viewModel.subscribe{ event->
        when(event){
            is OverviewEvent.Exit ->{
                if(mode == OverviewMode.FromRemote){
                    navController.navigate("SiteScreen")
                }else{
                    navController.navigate("HomeScreen")
                }
            }
        }
    }
    OverViewComposable(state = state, intent = viewModel::intent)
}
@Composable
fun OverViewComposable(state:OverviewState,intent:(OverviewIntent)->Unit){
    when(state){
        is OverviewState.Loading ->{
            OverviewLoading()
        }
        is OverviewState.DisplayingRemoteArticle ->{
            val article = state.article?: NewsArticle.getNotFoundArticle()
            OverViewContent(article = article, buttonText = "remote"){
                intent(OverviewIntent.AddToFavoritesClicked(article))
                intent(OverviewIntent.ExitClicked)
            }

        }
        is OverviewState.DisplayingPrivateArticle ->{
            val article = state.article?: NewsArticle.getNotFoundArticle()
            OverViewContent(article = article,buttonText = "local"){
                intent(OverviewIntent.RemoveFromFavoritesClicked(article.id))
                intent(OverviewIntent.ExitClicked)
            }
        }
        is OverviewState.Error->{
            TODO()
        }
    }
}

@Composable
fun OverViewContent(article:NewsArticle,buttonText:String,event: ()->Unit){
    Text(text = article.articleTitle,
        modifier = Modifier.clickable { event() })
    Button(onClick = { event() }) {
        Text(text = buttonText)
    }
}
@Composable
fun OverviewLoading(){
    Dialog(onDismissRequest = { /*TODO*/ }) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.tertiary
        )
    }
}