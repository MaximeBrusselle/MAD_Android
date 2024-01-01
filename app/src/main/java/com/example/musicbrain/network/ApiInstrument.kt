package com.example.musicbrain.network

import com.example.musicbrain.model.Instrument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiInstrument(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "Unknown",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("description") val description: String = "No description provided",
)

fun Flow<List<ApiInstrument>>.asDomainObjects(): Flow<List<Instrument>> {
    val list =
        this.map {
            it.asDomainObjects()
        }
    return list
}

fun List<ApiInstrument>.asDomainObjects(): List<Instrument> {
    return this.map {
        it.asDomainObject()
    }
}

fun Flow<ApiInstrument>.asDomainObject(): Flow<Instrument> {
    val instrument =
        this.map {
            it.asDomainObject()
        }
    return instrument
}

fun ApiInstrument.asDomainObject(): Instrument {
    return Instrument(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        description = this.description,
    )
}
