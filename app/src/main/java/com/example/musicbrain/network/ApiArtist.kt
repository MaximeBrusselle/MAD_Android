package com.example.musicbrain.network

import com.example.musicbrain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiArtist(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "Unknown",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("gender") val gender: String = "Unknown",
    @SerialName("disambiguation") val disambiguation: String = "Unknown",
)

fun Flow<List<ApiArtist>>.asDomainObjects(): Flow<List<Artist>> {
    val list =
        this.map {
            it.asDomainObjects()
        }
    return list
}

fun List<ApiArtist>.asDomainObjects(): List<Artist> {
    return this.map {
        it.asDomainObject()
    }
}

fun Flow<ApiArtist>.asDomainObject(): Flow<Artist> {
    val artist =
        this.map {
            it.asDomainObject()
        }
    return artist
}

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
