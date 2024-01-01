package com.example.musicbrain.network

import com.example.musicbrain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable data class representing an artist retrieved from the API.
 *
 * @property id The unique identifier of the artist.
 * @property type The type of the artist (default is "Unknown").
 * @property score The score associated with the artist (default is 0).
 * @property name The name of the artist.
 * @property gender The gender of the artist (default is "Unknown").
 * @property disambiguation Additional information to disambiguate artists (default is "Unknown").
 */
@Serializable
data class ApiArtist(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "Unknown",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("gender") val gender: String = "Unknown",
    @SerialName("disambiguation") val disambiguation: String = "Unknown",
)

/**
 * Extension function to convert a flow of lists of [ApiArtist] to a flow of lists of [Artist].
 */
fun Flow<List<ApiArtist>>.asDomainObjects(): Flow<List<Artist>> {
    return map { it.asDomainObjects() }
}

/**
 * Extension function to convert a list of [ApiArtist] to a list of [Artist].
 */
fun List<ApiArtist>.asDomainObjects(): List<Artist> {
    return map { it.asDomainObject() }
}

/**
 * Extension function to convert a flow of [ApiArtist] to a flow of [Artist].
 */
fun Flow<ApiArtist>.asDomainObject(): Flow<Artist> {
    return map { it.asDomainObject() }
}

/**
 * Extension function to convert an [ApiArtist] to an [Artist].
 */
fun ApiArtist.asDomainObject(): Artist {
    return Artist(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        gender = this.gender,
        disambiguation = this.disambiguation,
    )
}
