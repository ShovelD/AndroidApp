@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsApp.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsApp.MainActivity
import com.example.newsApp.R


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