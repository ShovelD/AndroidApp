package com.example.newsApp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.newsApp.MainActivity
import com.example.newsApp.R
import com.example.newsApp.viewmodels.NewsArticle
import com.example.newsApp.viewmodels.RemoteState
import com.example.newsApp.viewmodels.RemoteViewModel
import org.koin.compose.koinInject

@Composable
fun SiteComposable(navController: NavController){
    val viewModel = koinInject<RemoteViewModel>()
    val state: State<RemoteState> = viewModel.state.collectAsStateWithLifecycle()
    SiteScreenContent(navController = navController, onItemClicked = {article->viewModel.onClickSave(article)},state.value)
}
@Composable
fun SiteScreenContent(navController: NavController, onItemClicked:(NewsArticle)->Unit,state:RemoteState){
    Scaffold(
        topBar = { RemoteScreenTopBar(navController = navController) },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { navController.navigate("EditScreen?id = ${null}")},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add", tint = Color.Green)
            }
        }
    ) {
            values->
        when(state){
            is RemoteState.DisplayingNewsArticles ->{
                Column(modifier = Modifier
                    .padding(values)
                    .verticalScroll(rememberScrollState())
                ) {
                    for(item in state.newsArticles){
                        RemoteNewsArticle(
                            article = item,
                            onItemClick = {onItemClicked(item)}
                        )
                    }
                }
            }
            RemoteState.Loading -> HomeScreenLoading(modifier = Modifier.padding(values))
        }
    }
}
@Composable
fun RemoteNewsArticle(article: NewsArticle,onItemClick:()->Unit){
    Text(text = article.articleTitle, modifier = Modifier.padding(30.dp).clickable { onItemClick.invoke()})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteScreenTopBar(navController: NavController){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.tertiary
        ),
        title = {
            Text(
                text = stringResource(id = R.string.home_screen),
                fontFamily = MainActivity.AcmeFont,
                fontSize = 30.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate("HomeScreen")}) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
            }
        },
    )
}