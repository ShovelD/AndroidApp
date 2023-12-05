package com.example.newsApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsApp.database.entities.NewsEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
@Dao
internal interface NewsDao {
    @Query("SELECT * FROM ${NewsEntity.TableName}")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM ${NewsEntity.TableName} WHERE id = :id")
    fun getNews(id: UUID): Flow<NewsEntity>

    @Upsert
    suspend fun save(e: NewsEntity)

    @Query("DELETE FROM ${NewsEntity.TableName} WHERE id = :id")
    suspend fun delete(id: UUID)
}