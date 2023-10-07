package com.example.newsApp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenComposable(onClick: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = { Text(text = "HomeScreen", fontFamily = MainActivity.AcmeFont, fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { onClick() }) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                    }
                }
            )
        },
    ){
        HomeScreen()
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
                title = { Text(text = "About app", fontFamily = MainActivity.AcmeFont, fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { onClick.invoke() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) {
        AboutApp()
    }
}

@Preview
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
                Text(fontFamily = MainActivity.AcmeFont,
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

@Composable
private fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(20.dp, 70.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        for (i:Int in 1..6) {
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
        }
    }
}