package com.example.musicbrain.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable data class representing the response when retrieving a list of instruments from the API.
 *
 * @property created The timestamp indicating when the response was created.
 * @property count The total number of instruments in the response.
 * @property offset The offset value used for pagination.
 * @property instruments The list of [ApiInstrument] objects in the response.
 */
@Serializable
data class InstrumentsResponse(
    @SerialName("created") val created: String,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("instruments") val instruments: List<ApiInstrument>,
)
