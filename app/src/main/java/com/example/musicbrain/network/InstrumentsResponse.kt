package com.example.musicbrain.network

import com.example.musicbrain.model.Artist
import com.example.musicbrain.model.Instrument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InstrumentsResponse(
    @SerialName("created") val created: String,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("instruments") val instruments: List<ApiInstrument?>,
)

fun Flow<InstrumentsResponse>.asDomainObjects(): Flow<List<Instrument>> {
    return map { it.asDomainObject() }
}

fun InstrumentsResponse.asDomainObject(): List<Instrument> {
    val domainList = this.instruments.map {
        Instrument(
            id = it!!.id,
            type = it.type,
            score = it.score,
            name = it.name,
            description = it.description,
        )
    }
    return domainList
}