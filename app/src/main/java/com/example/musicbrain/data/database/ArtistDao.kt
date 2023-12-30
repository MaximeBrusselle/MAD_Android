package com.example.musicbrain.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * from artists WHERE id = :id")
    fun getItem(id: String): Flow<dbArtist>

    @Query("SELECT * from artists WHERE name LIKE :query ORDER BY name COLLATE NOCASE ASC ")
    fun getAllItems(query: String): Flow<List<dbArtist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbArtist)

    @Update
    suspend fun update(item: dbArtist)

    @Delete
    suspend fun delete(item: dbArtist)
}