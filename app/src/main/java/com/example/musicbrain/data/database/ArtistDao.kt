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
    fun getItem(id: String): Flow<DbArtist>

    @Query("SELECT * from artists ORDER BY name COLLATE NOCASE ASC ")
    fun getAllItems(): Flow<List<DbArtist>>

    @Query("SELECT * from artists WHERE name LIKE :search  ORDER BY name COLLATE NOCASE ASC ")
    fun getSearchItems(search: String): Flow<List<DbArtist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbArtist)

    @Update
    suspend fun update(item: DbArtist)

    @Delete
    suspend fun delete(item: DbArtist)
}
