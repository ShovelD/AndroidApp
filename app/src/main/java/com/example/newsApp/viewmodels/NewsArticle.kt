package com.example.newsApp.viewmodels

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
){
    companion object {
        fun getNotFoundArticle(): NewsArticle {
            return NewsArticle(
                "article1",
                "author",
                "description",
                Date(),
                true,
                listOf("")
            )
        }
    }
}