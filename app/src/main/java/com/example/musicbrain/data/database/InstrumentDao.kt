package com.example.musicbrain.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InstrumentDao {
    @Query("SELECT * from instruments WHERE id = :id")
    fun getItem(id: String): Flow<dbInstrument>

    @Query("SELECT * from instruments ORDER BY name COLLATE NOCASE ASC ")
    fun getAllItems(): Flow<List<dbInstrument>>

    @Query("SELECT * from instruments WHERE name LIKE :search ORDER BY name COLLATE NOCASE ASC ")
    fun getSearchItems(search: String): Flow<List<dbInstrument>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbInstrument)

    @Update
    suspend fun update(item: dbInstrument)

    @Delete
    suspend fun delete(item: dbInstrument)
}