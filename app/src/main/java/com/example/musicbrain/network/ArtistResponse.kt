package com.example.musicbrain.network

import com.example.musicbrain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponse(
    @SerialName("created") val created: String,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("artists") val artists: List<ApiArtist?>,
)

fun Flow<ArtistResponse>.asDomainObjects(): Flow<List<Artist>> {
    return map { it.asDomainObject() }
}

fun ArtistResponse.asDomainObject(): List<Artist> {
    val domainList = this.artists.map {
        Artist(
            id = it!!.id,
            type = it.type,
            score = it.score,
            name = it.name,
            gender = it.gender,
            disambiguation = it.disambiguation,
        )
    }
    return domainList
}