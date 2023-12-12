package com.example.newsApp.datasources

import java.util.UUID
import java.util.concurrent.Flow

interface DetailsDataSource {
    suspend fun addToFavorite(id:UUID)
    suspend fun removeFromFavorite(id:UUID)
}