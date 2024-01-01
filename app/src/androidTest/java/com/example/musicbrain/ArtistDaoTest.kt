package com.example.musicbrain

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicbrain.data.database.ArtistDao
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.data.database.asDbArtist
import com.example.musicbrain.data.database.asDomainArtist
import com.example.musicbrain.fake.FakeDataSource
import com.example.musicbrain.model.Artist
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test for the [ArtistDao]
 */
@RunWith(AndroidJUnit4::class)
class ArtistDaoTest {
    /**
     * The DAO for the [MusicDb] database.
     */
    private lateinit var artistDao: ArtistDao

    /**
     * The [MusicDb] database.
     */
    private lateinit var musicDb: MusicDb

    /**
     * The [Artist] objects to use for testing.
     */
    private var artist1 = FakeDataSource.apiArtists[0].asDomainObject()
    private var artist2 = FakeDataSource.apiArtists[1].asDomainObject()

    /**
     * Adds a single artist to the database.
     */
    private suspend fun addOneArtistToDb() {
        artistDao.insert(artist1.asDbArtist())
    }

    /**
     * Adds two artists to the database.
     */
    private suspend fun addTwoArtistsToDb() {
        artistDao.insert(artist1.asDbArtist())
        artistDao.insert(artist2.asDbArtist())
    }

    /**
     * Creates the database and DAO.
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        musicDb =
            Room.inMemoryDatabaseBuilder(context, MusicDb::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        artistDao = musicDb.artistDao()
    }

    /**
     * Closes the database.
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        musicDb.close()
    }

    /**
     * Tests that the DAO can insert an artist into the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertArtistIntoDB() =
        runBlocking {
            addOneArtistToDb()
            val allItems = artistDao.getAllItems().first()
            assertEquals(allItems[0].asDomainArtist(), artist1)
        }

    /**
     * Tests that getAllArtists returns all artists from the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllArtists_returnsAllArtistsFromDB() =
        runBlocking {
            addTwoArtistsToDb()
            val allItems = artistDao.getAllItems().first()
            assertEquals(allItems[0].asDomainArtist(), artist1)
            assertEquals(allItems[1].asDomainArtist(), artist2)
        }
}
