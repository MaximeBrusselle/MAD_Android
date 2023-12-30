package com.example.musicbrain.network

import com.example.musicbrain.model.Artist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiArtist(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("gender") val gender: String = "Unknown",
    @SerialName("disambiguation") val disambiguation: String = "Unknown",
)

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