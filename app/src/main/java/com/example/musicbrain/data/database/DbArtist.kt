package com.example.musicbrain.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicbrain.model.Artist

@Entity(tableName = "artists")
data class DbArtist(
    @PrimaryKey
    val id: String = "",
    val type: String = "",
    val score: Int = 0,
    val name: String = "",
    val gender: String = "Unknown",
    val disambiguation: String = "Unknown",
)

fun DbArtist.asDomainArtist(): Artist {
    return Artist(
        this.id,
        this.type,
        this.score,
        this.name,
        this.gender,
        this.disambiguation,
    )
}

fun Artist.asDbArtist(): DbArtist {
    return DbArtist(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        gender = this.gender,
        disambiguation = this.disambiguation,
    )
}

fun List<DbArtist>.asDomainArtists(): List<Artist> {
    val artistList =
        this.map {
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
