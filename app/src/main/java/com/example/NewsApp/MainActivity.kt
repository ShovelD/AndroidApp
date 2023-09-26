@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.NewsApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import com.example.NewsApp.ui.theme.AppTheme

import com.example.shittyapp.R

class MainActivity : ComponentActivity() {

    companion object {
        val aboutDeveloper = listOf(
            R.string.app_name,
            R.string.developer_name,
            R.string.developer_email
        )
    }
    val AcmeFont = FontFamily(
        Font(R.font.acme_regular)
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(true) {
                MainComposable()
            }
        }
    }

    @Preview
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainComposable() {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.tertiary
                    ),
                    title = { Text(text = "About app", fontFamily = AcmeFont, fontSize = 30.sp) },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
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
    fun AboutApp() {
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
                        .clickable {  }
                )
            }
            for (i in aboutDeveloper) {
                Row(
                    modifier = Modifier.padding(5.dp)
                        .clip(CutCornerShape(5.dp))
                        .background(color = MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(fontFamily = AcmeFont,
                        text = stringResource(i),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}