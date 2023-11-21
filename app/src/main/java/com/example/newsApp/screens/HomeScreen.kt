package com.example.newsApp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsApp.HomeState
import com.example.newsApp.HomeViewModel
import com.example.newsApp.MainActivity
import com.example.newsApp.NewsArticle
import com.example.newsApp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<HomeViewModel>()
    val myState: State<HomeState> = viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        onDeleteClick = { newsArticle ->
            val job = Job()
            val scope = CoroutineScope(job)
            scope.launch {
                viewModel.onClickRemoveArticle(newsArticle)
            }
        }, onEditClick = { navController.navigate("EditScreen") },
        navController,
        state = myState.value
    )
}

@Composable
private fun HomeScreenContent(onDeleteClick:(NewsArticle)->Unit, onEditClick: () -> Unit, navController: NavController,state: HomeState) {
    Scaffold(
        topBar = { HomeScreenTopBar(navController = navController)},
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { navController.navigate("EditScreen") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add", tint = Color.Green)
            }
        }
    ) {
        values->
        when(state){
            is HomeState.Empty -> HomeScreenLoading()
            is HomeState.DisplayingNewsArticles ->{
                Column(modifier = Modifier
                    .padding(values)
                    .verticalScroll(rememberScrollState())
                ) {
                    for(item in state.newsArticles){
                        NewsArticle(
                            article = item,
                            onEditClick = { onEditClick()},
                            onDeleteClick = {onDeleteClick(item)})
                    }
                }
            }
            is HomeState.Error -> HomeScreenLoading()
            HomeState.Loading -> HomeScreenLoading()
        }
    }
}
@Composable
@Preview
fun HomeScreenLoading(){
    Dialog(onDismissRequest = { /*TODO*/ }) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun NewsArticle(article: NewsArticle, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CutCornerShape(5))
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row {
                IconButton(
                    onClick = { onDeleteClick()},
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)

                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.Red,
                    )
                }
            }
            Row {
                IconButton(
                    onClick = { onEditClick()},
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = Color.Blue,
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.developer_logo),
            contentDescription = "",
            modifier = Modifier
                .clip(CutCornerShape(5))
                .size(100.dp)
        )

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.author) + article.articleAuthor,
                fontFamily = MainActivity.AcmeFont,
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.description) + article.articleDescription,
                fontFamily = MainActivity.AcmeFont,
                fontSize = 14.sp
            )
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            Text(
                text = stringResource(R.string.date) + formatter.format(article.articlePublishingDate),
                fontFamily = MainActivity.AcmeFont,
                fontSize = 14.sp
            )
            if (article.isDraft)
                Text(
                    text = stringResource(R.string.draft),
                    color = Color.Red,
                    fontFamily = MainActivity.AcmeFont,
                    fontSize = 14.sp
                )
            Text(
                text = stringResource(R.string.tags),
                color = Color.Blue,
                fontFamily = MainActivity.AcmeFont,
                fontSize = 14.sp
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(navController: NavController){
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
            IconButton(onClick = { navController.navigate("AboutAppScreen")}) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
            }
        },
    )
}

