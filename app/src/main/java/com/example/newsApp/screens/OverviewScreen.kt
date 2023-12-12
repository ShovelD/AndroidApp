package com.example.newsApp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.newsApp.MVIFramework.container
import com.example.newsApp.MVIFramework.subscribe
import com.example.newsApp.MainActivity
import com.example.newsApp.R
import com.example.newsApp.viewmodels.HomeState
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
            OverViewContent(article = article, buttonText = "Add to favorite"){
                intent(OverviewIntent.AddToFavoritesClicked(article))
                intent(OverviewIntent.ExitClicked)
            }
        }
        is OverviewState.DisplayingPrivateArticle ->{
            val article = state.article?: NewsArticle.getNotFoundArticle()
            OverViewContent(article = article,buttonText = "RemoveFromFavorite"){
                intent(OverviewIntent.RemoveFromFavoritesClicked(article.id))
                intent(OverviewIntent.ExitClicked)
            }
        }
        is OverviewState.Error->{
            TODO()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OverViewContent(article:NewsArticle,buttonText:String,event: ()->Unit){
    Scaffold (
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Title: ${article.articleTitle}", modifier = Modifier.padding(10.dp)
                .background(MaterialTheme.colorScheme.primary))
            Text(text = "Author: ${article.articleAuthor}",modifier = Modifier.padding(10.dp)
                .background(MaterialTheme.colorScheme.primary))
            Text(text = "Description: ${article.articleDescription}",modifier = Modifier.padding(10.dp)
                .background(MaterialTheme.colorScheme.primary))
            Text(text = "Date: ${article.articlePublishingDate}",modifier = Modifier.padding(10.dp)
                .background(MaterialTheme.colorScheme.primary))
            Button(
                onClick = { event() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = buttonText, color = MaterialTheme.colorScheme.secondary)
            }
        }
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