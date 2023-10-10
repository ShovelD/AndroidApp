@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsApp


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenComposable(onClick: () -> Unit, onEditClick: () -> Unit) {
    val viewModel = viewModel<HomeViewModel>()
    Scaffold(
        topBar = {
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
                    IconButton(onClick = { onClick() }) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                    }
                },
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { viewModel.onClickAddArticle() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add", tint = Color.Green)
            }
        }
    ) {
        HomeScreen(viewModel, onEditClick)
    }
}

@Composable
//@Preview
private fun HomeScreen(viewModel: HomeViewModel, onEditClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(5.dp, 70.dp)
            .fillMaxSize(),
    ) {
        items(viewModel.items.size) { index ->
            NewsArticles(viewModel.items[index], index + 1, onEditClick)
        }
    }
}

@Composable
fun NewsArticles(article: NewsArticle, index: Int, onEditClick: () -> Unit) {

    Row(
        modifier = Modifier
            .clip(CutCornerShape(5))
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {},
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row {
                IconButton(
                    onClick = { },
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
                    onClick = { onEditClick() },
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
                text = index.toString() + article.articleTitle,
                fontFamily = MainActivity.AcmeFont,
                fontSize = 16.sp
            )
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutAppComposable(onClick: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.info_screen),
                        fontFamily = MainActivity.AcmeFont,
                        fontSize = 30.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClick.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {
        AboutApp()
    }
}

@Composable
private fun AboutApp() {
    Column(
        modifier = Modifier
            .padding(20.dp, 70.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.developer_logo),
                contentDescription = "",
                modifier = Modifier
                    .clip(CutCornerShape(45.dp))
                    .size(200.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { }
            )
        }
        for (i in MainActivity.aboutDeveloper) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CutCornerShape(5.dp))
                    .background(color = MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    fontFamily = MainActivity.AcmeFont,
                    text = stringResource(i),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditComposable(onClick: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.edit_screen),
                        fontFamily = MainActivity.AcmeFont,
                        fontSize = 30.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClick() }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }
                },
            )
        }
    ) {
        EditScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun EditScreen() {
    var titleText by remember { mutableStateOf("Title") }
    var authorText by remember { mutableStateOf("Author") }
    var description by remember { mutableStateOf("Description") }
    LazyColumn(
        modifier = Modifier
            .padding(5.dp, 70.dp)
            .fillMaxSize(),
    ) {
        item {
            TextField(
                value = titleText,
                onValueChange = { titleText = it },
                label = {
                    Text(
                        text = stringResource(R.string.title),
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = MainActivity.AcmeFont, fontSize = 14.sp
                    )
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primary),
            )
            TextField(
                value = authorText,
                onValueChange = { authorText = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.author),
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = MainActivity.AcmeFont, fontSize = 14.sp
                    )
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primary),
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.description),
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = MainActivity.AcmeFont, fontSize = 14.sp
                    )
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primary),
            )
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start

            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(id = R.string.draft),fontFamily = MainActivity.AcmeFont, fontSize = 14.sp)
                }
                Column(
                    modifier = Modifier
                ) {
                    val checkedState = remember { mutableStateOf(true) }
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it })
                }
            }

            val datePickerState = rememberDatePickerState()
            DatePicker(state = datePickerState, showModeToggle = true)
        }
    }
}