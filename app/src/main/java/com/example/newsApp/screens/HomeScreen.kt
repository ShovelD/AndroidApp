package com.example.newsApp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
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
        },
        navController,
        state = myState.value
    )
}

@Composable
private fun HomeScreenContent(onDeleteClick:(NewsArticle)->Unit, navController: NavController,state: HomeState) {
    Scaffold(
        topBar = { HomeScreenTopBar(navController = navController)},
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
            is HomeState.Empty -> HomeScreenEmpty(modifier = Modifier.padding(values).fillMaxWidth())
            is HomeState.DisplayingNewsArticles ->{
                Column(modifier = Modifier
                    .padding(values)
                    .verticalScroll(rememberScrollState())
                ) {
                    for(item in state.newsArticles){
                        NewsArticleItem(
                            article = item,
                            navController,
                            onDeleteClick = {onDeleteClick(item)})
                    }
                }
            }
            is HomeState.Error -> HomeScreenLoading(modifier = Modifier.padding(values))
            HomeState.Loading -> HomeScreenLoading(modifier = Modifier.padding(values))
        }
    }
}
@Composable
fun HomeScreenLoading(modifier: Modifier){
    Dialog(onDismissRequest = { /*TODO*/ }) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun HomeScreenEmpty(modifier: Modifier) {
    Text(
        text = stringResource(R.string.list_is_empty),
        color = MaterialTheme.colorScheme.tertiary,
        fontFamily = MainActivity.AcmeFont,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun NewsArticleItem(article: NewsArticle,navController: NavController, onDeleteClick: () -> Unit) {
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
                    onClick = { navController.navigate("EditScreen?id=${article.id}")},
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
                text = article.articleTitle,
                fontFamily = MainActivity.AcmeFont,
                fontSize = 14.sp
            )
            Text(
                text = article.articleAuthor,
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

