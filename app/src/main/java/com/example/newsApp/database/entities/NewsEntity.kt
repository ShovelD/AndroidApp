package com.example.newsApp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.UUID

@Entity(tableName = NewsEntity.TableName)
data class NewsEntity (
    @PrimaryKey val id:UUID,
    val articleTitle: String,
    val articleAuthor: String,
    val articleDescription: String,
    val articlePublishingDate: Long,
    val isDraft: Boolean,
    val tags: String
){
    internal companion object{
        const val TableName = "news"
    }
}