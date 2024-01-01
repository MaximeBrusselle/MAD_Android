package com.example.musicbrain.model

/**
 * Data class representing an artist.
 *
 * @property id The unique identifier of the artist.
 * @property type The type of the artist (default is "Unknown").
 * @property score The score associated with the artist (default is 0).
 * @property name The name of the artist.
 * @property gender The gender of the artist (default is "Unknown").
 * @property disambiguation Additional information to disambiguate artists (default is "Unknown").
 */
data class Artist(
    val id: String,
    val type: String = "Unknown",
    val score: Int = 0,
    val name: String = "",
    val gender: String = "Unknown",
    val disambiguation: String = "Unknown",
)
