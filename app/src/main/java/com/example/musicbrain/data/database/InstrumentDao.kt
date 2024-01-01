package com.example.musicbrain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the "instruments" table in the database.
 * This interface defines methods to perform CRUD (Create, Read, Update, Delete) operations on the instruments table.
 */
@Dao
interface InstrumentDao {
    /**
     * Retrieves a single instrument from the "instruments" table based on the specified ID.
     *
     * @param id The unique identifier of the instrument.
     * @return A [Flow] emitting the [DbInstrument] with the specified ID.
     */
    @Query("SELECT * from instruments WHERE id = :id")
    fun getItem(id: String): Flow<DbInstrument>

    /**
     * Retrieves all instruments from the "instruments" table, ordered by name in a case-insensitive manner.
     *
     * @return A [Flow] emitting a list of all [DbInstrument] objects in alphabetical order by name.
     */
    @Query("SELECT * from instruments ORDER BY name COLLATE NOCASE ASC")
    fun getAllItems(): Flow<List<DbInstrument>>

    /**
     * Searches for instruments in the "instruments" table whose names contain the specified search query.
     *
     * @param search The search query for instrument names.
     * @return A [Flow] emitting a list of [DbInstrument] objects whose names match or contain the search query.
     */
    @Query("SELECT * from instruments WHERE name LIKE '%' || :search || '%' ORDER BY name COLLATE NOCASE ASC")
    fun getSearchItems(search: String): Flow<List<DbInstrument>>

    /**
     * Inserts a new [DbInstrument] into the "instruments" table.
     *
     * @param item The [DbInstrument] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbInstrument)
}
