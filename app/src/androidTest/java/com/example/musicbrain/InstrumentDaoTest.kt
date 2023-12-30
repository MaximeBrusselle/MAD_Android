package com.example.musicbrain

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicbrain.data.database.InstrumentDao
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.data.database.asDbInstrument
import com.example.musicbrain.data.database.asDomainInstrument
import com.example.musicbrain.model.Instrument
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InstrumentDaoTest {
    private lateinit var instrumentDao: InstrumentDao
    private lateinit var musicDb: MusicDb

    private var instrument1 = Instrument("test1", "musicbrainInstrumentDaoTest", "type1", "decription1", 1)
    private var instrument2 = Instrument("test2", "musicbrainInstrumentDaoTest", "type2", "description2", 2)

    // unility functions
    private suspend fun addOneInstrumentToDb() {
        instrumentDao.insert(instrument1.asDbInstrument())
    }

    private suspend fun addTwoInstrumentsToDb() {
        instrumentDao.insert(instrument1.asDbInstrument())
        instrumentDao.insert(instrument2.asDbInstrument())
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
        instrumentDao = musicDb.instrumentDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        musicDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertInstrumentIntoDB() = runBlocking {
        addOneInstrumentToDb()
        val allItems = instrumentDao.getAllItems("musicbrainInstrumentDaoTest").first()
        assertEquals(allItems[0].asDomainInstrument(), instrument1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoInstrumentsToDb()
        val allItems = instrumentDao.getAllItems("musicbrainInstrumentDaoTest").first()
        assertEquals(allItems[0].asDomainInstrument(), instrument1)
        assertEquals(allItems[1].asDomainInstrument(), instrument2)
    }
}
