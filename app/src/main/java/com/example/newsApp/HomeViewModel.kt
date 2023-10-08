package com.example.newsApp

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

data class NewsArticle(
    val articleTitle:String,
    val articleAuthor:String,
    val articleDescription:String,
    val articlePublishingDate:Date,
    val isDraft:Boolean,
    val id:UUID = UUID.randomUUID()
)

class HomeViewModel(): ViewModel(){
    val items:SnapshotStateList<NewsArticle> = DefaultNewsArticles.toMutableStateList()

    fun onClickRemoveArticle(newsArticle: NewsArticle) = items.remove(newsArticle)
    fun onClickAddArticle() = items.add(
        NewsArticle(
            "fucking peace of shit",
            "me",
            "f*cku",
            Date(),
            true,
        )
    )
    private companion object {
        private val DefaultNewsArticles = listOf(
            NewsArticle(
                "fucking shit",
                "me",
                "no",
                Date(),
                true,
            ),
            NewsArticle(
                "fucking peace of shit",
                "me",
                "f*cku",
                Date(),
                true,
            )
        )
    }
}