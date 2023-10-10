package com.example.newsApp

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

data class NewsArticle(
    val articleTitle: String,
    val articleAuthor: String,
    val articleDescription: String,
    val articlePublishingDate: Date,
    val isDraft: Boolean,
    val tags: List<String>,
    val id: UUID = UUID.randomUUID()
)

class HomeViewModel() : ViewModel() {
    val items: SnapshotStateList<NewsArticle> = DefaultNewsArticles.toMutableStateList()

    fun onClickRemoveArticle(newsArticle: NewsArticle) = items.remove(newsArticle)
    fun onClickAddArticle() = items.add(
        NewsArticle(
            "article",
            "author",
            "description",
            Date(),
            true,
            listOf("")
        )
    )

    private companion object {
        private val DefaultNewsArticles = listOf(
            NewsArticle(
                "article",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            ),
            NewsArticle(
                "article",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            )
        )
    }
}