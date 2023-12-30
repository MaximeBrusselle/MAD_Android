package com.example.musicbrain.network

import com.example.musicbrain.model.Instrument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiInstrument(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "Unknown",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("description") val description: String = "Unknown",
)

fun ApiInstrument.asDomainObject(): Instrument {
    return Instrument(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        description = this.description,
    )
}