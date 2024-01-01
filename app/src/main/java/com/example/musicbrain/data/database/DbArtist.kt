package com.example.musicbrain.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicbrain.model.Artist

/**
 * Represents an artist entity in the local Room database.
 *
 * @property id The unique identifier of the artist.
 * @property type The type of the artist.
 * @property score The score associated with the artist.
 * @property name The name of the artist.
 * @property gender The gender of the artist (default is "Unknown").
 * @property disambiguation Additional disambiguation information about the artist (default is "Unknown").
 */
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

/**
 * Converts a [DbArtist] object to a [Artist] domain model.
 *
 * @return An instance of [Artist] representing the same data as the [DbArtist].
 */
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

/**
 * Converts an [Artist] domain model to a [DbArtist] object.
 *
 * @return An instance of [DbArtist] representing the same data as the [Artist].
 */
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

/**
 * Converts a list of [DbArtist] objects to a list of [Artist] domain models.
 *
 * @return A list of [Artist] objects representing the same data as the list of [DbArtist].
 */
fun List<DbArtist>.asDomainArtists(): List<Artist> {
    return this.map {
        Artist(
            it.id,
            it.type,
            it.score,
            it.name,
            it.gender,
            it.disambiguation,
        )
    }
}
