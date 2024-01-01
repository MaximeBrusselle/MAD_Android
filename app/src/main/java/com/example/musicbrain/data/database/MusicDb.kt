package com.example.musicbrain.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room Database class for managing local data persistence.
 * This database includes tables for artists and instruments.
 *
 * @property artistDao The Data Access Object (DAO) for artists.
 * @property instrumentDao The Data Access Object (DAO) for instruments.
 */
@Database(entities = [DbArtist::class, DbInstrument::class], version = 3, exportSchema = false)
abstract class MusicDb : RoomDatabase() {
    /**
     * The Data Access Object (DAO) for artists.
     */
    abstract fun artistDao(): ArtistDao

    /**
     * The Data Access Object (DAO) for instruments.
     */
    abstract fun instrumentDao(): InstrumentDao

    companion object {
        /**
         * The singleton instance of the [MusicDb] database.
         */
        @Volatile
        private var instance: MusicDb? = null

        /**
         * Gets an instance of the [MusicDb] database.
         *
         * @param context The application context.
         * @return An instance of the [MusicDb] database.
         */
        fun getDatabase(context: Context): MusicDb {
            // If the instance is not null, return it; otherwise, create a new database instance.
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDb::class.java, "music_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
