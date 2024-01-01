package com.example.musicbrain.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsResponse(
    @SerialName("created") val created: String,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("artists") val artists: List<ApiArtist>,
)
