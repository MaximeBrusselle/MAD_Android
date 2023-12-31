package com.example.musicbrain

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicbrain.data.database.ArtistDao
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.data.database.asDbArtist
import com.example.musicbrain.data.database.asDomainArtist
import com.example.musicbrain.model.Artist
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArtistDaoTest {
    private lateinit var artistDao: ArtistDao
    private lateinit var musicDb: MusicDb

    private var artist1 = Artist("test1", "type1", 12, "musicbrainArtistDaoTest")
    private var artist2 = Artist("test2", "type2", 12, "musicbrainArtistDaoTest")

    // unility functions
    private suspend fun addOneArtistToDb() {
        artistDao.insert(artist1.asDbArtist())
    }

    private suspend fun addTwoArtistsToDb() {
        artistDao.insert(artist1.asDbArtist())
        artistDao.insert(artist2.asDbArtist())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        musicDb = Room.inMemoryDatabaseBuilder(context, MusicDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        artistDao = musicDb.artistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        musicDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertArtistIntoDB() = runBlocking {
        addOneArtistToDb()
        val allItems = artistDao.getAllItems().first()
        assertEquals(allItems[0].asDomainArtist(), artist1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllArtists_returnsAllArtistsFromDB() = runBlocking {
        addTwoArtistsToDb()
        val allItems = artistDao.getAllItems().first()
        assertEquals(allItems[0].asDomainArtist(), artist1)
        assertEquals(allItems[1].asDomainArtist(), artist2)
    }
}
