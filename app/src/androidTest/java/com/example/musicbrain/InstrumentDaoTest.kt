package com.example.musicbrain

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicbrain.data.database.InstrumentDao
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.data.database.asDbInstrument
import com.example.musicbrain.data.database.asDomainInstrument
import com.example.musicbrain.fake.FakeDataSource
import com.example.musicbrain.model.Instrument
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
 * Instrumented test for the [InstrumentDao]
 */
@RunWith(AndroidJUnit4::class)
class InstrumentDaoTest {
    /**
     * The DAO for the [MusicDb] database.
     */
    private lateinit var instrumentDao: InstrumentDao

    /**
     * The [MusicDb] database.
     */
    private lateinit var musicDb: MusicDb

    /**
     * The [Instrument] objects to use for testing.
     */
    private var instrument1 = FakeDataSource.apiInstruments[0].asDomainObject()
    private var instrument2 = FakeDataSource.apiInstruments[1].asDomainObject()

    /**
     * Adds a single instrument to the database.
     */
    private suspend fun addOneInstrumentToDb() {
        instrumentDao.insert(instrument1.asDbInstrument())
    }

    /**
     * Adds two instruments to the database.
     */
    private suspend fun addTwoInstrumentsToDb() {
        instrumentDao.insert(instrument1.asDbInstrument())
        instrumentDao.insert(instrument2.asDbInstrument())
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
        instrumentDao = musicDb.instrumentDao()
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
     * Tests that the DAO can insert an instrument into the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertInstrumentIntoDB() =
        runBlocking {
            addOneInstrumentToDb()
            val allItems = instrumentDao.getAllItems().first()
            assertEquals(allItems[0].asDomainInstrument(), instrument1)
        }

    /**
     * Tests that getAllInstruments returns all instruments from the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllInstruments_returnsAllInstrumentsFromDB() =
        runBlocking {
            addTwoInstrumentsToDb()
            val allItems = instrumentDao.getAllItems().first()
            assertEquals(allItems[0].asDomainInstrument(), instrument1)
            assertEquals(allItems[1].asDomainInstrument(), instrument2)
        }
}
