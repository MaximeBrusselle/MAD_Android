package com.example.musicbrain.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable data class representing the response when retrieving a list of artists from the API.
 *
 * @property created The timestamp indicating when the response was created.
 * @property count The total number of artists in the response.
 * @property offset The offset value used for pagination.
 * @property artists The list of [ApiArtist] objects in the response.
 */
@Serializable
data class ArtistsResponse(
    @SerialName("created") val created: String,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("artists") val artists: List<ApiArtist>,
)
