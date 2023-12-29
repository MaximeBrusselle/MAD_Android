package com.example.musicbrain.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicbrain.model.Artist

@Entity(tableName = "artists")
data class dbArtist(
    @PrimaryKey
    val id: String = "",
    val type: String = "",
    val score: Int = 0,
    val name: String = "",
    val gender: String = "",
    val disambiguation: String = "",
)

fun dbArtist.asDomainArtist(): Artist {
    return Artist(
        this.id,
        this.type,
        this.score,
        this.name,
        this.gender,
        this.disambiguation,
    )
}

fun Artist.asDbArtist(): dbArtist {
    return dbArtist(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        gender = this.gender,
        disambiguation = this.disambiguation,
    )
}

fun List<dbArtist>.asDomainArtists(): List<Artist> {
    val artistList = this.map {
        Artist(
            it.id,
            it.type,
            it.score,
            it.name,
            it.gender,
            it.disambiguation,
        )
    }
    return artistList
}