package com.example.musicbrain.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbArtist::class, DbInstrument::class], version = 3, exportSchema = false)
abstract class MusicDb : RoomDatabase() {
    abstract fun artistDao(): ArtistDao

    abstract fun instrumentDao(): InstrumentDao

    companion object {
        @Volatile
        private var instance: MusicDb? = null

        fun getDatabase(context: Context): MusicDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDb::class.java, "music_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
