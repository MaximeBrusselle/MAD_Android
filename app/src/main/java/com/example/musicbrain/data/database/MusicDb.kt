package com.example.musicbrain.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [dbArtist::class], version = 2, exportSchema = false)
abstract class MusicDb : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var Instance: MusicDb? = null

        fun getDatabase(context: Context): MusicDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDb::class.java, "music_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}