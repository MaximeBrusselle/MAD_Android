package com.example.musicbrain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the "artists" table in the database.
 * This interface defines methods to perform CRUD (Create, Read, Update, Delete) operations on the artists table.
 */
@Dao
interface ArtistDao {
    /**
     * Retrieves a single artist from the "artists" table based on the specified ID.
     *
     * @param id The unique identifier of the artist.
     * @return A [Flow] emitting the [DbArtist] with the specified ID.
     */
    @Query("SELECT * from artists WHERE id = :id")
    fun getItem(id: String): Flow<DbArtist>

    /**
     * Retrieves all artists from the "artists" table, ordered by name in a case-insensitive manner.
     *
     * @return A [Flow] emitting a list of all [DbArtist] objects in alphabetical order by name.
     */
    @Query("SELECT * from artists ORDER BY name COLLATE NOCASE ASC")
    fun getAllItems(): Flow<List<DbArtist>>

    /**
     * Searches for artists in the "artists" table whose names match the specified search query.
     *
     * @param search The search query for artist names.
     * @return A [Flow] emitting a list of [DbArtist] objects whose names contain the search query.
     */
    @Query("SELECT * from artists WHERE name LIKE '%' || :search || '%' ORDER BY name COLLATE NOCASE ASC")
    fun getSearchItems(search: String): Flow<List<DbArtist>>

    /**
     * Inserts a new [DbArtist] into the "artists" table.
     *
     * @param item The [DbArtist] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbArtist)
}
